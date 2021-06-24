// start the time so we can get the total time the program run
val start = System.currentTimeMillis
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{VectorAssembler, IndexToString, StringIndexer, VectorIndexer}
import org.apache.spark.sql.SparkSession

//reduce the errors
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
//start session
val spark = SparkSession.builder().getOrCreate()
//load csv
val data = spark.read.option("header", "true").option("inferSchema","true").option("delimiter",";")csv("C:/Users/PC/Documents/spark-2.4.7-bin-hadoop2.7/spark-2.4.7-bin-hadoop2.7/bin/bank-full.csv")

//transform the categorical data into numeric so we can index it
val labelIndexer = new StringIndexer().setInputCol("loan").setOutputCol("indexedLabel").fit(data)
val indexed = labelIndexer.transform(data).drop("loan").withColumnRenamed("indexedLabel", "label") 
val assembler = (new VectorAssembler().setInputCols(Array("age", "balance", "day", "duration", "previous")).setOutputCol("features"))
val features = assembler.transform(indexed)
val filter = features.withColumnRenamed("loan", "label")

val feature_data = filter.select("label", "features")

//we index the labels with the columns and use the function fit now with feature_data
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(feature_data)
// we now set the input, output and max categories with featuredata
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(feature_data)
// Split and save the data into training and test data, going with 70% and 30%
val Array(trainingData, testData) = feature_data.randomSplit(Array(0.7, 0.3))

// Train a DecisionTreeClassifier model.
val dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")

// Convert indexed labels back to normal.
val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)

// Chain indexers and tree in a Pipeline.
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))

// Train the model, this also runs the indexers.
val model = pipeline.fit(trainingData)

// Make the predictions.
val predictions = model.transform(testData)

// Select example rows to display. 
predictions.select("predictedLabel", "label", "features").show(5)

// Select (prediction, true label)
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
// Compute the test error.
val accuracy = evaluator.evaluate(predictions)
println(s"accuracy = ${(accuracy)}")

// Show by stages the classification of the tree model
val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
println(s"Learned classification tree model:\n ${treeModel.toDebugString}")

//print the total time the program executed
val totalTime = System.currentTimeMillis - start
println(s"Total time:\n  ${totalTime} ms")
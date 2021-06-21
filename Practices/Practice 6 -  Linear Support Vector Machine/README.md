# Decision tree


``` scala
// The following example loads a data set and divides it into test and training sets.

//  Import the necessary libraries
import  org.apache.spark.ml.Pipeline 
import  org.apache.spark.ml.classification.DecisionTreeClassificationModel 
import  org.apache.spark.ml.classification.DecisionTreeClassifier 
import  org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator 
import  org.apache .spark.ml.feature. { IndexToString ,  StringIndexer ,  VectorIndexer }
import  org.apache.spark.sql.SparkSession

// Load the data stored in LIBSVM format as a DataFrame.
// It is implemented for optimization, it supports classification and regression.
val data = spark.read.format("libsvm").load("/home/eduardo/Escritorio/expo/sample_libsvm_data.txt")

// Index tags, adding metadata to the tag column.
// Fit in the entire dataset to include all labels in the index.
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(data)

// Automatically identify categorical features and index them.
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(data)

// Divide the data into test and training sets (30% reserved for testing).
val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

// Train a DecisionTree model.
val dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")

// Convert indexed tags back to original tags.
val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)

// Chain of indexers and tree in a Pipeline.
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))

// Train model. This also runs the indexers.
val model = pipeline.fit(trainingData)

// Make predictions.
val predictions = model.transform(testData)

// Select sample rows to display. In this case there will only be 5
predictions.select("predictedLabel", "label", "features").show(10)

// Seleccione (predicción, etiqueta verdadera).
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")

// Select (prediction, true label).
val accuracy = evaluator.evaluate(predictions)
println(s"Test Error = ${(1.0 - accuracy)}")

// Show by stages the classification of the tree model
val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
println(s"Learned classification tree model:\n ${treeModel.toDebugString}")

``` 



// We import the libraries we occupy
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{GBTClassificationModel, GBTClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}

// We load the txt file of the established path
val data = spark.read.format("libsvm").load("sample_libsvm_data.txt")


// We will create a column using stringIndexer so that the data has its categorization
val labelIndexer = new StringIndexer()
  .setInputCol("label")
  .setOutputCol("indexedLabel")
  .fit(data)
  
// We create a vector that will have a maximum of 4 categories
val featureIndexer = new VectorIndexer()
  .setInputCol("features")
  .setOutputCol("indexedFeatures")
  .setMaxCategories(4)
  .fit(data)

// We separate the data into two parts, one called training with 70% and the other mushroom test with 30% 
val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

//Se entren al modelo GPT
val gbt = new GBTClassifier()
  .setLabelCol("indexedLabel")
  .setFeaturesCol("indexedFeatures")
  .setMaxIter(10)
  .setFeatureSubsetStrategy("auto")

//We convert the indented labels to original labels
val labelConverter = new IndexToString()
  .setInputCol("prediction")
  .setOutputCol("predictedLabel")
  .setLabels(labelIndexer.labels)

//Chain of Indenters and GPT EN Pipeline
val pipeline = new Pipeline()
  .setStages(Array(labelIndexer, featureIndexer, gbt, labelConverter))

// The model is trained. This also runs the indexers
val model = pipeline.fit(trainingData)

// We create the predictions.
val predictions = model.transform(testData)

// We select the first 5 rows to display them
predictions.select("predictedLabel", "label", "features").show(5)

// We select prediction and calculation of the test error.
val evaluator = new MulticlassClassificationEvaluator()
  .setLabelCol("indexedLabel")
  .setPredictionCol("prediction")
  .setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println(s"Test Error = ${1.0 - accuracy}")

val gbtModel = model.stages(2).asInstanceOf[GBTClassificationModel]
println(s"Learned classification GBT model:\n ${gbtModel.toDebugString}")

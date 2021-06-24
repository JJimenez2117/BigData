// Final Project
// Logistic Regression



// start the time so we can get the total time the program run
val start = System.currentTimeMillis
// Import the libraries
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.mllib.evaluation.MulticlassMetrics

// Errors are minimized
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

// Spark session is created
val spark = SparkSession.builder().getOrCreate()

// The dataframe is created with the CSV bank-full
val data = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("bank-full.csv")

// Index labels, adding metadata to the label column, fit on whole dataset to include all labels in index.
val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("indexedLabel").fit(data)
val indexed = labelIndexer.transform(data).drop("y").withColumnRenamed("indexedLabel", "label")

// The vector is created to add the fields to the array
val vectorFeatures = (new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features"))

// The vector is transformed into a new indexed variable
val features = vectorFeatures.transform(indexed)

// The label of the features field is renamed
val featuresLabel = features.withColumnRenamed("y", "label")

// A new variable is created by selecting some fields
val dataIndexed = featuresLabel.select("label","features")

// Use randomSplit to create 70/30 split test and train data
val Array(training, test) = dataIndexed.randomSplit(Array(0.7, 0.3), seed = 12345)

// The Logistic Regression is created with the parameters sent
val logisticAlgorithm = new LogisticRegression().setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8).setFamily("multinomial")

// The logistic model is trained
val logisticModel = logisticAlgorithm.fit(training)

// The precision of the test data is calculated
val results = logisticModel.transform(test)
val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

// The confusion matrix is displayed
println("Confusion matrix:")
println(metrics.confusionMatrix)

// Accuracy and error test is printed
println("Accuracy: "+metrics.accuracy) 
println(s"Test Error = ${(1.0 - metrics.accuracy)}")


val totalTime = System.currentTimeMillis - start
println(s"Total time:\n  ${totalTime} ms")

spark.stop()

// Confusion matrix:

//12170.0  0.0  
//1584.0   0.0  
//Time:8367 ms

//--------1-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:8367 ms
//--------2-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:13895 ms
//--------3-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time: 9118 ms
//--------4-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time: 16356 ms
//--------5-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time: 8061 ms
//--------6-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time: 8165 ms
//--------7-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time: 8044 ms
//--------8-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time: 8024  ms
//--------9-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time: 7946 ms
//--------10-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time: 8184 ms
//--------11-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time: 8083 ms
//--------12-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
// Time: 8128 ms
//--------13-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time: 8214 ms
//--------14-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time: 8050 ms
//--------15-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:7856 ms
//--------16--------
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------17-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------18-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------19-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------20-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------21-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------22-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------23-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------24-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------25-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//--------26-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------27-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------28-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------29-------- 
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371
//--------30--------  
// Accuracy: 0.884833502980951
//Test Error = 0.11516649701904902
//Time:16371

// Final Proyect 
// SVM (Support Vector Machines)


// start the time so we can get the total time the program run
val start = System.currentTimeMillis

//Import libs
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.classification.LinearSVC
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.Pipeline

//Minimize errors
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

//Spark session.
val spark = SparkSession.builder.appName("svm").getOrCreate()

//Load the bank-full.csv dataset
val df  = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("bank-full.csv")
df.head()
df.describe()

//Index column "y"
val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("indexedY").fit(df)
val indexed = labelIndexer.transform(df).drop("y").withColumnRenamed("indexedY", "label")

//Create a vector with the columns with numerical data and name it as features
val vectorFeatures = (new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features"))

//Use the assembler object to transform features
val featurestrans = vectorFeatures.transform(indexed)

//Rename column "y" as label
val featureslabel = featurestrans.withColumnRenamed("y", "label")

//Union of label and features as dataIndexed.
val dataindexed = featureslabel.select("label","features")
dataindexed.show()

//Creation of labelIndexer and featureIndexer for the pipeline, Where features with distinct values > 4, are treated as continuous.
val labelindexer = new StringIndexer().setInputCol("label").setOutputCol("indexedlabel").fit(dataindexed)
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedfeatures").setMaxCategories(4).fit(dataindexed)

//Training data as 70% and test data as 30%.
val Array(training, test) = dataindexed.randomSplit(Array(0.7, 0.3), seed = 1234L)

//Linear Support Vector Machine object.
val supportVM = new LinearSVC().setMaxIter(10).setRegParam(0.1)
    
//Fitting the trainingData into the model.
val modelSVM = supportVM.fit(training)

//Transforming testData for the predictions.
val predictions = modelSVM.transform(test)
predictions.show()

//Obtaining the metrics.
val predictionAndLabels = predictions.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

//Confusion matrix.
println("Confusion matrix:")
println(metrics.confusionMatrix)



//Accuracy and Test Error.
println("Accuracy: " + metrics.accuracy) 
println(s"Test Error = ${(1.0 - metrics.accuracy)}")


val totalTime = System.currentTimeMillis - start
println(s"Total time:\n  ${totalTime} ms")

spark.stop()

// Confusion matrix:

//11969.0  22.0  
//1599.0   22.0 
//Time:16371 ms
//--------1-----
//11969.0  22.0  
//1599.0   22.0  
//Time:16708 ms
//--------2-------- 
//11969.0  22.0  
//1599.0   22.0  
//Time:16335 ms
//--------3-------- 
//11969.0  22.0  
//1599.0   22.0  
//Time:17896 ms
//--------4--------
//11969.0  22.0  
//1599.0   22.0  
//Time:18313 ms
//--------5--------
//11969.0  22.0  
//1599.0   22.0  
//Time:  18219 ms
//--------6--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 19133 ms
//--------7--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 18292 ms
//--------8--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16390 ms
//--------9--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16357 ms
//--------10--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16623 ms
//--------11--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16751 ms
//--------12--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16413 ms
//--------13--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 17008 ms
//--------14--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 22744 ms
//--------15--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16388  ms
//--------16--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 24049  ms
//--------17--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16101  ms
//--------18--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16110 ms
//--------19--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16553 ms
//--------20--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16097 ms
//--------21--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16923 ms

//--------23--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16705 ms
//--------24--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16009 ms
//--------25--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 10812 ms
//--------26--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 15889  ms
//--------27--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16331 ms
//--------28--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16500 ms
//--------29--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16157 ms
//--------30--------
//11969.0  22.0  
//1599.0   22.0  
//Time: 16206 ms


//--------1-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------2-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16708 ms
//--------3-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------4-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------5-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------6-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------7-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------8-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------9-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------10-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------11-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------12-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------13-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------14-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------15-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------16--------
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------17-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------18-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------19-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------20-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------21-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------22-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------23-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------24-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------25-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------26-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------27-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------28-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------29-------- 
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371
//--------30--------  
// Accuracy: 0.8809138995004407
//Test Error = 0.11908610049955926
//Time:16371

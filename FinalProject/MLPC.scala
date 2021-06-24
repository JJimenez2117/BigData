// start the time so we can get the total time the program run
val start = System.currentTimeMillis
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.ml.feature.StringIndexer 
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

//reduce the errors
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
//start session
val spark = SparkSession.builder().getOrCreate()
//load csv
val df = spark.read.option("header", "true").option("inferSchema","true").option("delimiter",";")csv("C:/Users/PC/Documents/spark-2.4.7-bin-hadoop2.7/spark-2.4.7-bin-hadoop2.7/bin/bank-full.csv")

//transform the categorical data into numeric so we can index it
val labelIndexer = new StringIndexer().setInputCol("loan").setOutputCol("indexedLabel").fit(df)
val indexed = labelIndexer.transform(df).drop("loan").withColumnRenamed("indexedLabel", "label")
    indexed.describe().show()

//create column labels and features to avoid errors
val assembler = new VectorAssembler().setInputCols(Array("balance","day","duration","previous")).setOutputCol("features")
val  features = assembler.transform(indexed)

//We split it into 70% training and 30% test
val splits = features.randomSplit(Array(0.7, 0.3), seed = 1234L)
val train = splits(0)
val test = splits(1)

//We specified the layers of the neural network The input layer is size 4 (characteristics),two intermediate layers one size 7 and the other size 7 and 3 out (the classes)
val layers = Array[Int](4, 5, 4, 2)

//tran with MTC, setting the max iterations to 100
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)

//Train the model from the trainer
val model = trainer.fit(train)

//Get the resulting values of accuracy
val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")

// print accuracy
println(s"\n\nTest set accuracy = ${evaluator.evaluate(predictionAndLabels)}")


//print the total time the program executed
val totalTime = System.currentTimeMillis - start
println(s"Total time:\n  ${totalTime} ms")

spark.stop()
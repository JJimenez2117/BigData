import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.ml.feature.{StringIndexer, StringIndexer, VectorIndexer} 
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

//start the spark session
val spark = SparkSession.builder().getOrCreate()

//load the data from the csv

val df = spark.read.option("header", "true").option("inferSchema","true")csv("C:/Users/PC/Documents/spark-2.4.7-bin-hadoop2.7/spark-2.4.7-bin-hadoop2.7/bin/iris.csv")

//show the name of the columns
df.columns

//show the df schema
df.printSchema()

//show the df first 5 columns
df.head(5)
df.show(5)

//using the method .describe() to know more about the df
df.describe()

//transform the categorical data into numeric so we can index it
val speciesIndexer = new StringIndexer().setInputCol("species").setOutputCol("indexedSpecies").fit(df)

val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(df)
//We split it into 70% training and 30% test
val splits = features.randomSplit(Array(0.7, 0.3), seed = 1234L)
val train = splits(0)
val test = splits(1)
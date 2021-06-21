# EVALUATION PRACTICE 3
``` scala
//import spark session
import org.apache.spark.sql.SparkSession
//we minimize the errors
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
//start spark session
val spark = SparkSession.builder().getOrCreate()
//import kmeans library
import org.apache.spark.ml.clustering.KMeans
//load the dataset from the csv
val df = spark.read.option("header", "true").option("inferSchema","true")csv("C:/Users/PC/Documents/spark-2.4.7-bin-hadoop2.7/spark-2.4.7-bin-hadoop2.7/bin/Wholesale_customers_data.csv")
//select the following columns and name it features_data
val feature_data = df.select("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper", "Delicassen")
//import vector assambler and vector
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vector
//create the object assembler for the columns as input
val assembler = new VectorAssembler().setInputCols(Array("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper", "Delicassen")).setOutputCol("features")
//using the object assembler to transform feature_data
val features = assembler.transform(feature_data)
//create the kmean model with k=3
val kmeans = new KMeans().setK(3).setSeed(1L)
val model = kmeans.fit(features)
//We evaluate the groups using within set sum of squared errors WSSSE and print cluster centers
val WSSE = model.computeCost(features)
println(s"\nWithin set sum of Squared Errors = $WSSE\n")

println("Cluster Centers: ")
model.clusterCenters.foreach(println)
``` 

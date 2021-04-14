import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("Netflix_2011_2016.csv") //Finantial Crisis
df.columns
df.printSchema()
df.describe().show()
df.head(5)
val df2 = df.withColumn("HV Ratio",df("High")/df("Volume"))
df.select(max("Close")).show()
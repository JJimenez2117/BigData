import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("Netflix_2011_2016.csv") //Finantial Crisis
df.columns
df.printSchema()
df.describe().show()
df.head(5)
val df2 = df.withColumn("HV Ratio",df("High")/df("Volume"))
df.select(max("Close")).show()





//9) Escribe con tus propias palabras en un comentario de tu codigo. ¿Cuál es el
//significado de la columna Cerrar “Close”?


//Respuesta: es ultimo precio de cotizacion que se registro en el dia



//10) ¿Cuál es el máximo y mínimo de la columna “Volume”?

// maximo
df.select(max("Volume")).show()

// minimo
df.select(min("Volume")).show()
``` scala
//Basic Statistics

// Correlation

//We import the necesary libraries
import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.Row

//we are going to create a dataset with vectors
val data = Seq(
  Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),
  Vectors.dense(4.0, 5.0, 0.0, 3.0),
  Vectors.dense(6.0, 7.0, 0.0, 8.0),
  Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
)
//we use the dataframe and use the function map 
val df = data.map(Tuple1.apply).toDF("features")
//we use the pearson correlation matrix
val Row(coeff1: Matrix) = Correlation.corr(df, "features").head
println(s"Pearson correlation matrix:\n $coeff1")
//we use the spearman correlation matrix
val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
println(s"Spearman correlation matrix:\n $coeff2")


//Chi Square Test Example

// the chi square test is used to see if two variables are independent of each other
// or they are not independent


//Import libs 
import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.apache.spark.ml.stat.ChiSquareTest


//Declare a value of name data to which a sequence of dense vectors is assigned 
val data = Seq(
  (0.0, Vectors.dense(0.5, 10.0)),
  (0.0, Vectors.dense(1.5, 20.0)),
  (1.0, Vectors.dense(1.5, 30.0)),
  (0.0, Vectors.dense(3.5, 30.0)),
  (0.0, Vectors.dense(3.5, 40.0)),
  (1.0, Vectors.dense(3.5, 40.0))
)

//A new dataframe is created to which the data value is assigned in the label and features columns 
val df = data.toDF("label", "features")

//A chi value is created to which ChiSquare is applied through the libraries 
val chi = ChiSquareTest.test(df, "features", "label").head
println(s"pValues = ${chi.getAs[Vector](0)}")
println(s"degreesOfFreedom ${chi.getSeq[Int](1).mkString("[", ",", "]")}")
println(s"statistics ${chi.getAs[Vector](2)}")


//Summarizer


// Import Summarizer 
import org.apache.spark.ml.stat.Summarizer


//Data value will contain two vectors 
val data = Seq(
  (Vectors.dense(2.0, 3.0, 5.0), 1.0),
  (Vectors.dense(4.0, 6.0, 7.0), 2.0)
)

//Valor de Df para hacer referencia a "Funciones" y "peso"

val df = data.toDF("features", "weight")

//Two values for the mean and variance, Select the metrics (Mean, Variance) 
val (meanVal, varianceVal) = df.select(metrics("mean", "variance")

//Summary method that we will apply to Feactures and Weight and give it an alias 
  .summary($"features", $"weight").as("summary"))

// We select the Summary with its respective metric 
  .select("summary.mean", "summary.variance")

// We give you the way we want to print 
  .as[(Vector, Vector)].first()

//We send to printing to show the values of the Mean and Variance 
println(s"with weight: mean = ${meanVal}, variance = ${varianceVal}")

//Second Value without using the Summary method 

val (meanVal2, varianceVal2) = df.select(mean($"features"), variance($"features"))
  .as[(Vector, Vector)].first()

println(s"without weight: mean = ${meanVal2}, sum = ${varianceVal2}")

``` 

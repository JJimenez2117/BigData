# evaluation practice 1

```r
//1. we import sql SparkSession
    import org.apache.spark.sql.SparkSession

// we import the scala notation
    import spark.implicits._

/// we build the session and assign it to a variable

    val spark = SparkSession.builder().getOrCreate()

//2. we load the dataframe
// header is used to detect the first row as the name of each column
// inferSchema is used to identify the data types of the columns

    val df = spark.read.option("header", "true").option("inferSchema","true")csv("Netflix_2011_2016.csv") //Finantial Crisis


//3. show the name of the columns

    df.columns


//4. we consult the schema, the data types

    df.printSchema()

//5. Use describe () to learn from the dataFrame

    // returns a DataFrame containing information such as the number of
    // Non-null entries (count), the mean, the standard deviation, and the minimum and maximum value for each numeric column.

    df.describe().show()

//6. Print the first 5 columns.
    df.select($"Date", $"Open", $"High", $"Low", $"Close").show()

    df.head(5)


//7.Create a new dataframe with a new column called "HV Ratio" which is the
// relationship between the price of the column "High" versus the column "Volume" of
// shares traded for one day. (Hint: It is a column operation).

    val df2 = df.withColumn("HV Ratio",df("High")/df("Volume"))
    


//8. ¿Qué día tuvo el pico mas alto en la columna “Close”?

    df.select(max("Close")).show()


//9) write in your own words in a comment of your code. Which is the
// meaning of the Close column “Close”?


    // Answer: it is the last quote price that was registered on the day



//10) What is the maximum and minimum of the “Volume” column?


    // maximo
    df.select(max("Volume")).show()

    // minimo
    df.select(min("Volume")).show()

//11.With Scala / Spark $ syntax answer the following:


    // a How many days was the “Close” column less than $ 600?

        df.filter($"Close"<600).count()

    // b. What percentage of the time was the “High” column greater than $ 500?


        df.filter($"High">500).count()*1.0/df.count()*100

    // c. What is the Pearson correlation between column "High" and column "Volume"?

        df.select(corr("High","Volume")).show()

    // d. What is the maximum in the “High” column per year?


        // We create a dataframe with the dataframe df but adding a new column called
        // Year which will only contain the years present in the date column

        val dfyear = df.withColumn("Year",year(df("Date")))

        // We create another dataframe based on the dfyear dataframe selecting the columns "Year", "High" grouped by the maximum of each year

        val maxsyear = dfyear.select($"Year",$"High").groupBy("Year").max()

        // we do the query of the dataframe selecting only the columns Year and max (High)

        maxsyear.select($"Year",$"max(High)").show()

    //e. What is the “Close” column average for each calendar month?


        // We create a dataframe from the main dataframe called df but adding a new one
        // column that will contain the months present in the Date column
        val dfmonth = df.withColumn("Month",month(df("Date")))

        // We create a new dataframe with the Month and Close columns present in the previous dataframe
        // and we group them by the average of each month
        val monthpromedio = dfmonth.select($"Month",$"Close").groupBy("Month").mean()
        
        // we make the query

        monthpromedio.select($"Month",$"avg(Close)").show()
```
# Random Forest Classifier


``` scala
// Import libraries
importar  org . apache . chispa . ml . Tubería
importar  org . apache . chispa . ml . clasificación . DecisiónÁrbolClasificaciónModelo
importar  org . apache . chispa . ml . clasificación . DecisionTreeClassifier
importar  org . apache . chispa . ml . evaluación . Evaluador de clasificación multiclase
importar  org . apache . chispa . ml . función . { IndexToString , StringIndexer , VectorIndexer }
importar  org . apache . chispa . sql . SparkSession

// Creating the main function
object  DecisionTreeClassificationExample {
  def  main () :  Unidad  = {
    val  spark  =  SparkSession
      .constructor
      .appName ( " DecisionTreeClassificationExample " )
      .getOrCreate ()

    // Load the data stored in LIBSVM format as a DataFrame.
    val  data  = spark.read.format ( " libsvm " ) .load ( " sample_libsvm_data.txt " )

    // Index tags, adding metadata to the tag column.
    // Fit in the entire dataset to include all labels in the index.
    val  labelIndexer  =  nuevo  StringIndexer ()
      .setInputCol ( " etiqueta " )
      .setOutputCol ( " indexedLabel " )
      .fit (datos)
      
    // Automatically identify categorical features and index them.
    val  featureIndexer  =  new  VectorIndexer ()
      .setInputCol ( " características " )
      .setOutputCol ( " indexedFeatures " )
      .setMaxCategories ( 4 ) // las entidades con> 4 valores distintos se tratan como continuas.
      .fit (datos)

    // Divide the data into test and training sets (30% reserved for testing).
    val  Array (trainingData, testData) = data.randomSplit ( Array ( 0.7 , 0.3 ))

    // Train a DecisionTree model.
    val  dt  =  nuevo  DecisionTreeClassifier ()
      .setLabelCol ( " indexedLabel " )
      .setFeaturesCol ( " indexedFeatures " )

    // Convert indexed tags back to original tags.
    val  labelConverter  =  nuevo  IndexToString ()
      .setInputCol ( " predicción " )
      .setOutputCol ( " etiqueta predicha " )
      .setLabels (labelIndexer.labels)

    // Chain of indexers and tree in a Pipeline.
    val  pipeline  =  new  Pipeline ()
      .setStages ( Array (labelIndexer, featureIndexer, dt, labelConverter))

    // Train model. This also runs the indexers.
    val  model  = pipeline.fit (trainingData)

    // Make predictions.
     predicciones de  val = model.transform (testData)

    // Select sample rows to display.
    predictions.select ( " predictedLabel " , " etiqueta " , " características " ) .show ( 5 )

    // Select (prediction, true label) and calculate the test error.
    val  evaluador  =  new  MulticlassClassificationEvaluator ()
      .setLabelCol ( " indexedLabel " )
      .setPredictionCol ( " predicción " )
      .setMetricName ( " precisión " )
    val  precisión  = evaluador.evaluar (predicciones)
    println ( s " Error de prueba = $ {( 1.0  - precisión)} " )

    val  treeModel  = model.stages ( 2 ). asInstanceOf [ DecisionTreeClassificationModel ]
    println ( s " Modelo de árbol de clasificación aprendido: \ n  $ {treeModel.toDebugString} " )
    // $ ejemplo de descuento $

    Spark.stop ()
  }
}

``` 

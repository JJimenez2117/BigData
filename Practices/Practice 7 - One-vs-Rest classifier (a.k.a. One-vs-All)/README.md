# One vs Rest classifier


``` scala
// Import Libraries
importar  org . apache . chispa . ml . clasificación . { LogisticRegression , OneVsRest }
importar  org . apache . chispa . ml . evaluación . Evaluador de clasificación multiclase

// Upload the file
val  inputData  = spark.read.format ( " libsvm " ) .load ( " sample_multiclass_classification_data.txt " )

// Generate the division of the train and test set.
val  Array (tren, prueba) = inputData.randomSplit ( Array ( 0.8 , 0.2 ))

// Instantiate the base classifier
val  clasificador  =  new  LogisticRegression () .setMaxIter ( 10 ) .setTol ( 1E-6 ) .setFitIntercept ( verdadero )

// An instance of the One Vs Rest classifier is created.
val  ovr  =  new  OneVsRest () .setClassifier (clasificador)

// Train (train) the multiclass model.
val  ovrModel  = ovr.fit (tren)

// The model is scored on the test data.
 predicciones  val = ovrModel.transform (prueba)

// You get the evaluator
val  evaluator  =  new  MulticlassClassificationEvaluator () .setMetricName ( " precisión " )

// The classification error in the test data is calculated.
val  precisión  = evaluador.evaluar (predicciones)
println ( s " Error de prueba = $ { 1  - precisión} " )

``` 

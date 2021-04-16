//Practice 2
// 1. Crea una lista llamada "lista" con los elementos "rojo", "blanco", "negro"
var lista=List("rojo","blanco","negro")

// 2. Añadir 5 elementos mas a "lista" "verde" ,"amarillo", "azul", "naranja", "perla
var temp1= "verde" :: lista
var temp2= "amarillo" :: temp1
var temp3= "azul" :: temp2
var temp4= "naranja" :: temp3
var listafinal= "perla" :: temp4

// 3. Traer los elementos de "lista" "verde", "amarillo", "azul"
listafinal.slice(2,5)

// 4. Crea un arreglo de numero en rango del 1-1000 en pasos de 5 en 5
var arreglo=Array(Range(1,1001,5))

// 5 What are the unique elements of the list List (1,3,3,4,6,7,3,7) use conversion to sets
val myList = List(1,3,3,4,6,7,3,7)
myList.toSet

// 6 Create a mutable map named names that contains the following
// "Jose", 20, "Luis", 24, "Ana", 23, "Susana", "27"
val mutmymap = collection.mutable.Map(("Jose",20),("Luis",24),("Ana", 23),("Susana", "27"))

// 6 a. Print all keys on the map
mutmymap.keys

// 7 b. Add the following value to the map ("Miguel", 23)
mutmymap += ("Miguel" -> 23)


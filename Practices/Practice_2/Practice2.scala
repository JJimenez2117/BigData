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
# Practica_1
``` scala

//1. Desarrollar un algoritmo en scala que calcule el radio de un circulo
    
  var diametro: Double=4
  var radio: Double=0

  radio=diametro/2


//2. Desarrollar un algoritmo en scala que me diga si un numero es primo
var numero: Int = 7 
var primo: Boolean = true

for(contador <- Range(2,numero))
{
    if ((numero % contador) == 0)
    {
        primo = false
    }
}

if (primo == true)
{
    println("Es primo")
}else
{
    println("No es primo")
}

/*3. Dada la variable bird = "tweet", utiliza interpolacion de string para
   imprimir "Estoy ecribiendo un tweet"*/

var bird: String = "tweet"
println(s"Estoy escribiendo un ${bird}")

/*3. Dada la variable bird = "tweet", utiliza interpolacion de string para
   imprimir "Estoy ecribiendo un tweet"*/

var bird: String = "tweet"
println(s"Estoy escribiendo un ${bird}")

// 4. Given the variable message = "Hello Luke, I am your father!" uses slilce to extract the
val message = "Hola Luke yo soy tu padre"
message slice  (5,10)

//5. What is the difference between value and a variable in scala?
// the object assigned to a val cannot be replaced, while an object assigned to a var can, that is, one is immutable and the other is not

// 6. Given the tuple (2,4,5,1,2,3,3.1416,23) returns the number 3.1416

val my_tup = (2,4,5,1,2,3,3.1416,23)

my_tup._7
``` 
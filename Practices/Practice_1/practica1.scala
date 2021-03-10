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



// Exercise 1
// En este ejercicio se crea una funcion llamada listEvens la cual recibira como parametro una lista de enteros 
// y devolvera un string, dentro de la funcion se encuentra un ciclo for que recorrera la lista y cuenta con la 
// variable n que en cada iteracion tomara el un valor entero presente en cada posicion de la lista,
// dentro del for existe una condicional if que evalura si el resto de dividir n entre dos es igual a cero, de 
// ser cierto imprimira es par de lo contrario imprimira es impar, una vez terminado de recorrer cada elemento 
// de la lista, retornara  "Done".

// In this exercise a function called listEvens is created which will receive as a parameter a list of integers
// and it will return a string, inside the function there is a for loop that will go through the list and has the
// variable n that in each iteration will take an integer value present in each position of the list,
// inside the for there is an if conditional that evaluates if the remainder of dividing n by two is equal to zero,
// to be true it will print it is even otherwise it will print it odd, once it has finished going through each element
// from the list, it will return "Done".

def listEvens(list:List[Int]): String ={
    for(n <- list){
        if(n%2==0){
            println(s"$n is even")
        }else{
            println(s"$n is odd")
        }
    }
    return "Done"
}

val l = List(1,2,3,4,5,6,7,8,10,20,33,108,333)

listEvens(l)

//example print
//1 is odd
//2 is even
//3 is odd
//4 is even
//5 is odd
//6 is even
//7 is odd
//8 is even
//10 is even
//20 is even
//33 is odd
//108 is even
//333 is odd
//res7: String = Done


// Exercise 2  afortunado
// en el siguiente fragmento de codigo se declara una funcion llamada afortudado
// en la cual recibira una lista de enteros como parametro y returnara un valor entero,
// dentro de la funcion se declara una variable mutable y le asignamos el valor 0,
// y posteriomente nos encontramos un ciclo for que recorrera la lista y a la variable n
// se le asignara cada elemento presente en la lista, depues encontramos una condicional if
// que evaluara si n es igual a 7 de ser correcto a la variable res se le sumara 14 y si no 
// es cierto se le asigra n, al terminar todas las teracciones se devolvera el valor de res.

// Exercise 2  afortunado
// in the following code snippet a function called lucky is declared
// in which it will receive a list of integers as a parameter and return an integer value,
// inside the function a mutable variable is declared and we assign the value 0,
// and later we find a for loop that will go through the list and the variable n
// each element present in the list will be assigned, then we find an if conditional
// that will evaluate if n is equal to 7 if it is correct to the variable res it will be added 14 and if not
// it is true it is assigned n, when finishing all the interactions the value of res will be returned.

def afortunado(list:List[Int]): Int={
    var res=0
    for(n <- list){
        if(n==7){
            res = res + 14
        }else{
            res = res + n
        }
    }
    return res
}

val af= List(1,7,7)
println(afortunado(af))

//example print
// 29
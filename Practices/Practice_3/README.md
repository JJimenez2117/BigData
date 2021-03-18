# Practice_3
``` scala 
//version1
def fiborecur(n:Int): Int={
    if(n<2)
        return n
    else{
        return fiborecur(n-1)+fiborecur(n-2)
    }
}
}
fiborecur(8)

//version2
var l:Double=((1+Math.sqrt(5))/2)
var j:Double=0
def fiboexp(n:Double):Double={
    if(n<2)
        return n
    else{
        j=((Math.pow(l,n)-Math.pow((1-l),n))/(Math.sqrt(5)))
        return j
    }
}

fiboexp(15)

//Version 3
def fib(n:Int):Int={
    
    var a = 0;
    var b = 1;
    var c = 0;
     
    for ( k <- 0 to n) 
    {
        c = b+a
        a = b
        b = c

        return a
    }

}

fib(100)

// Version 4

def fib (n:Int):Int={

    var a = 0
    var b = 1
    for ( k <- 0 to n) 
    {
        b = b + a
        a = b - a

        return b
    }

}

// Version 5
def fib (n: Int): Int{=

    if (n < 2)
    {
        return n
    }else
    {
        var  nums = new Array[int](n+1) 
        var  nums(0) = 0
        var  nums(1) = 1 

        for ( k <- 2 to n+1) 
        {
            nums(k) = nums(k-1) - nums(k-2)

        return nums(n)
        }
    }
}


//version6

import util.control.Breaks._
var i:Double=0
var a:Double=0
var b:Double=0
var c:Double=0
var d:Double=0
var auxOne:Double=0
var auxTwo:Double=0
var tempab=List(a,b)
var tempcd=List(c,d)
def fibdiv(n:Double):Double={
    if(n<=0)
        return 0
    else{
        i=(n-1)
        auxOne=0
        auxTwo=1
        a=auxTwo
        b=auxOne
        c=auxOne
        d=auxTwo
        
        while(i>0){           
            if(i%2!=0){
                auxOne=((d*b)+(c*a))
                auxTwo=(d*(b+a)+(c*b))
                a=auxOne
                b=auxTwo
            }
            else{
                auxOne=((d*b)+(c*a))
                auxTwo=(d*(b+a)+(c*b))
                c=auxOne
                d=auxTwo
                i=(i/2)

            }

        }
        if(i<=0) break
        return (a+b)

    }

}
fibodiv(8)



```

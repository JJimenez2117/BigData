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

```

package com.trch.lambdas

object ImplicitClassApp extends App {

  implicit class FunctionMathmatics[T](val function: Function1[T,T]) {
    
    def *(amount:Int) = {
      def *(amount:Int, current : T => T) : Function1[T, T] = amount match {
        case 0 => current
        case _ => *(amount-1, union(function, current))
      }
      *(amount, x => x)
    }
    
    def +[S](that : T => S) = (x:T) => that(function(x))  
    
    private def union[T](func1 : T => T, func2 : T => T) : Function1[T,T] = (x:T) => func1(func2(x))
    
  }
  
  //could be useful for unit testing
  val increment = (x:Int) => x + 1
  val increment100 = increment * 100
  println(increment100(1)) //100
  
  val appendA = (x:String) => x + "A" 
  val add100As = appendA * 100
  println(add100As("Hol"))
  
  //make a pipeline of tasks
  val pipeline = ((x : Int) => x + 100) + ((x : Int) => "the number is " + x)
  println(pipeline(100))
  
  
}
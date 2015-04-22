package com.trch.implicits

/**
 * Created by thomas hutchinson on 4/6/15.
 */

trait FunctionImplicits {

  implicit class Function1SingleTypeMath[T](val function: Function1[T,T]) {

    def *(amount: Int) = {
      def *(amount: Int, current: T => T): Function1[T, T] = amount match {
        case 0 => current
        case _ => *(amount - 1, union(function, current))
      }
      *(amount, x => x)
    }

    def ++ = (x:T) => function(function(x))

    private def union[T](func1: T => T, func2: T => T): Function1[T, T] = (x: T) => func1(func2(x))
  }

  implicit class Function1Math[T,S](val function: Function1[T,S]) {
    def +[W](that : S => W) = (x:T) => that(function(x))
  }

}

object ImplicitClassApp extends App with FunctionImplicits {


    //def +[S](that : T => S) = (x:T) => that(function(x))

  //could be useful for unit testing
  val increment = (x:Int) => x + 1
  val increment100 = increment * 100
  println(increment100(1)) //100
  
  val appendA = (x:String) => x + "A" 
  val add100As = appendA * 100
  println(add100As("Hol"))

  println((increment++)(1))

  //make a pipeline of tasks
  val pipeline = ((x : Int) => System.currentTimeMillis() - x) + ((x : Long) => x.toHexString) + ((x : String) => "result => " + x)
  
  //see the power of type inference, we don't need to declare parameters types in the lambdas after the first
  val pipeline2 = ((x : Int) => System.currentTimeMillis() - x) + (x => x.toHexString) + (x => "result => " + x)
  
  println(pipeline(1))
  println(pipeline2(1))


  
  
}
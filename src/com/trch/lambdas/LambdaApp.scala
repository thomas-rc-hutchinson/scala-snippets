package com.trch.lambdas

/**
 * Created by thomas hutchinson on 4/6/15.
 */
object LambdaApp extends App {

  val square = (x:Int) => x * x
  println(square(6))//36
  
  val increment = (x:Int) => x + 1
  println(increment(6))//7
  
  val composition = union(square, increment)
  println(composition(6))//49
  
  //type inference, don't need to declare type of x
  println(union(x => x + 100, x => x + 200)(0))//300
  
  def union(func1 : Int => Int, func2 : Int => Int) = (x:Int) => func1(func2(x))
  
  
}
package com.trch.closure

/**
 * Created by thomash on 4/6/15.
 */
object ClosureApp extends App {

  def ifTrueElse[T](condition : Boolean, trueFunc : => T, falseFunc : => T) = if(condition) trueFunc else falseFunc
  

  lazy val millis = System.currentTimeMillis()

  val result = ifTrueElse(millis % 2 == 0, {
    "Current millis (" + millis + ") is even"
  }, {
    "Current millis (" + millis + ") is odd"
  })

  println(result)




}

package com.trch.implicits

import java.util.{Calendar, Date}

/**
 * Created by thomas hutchinson on 4/6/15.
 */

trait Millis[T] {
  def millis(value:T) : Long
}

case class AnotherDateClass(val millis:Long)

trait MillisImplicits {

  implicit val javaDateToMillis = new Millis[java.util.Date] {
    def millis(value:java.util.Date) = value.getTime
  }

  implicit val javaCalendarToMillis = new Millis[java.util.Calendar] {
    def millis(value:java.util.Calendar) = value.getTimeInMillis
  }

  implicit val anotherDateClassToMillis = new Millis[AnotherDateClass] {
    def millis(value:AnotherDateClass) = value.millis
  }
}



object ImplicitClassDateApp extends App with MillisImplicits {


  println(compare(new Date, Calendar.getInstance))
  println(compare(Calendar.getInstance, new Date))
  println(compare(new Date, new AnotherDateClass(System.currentTimeMillis())))


  def compare[T:Millis,S:Millis](time:T,time2:S) : Long = {
    val millis1 = implicitly[Millis[T]].millis(time)
    val millis2 = implicitly[Millis[S]].millis(time2)
    millis1.compareTo(millis2)
  }

}

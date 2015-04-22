package com.trch.future

import scala.util.{Failure, Success}
import dispatch._, Defaults._
/**
 * Created by thomash on 4/22/15.
 */

object IntermediateFutureApp extends App {

  //HTTP GET and orders by line size
  Future.sequence(
    List("http://www.google.com.gi", "https://www.yahoo.com/")
      .map(address => Http(url(address) OK as.String)
      .map(response => (address, response.length)) ))
  .onComplete{
      case Success(response) => response.sortWith((r1,r2) => r1._2 < r2._2).foreach(println)
      case Failure(error) => error.printStackTrace()
  }




}

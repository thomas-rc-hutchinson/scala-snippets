package com.trch.future

import scala.util.{Failure, Success}
import dispatch._, Defaults._
/**
 * Created by thomash on 4/22/15.
 */

object IntermediateFutureApp extends App {

  //HTTP GET and orders by line size
  def lineCount = Future.sequence(
    List("http://www.google.com.gi", "https://www.yahoo.com/")
      .map(address => Http(url(address) OK as.String)
      .map(response => (address, response.length)) ))
  .onComplete{
      case Success(response) => response.sortWith((r1,r2) => r1._2 < r2._2).foreach(println)
      case Failure(error) => error.printStackTrace()
  }


  def weather(location:String) = s"http://api.openweathermap.org/data/2.5/weather?q=${location}&mode=xml"
  def temperature(node:scala.xml.Node) = (node \\ "temperature").headOption.get.attribute("max").get.text.toDouble

  //will return a http 511 if run to many times.
  def countryTemperatures = Future.sequence(List("Gib", "London", "Madrid", "Berlin", "Coppenhagen")
    .map(address => Http(url(weather(address)) OK as.xml.Elem)
    .map(xml => (address, temperature(xml))))
  )
  .onComplete{
    case Success(response) => response.sortWith((r1,r2) => r1._2 > r2._2).foreach(println)
    case Failure(error) => error.printStackTrace()
  }



  lineCount
  countryTemperatures




}

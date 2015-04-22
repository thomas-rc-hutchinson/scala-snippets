package com.trch.future

import java.lang.management.ManagementFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * Created by thomash on 4/22/15.
 */
object SimpleFutureApp extends App {

  Future {
    10 * 10
  }.map(result => s"Result is $result").onComplete(println(_))


  //Gets all thread ids, looks up the ThreadInfo (concurrent) associated with each id and prints results to console
  lazy val threadMxBean = ManagementFactory.getThreadMXBean

  Future {
    threadMxBean.getAllThreadIds.toList
  }.flatMap(threadIds => Future.sequence(threadIds.map(id => Future {
    threadMxBean.getThreadInfo(id)
  }))).onComplete {
    case Success(threadIds) => threadIds.foreach(println)
    case Failure(failure) => println("Error occured " + failure.getMessage)
  }



  Thread.sleep(10000)

}

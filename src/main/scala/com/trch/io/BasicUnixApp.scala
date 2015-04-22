package com.trch.io
/**
 * Created by thomash on 4/22/15.
 */
import java.io.File

import scala.sys.process._

object BasicUnixApp extends App {

  lazy val thisSource  = new File("").getAbsolutePath + "/src/com/trch/io/BasicUnixApp.scala"

  val grep : ProcessBuilder = Seq("grep", "grep", thisSource)

  grep.lines.foreach(result => println(s"We just got grepped : $result"))

  val curl : ProcessBuilder = Seq("curl", "http://www.google.com")
  curl.lines.foreach(println(_))

  val tail : ProcessBuilder = Seq("tail", thisSource)
  tail.lines.foreach(println(_))

}

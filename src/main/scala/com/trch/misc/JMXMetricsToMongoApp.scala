package com.trch.misc

import java.lang.management.{ManagementFactory, MemoryMXBean}
import javax.management.MBeanServerConnection
import javax.management.remote.{JMXConnectorFactory, JMXServiceURL}

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.TypeImports.DBObject
import com.mongodb.casbah.{MongoClient, MongoCollectionBase}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
/**
 * Created by thomash on 4/23/15.
 */
object JMXMetricsToMongoApp extends App{

  def jxmConnection(address:String) : Future[MBeanServerConnection] = Future {
    JMXConnectorFactory.connect(new JMXServiceURL("service:jmx:rmi:///jndi/rmi://%s/jmxrmi".format(address)), null).getMBeanServerConnection
  }

  def memory(connection: MBeanServerConnection) : Future[MemoryMXBean] = Future {
    ManagementFactory.newPlatformMXBeanProxy(connection, ManagementFactory.MEMORY_MXBEAN_NAME,
      classOf[MemoryMXBean])
  }

  def memoryMongoObject(host:String, memoryMXBean: MemoryMXBean) = {
    val heap = memoryMXBean.getHeapMemoryUsage
    MongoDBObject("host" -> host, "init" -> heap.getInit, "commited" -> heap.getCommitted,  "max" -> heap.getMax, "used" -> heap.getUsed, "timestamp" -> System.currentTimeMillis())
  }


  def writeToMongo(db : MongoCollectionBase, mongoDBObjects: List[DBObject]) = Future {
     db.insert(mongoDBObjects : _*)
  }

  val hosts = List() //host:port e.g. 127.0.0.1:8080
  val mongo = MongoClient("localhost", 27017)
  val db = mongo("snippets")("hosts")


  //Gets heap memory usage and writes to a mongodb
  def extractJVMMetrics = Future.sequence(
    hosts.map(host =>  jxmConnection(host).
      flatMap(connection => memory(connection))
      .map(memoryBean => memoryMongoObject(host, memoryBean)))
  ).flatMap(dbObjects => writeToMongo(db, dbObjects)).onComplete {
    case Success(result) => db.find().foreach(println(_))
    case Failure(failure) => failure.printStackTrace
  }

  def clearDB = Future { db.remove(MongoDBObject.empty) }

  clearDB.onComplete({
    case Success(success) => extractJVMMetrics
    case Failure(exception) => exception.printStackTrace
  })


  Thread.sleep(10000)





}

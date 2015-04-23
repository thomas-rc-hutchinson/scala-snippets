package com.trch.db

/**
 * Created by thomash on 4/23/15.
 */

import com.mongodb.casbah.Imports._

object SimpleMongoDBApp extends App {

  //demonstrates how to connect to a db, dump some data and read it

  val mongo = MongoClient("localhost", 27017)
  val db = mongo("snippets")("animals") //currying


  def write = {
    val dog = MongoDBObject("name" -> "bandit")
    val cat = MongoDBObject("name" -> "smudge")
    db.insert(dog,cat)
  }

  def read = {
    val cursor = db.find()
    for(animal <- cursor) println(animal)
  }

  def query = {
    val option : Option[DBObject] = db.find(db => db.get("name").equals("bandit"))
    option match {
      case Some(dbObj) => println(s"We have a result! $dbObj")
      case None => println("Nothing found")
    }
  }

  write
  read
  query


}

package com.trch.regex

/**
 * Created by thomash on 4/6/15.
 */


class EmailRegexApp {

  val Email = """(\w+)@([\w\.]+)""".r

  def isEmailAddress(address: String): Boolean = address match {
    case Email(_, _) => true
    case _           => false
  }

  isEmailAddress("foo@foo.com")

}

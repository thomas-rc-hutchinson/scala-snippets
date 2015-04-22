package com.trch.regex

/**
 * Created by thomash on 4/6/15.
 */


class EmailRegexApp {

  val EmailAddress = """(\w+)@([\w\.]+)""".r

  def parse(address: String): (String,String) = address match {
    case EmailAddress(name, domain) => (name, domain)
  }

  val name,host = parse("foo@foo.com")
  println(name)
  println(host)

}

package com.trch.lambdas

/**
 * Created by thomash on 4/6/15.
 */
object SetApp extends App {

  type Set = Int => Boolean

  val one = singletonSet(1)
  assert(one(1))

  val two = singletonSet(2)
  val onetwo = union(one, two)
  assert(onetwo(1))
  assert(onetwo(2))
  assert(onetwo(0) == false)

  val copyone = singletonSet(1)
  val oneone = intersection(one, copyone)
  assert(oneone(1))
  assert(oneone(2) == false)

  val onetwodiff = diff(one, two)
  assert(onetwodiff(1))
  assert(onetwodiff(2) == false)

  val excludeMinusOneFilter = filter(one, x => x != 0)
  assert(excludeMinusOneFilter(-1) == false)

  val set = createSet(-1000,1000)
  assert(forall(set, x => x == -100) == false)
  assert(exists(set, x => x == -100))

  val multipltytwoset = map(union(singletonSet(6), singletonSet(18)), x => x / 2)
  assert(multipltytwoset(12))
  assert(multipltytwoset(36))
  assert(multipltytwoset(6) == false)
  assert(multipltytwoset(18) == false)


  assert(one(1))
  val i = map(one, x => x - 1)
  assert(i(2))



  def contains(s: Set, elem: Int): Boolean = s(elem)
  def singletonSet(elem: Int): Set = (x: Int) => x == elem
  def emptySet(): Set = (x: Int) => false

  def union(s: Set, t: Set): Set = (x: Int) => contains(s, x) || contains(t, x)
  def intersection(s: Set, t: Set): Set = (x: Int) => contains(s, x) && contains(t, x)
  def diff(s: Set, t: Set): Set = (x: Int) => contains(s, x) && !contains(t, x)

  def filter(s: Set, p: Int => Boolean): Set = diff(s, p)

  def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(x: Int): Boolean = {
      if (x == 1000) true
      else if (contains(s, x) && !contains(p,x)) false
      else iter(x+1)
    }
    iter(-1000)
  }


  def exists(s: Set, p: Int => Boolean): Boolean = forall(p, s)
  def map(s : Set, f : Int => Int) : Set = (x : Int) => s(f(x)) //f should apply the reverse to the transformation you want e.g. if you want to add 1 you would minus 1


  def createSet(from : Int, to : Int) : Set = List.range(from+1,to).map(singletonSet(_)).fold(singletonSet(from))((x, y) => union(x,y))



}

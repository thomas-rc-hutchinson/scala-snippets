package com.trch.railway

import com.trch.railway.TypeConverter.{ValueNotCast, ValueCast, CastResult}
import org.apache.commons.lang.math.NumberUtils


/**
 * Created by thutchi on 20/08/15.
 */
class TypeConverter {

  def bind(cast:String => CastResult) : (CastResult => CastResult) = (input:CastResult) => input match {
    case ValueCast(value) => ValueCast(value)
    case ValueNotCast(value) => cast(value)
  }

  private def buildCastFunction : String => Any = {
    val isDigitFun : String => CastResult = castIfInt
    val isDoubleFun : CastResult => CastResult = bind(castIfDouble)
    val isBooleanFun : CastResult => CastResult = bind(castIfBoolean)
    val asStringFun : CastResult => Any = asString

    val intThenDouble = isDoubleFun.compose(isDigitFun)
    val intThenDoubleThenBoolean = isBooleanFun.compose(intThenDouble)
    val intThenDoubleThenBooleanFallbackToString = asStringFun.compose(intThenDoubleThenBoolean)

    intThenDoubleThenBoolean
  }


  val caster : String => Any = buildCastFunction

  def cast(value:String) = caster(value)


  def castIfInt(value:String) : CastResult = NumberUtils.isDigits(value) match {
    case true => ValueCast(value.toInt)
    case false => ValueNotCast(value)
  }

  def castIfDouble(value:String) : CastResult = {
    val decIndex = value.indexOf(".")

    if(decIndex == -1){
      return ValueNotCast(value)
    }

    val left = value.substring(0, decIndex)
    val right = value.substring(decIndex+1, value.size)

    //If both sides are digits the value is a double
    NumberUtils.isDigits(left) && NumberUtils.isDigits(right) match {
      case true => ValueCast(value.toDouble)
      case false => ValueNotCast(value)
    }
  }

  def castIfBoolean(value:String) = "true".equals(value) || "false".equals(value) match {
    case true => ValueCast(value.toBoolean)
    case false => ValueNotCast(value)
  }

  def asString(result:CastResult) : Any = result.asInstanceOf[ValueNotCast].value

}
object TypeConverter {
  abstract class CastResult
  case class ValueCast(value:Any) extends CastResult
  case class ValueNotCast(value:String) extends CastResult
}

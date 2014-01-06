package de.choffmeister.scalaxbissue

import org.specs2.mutable._
import scala.xml._
import scalaxb._

class IssueSpec extends SpecificationWithJUnit {
  "scalaxb" should {
    "load mixed value content of tExpression" in {
      val model = fromXML[TDefinitions](XML.load(this.getClass.getResourceAsStream("/test.bpmn20.xml")))
      model.rootElement must haveSize(1)

      val process = singleOrNone(find[TProcess](model))
      process must beSome

      val userTask = singleOrNone(find[TUserTask](model))
      userTask must beSome

      val loopCardinality = userTask.get.loopCharacteristics.get.value.asInstanceOf[TMultiInstanceLoopCharacteristics].loopCardinality.get

      loopCardinality.toString must contain("12")
      loopCardinality.toString must contain("34")
    }
  }

  def findAll(model: TDefinitions) = find[TBaseElement](model)

  def findById(model: TDefinitions, id: String) = find[TBaseElement](model, be => be.id == Some(id))

  def find[T : Manifest](e: Any, predicate: T => Boolean = (result: T) => true): Seq[T] = {
    val recursion = e match {
      case Some(v) => find(v, predicate)
      case None => Nil
      case seq: Seq[Any] => seq.flatMap(e => find(e, predicate))
      case dr: DataRecord[Any] => find(dr.value, predicate)
      case d: TDefinitions => find(d.rootElement, predicate)
      case p: TProcess => find(p.flowElement, predicate) ++ find(p.documentation, predicate)
      case sp: TSubProcess => find(sp.flowElement, predicate)
      case _ => Nil
    }

    e match {
      case result: T if predicate(result) => Seq(result) ++ recursion
      case _ => recursion
    }
  }

  def singleOrNone[T](seq: Seq[T]): Option[T] = seq match {
    case first :: Nil => Some(first)
    case Nil => None
    case _ => throw new Exception()
  }
}

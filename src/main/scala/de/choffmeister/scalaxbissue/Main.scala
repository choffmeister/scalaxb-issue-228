package de.choffmeister.scalaxbissue

import scala.xml._
import scalaxb._

object Main extends App {
  val model = fromXML[TDefinitions](XML.load(this.getClass.getResourceAsStream("/test.bpmn20.xml")))

  println(model)
}

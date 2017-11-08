package com.kitsonkit.otable


import org.scalajs.dom
import dom.document

import scala.scalajs.js.annotation.JSExportTopLevel

class OTable {
  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }
}

object OTable {
  val c = new OTable
  def main(args: Array[String]): Unit = {
    c.appendPar(document.body,"Hello World!")
    println("Hello world!")
  }

  @JSExportTopLevel("addClickedMessage")
  def addClickedMessage(): Unit = {
    c.appendPar(document.body, "You clicked the button!")
  }
}
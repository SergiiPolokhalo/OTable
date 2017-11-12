package com.kitsonkit.otable


import org.scalajs.dom
import org.scalajs.dom.raw.MouseEvent
import org.scalajs.dom.{Element, EventTarget}

import scala.scalajs.js.annotation.JSExportTopLevel
import scalatags.JsDom.all._


abstract class OAction

case class OClick(action: MouseEvent => Unit) extends OAction

case class ODblClick(action: MouseEvent => Unit) extends OAction

case class OCell[T](data: T, action: Seq[OAction] = List())

object OTableWizard {

  val titleAction = List(
    ODblClick(x => {
      x.target.classList.toggle("ui")
      x.target.classList.toggle("button")
    }
    ),
    OClick(x => {
      x.target.innerHTML = "pppp"
    })
  )

  implicit def target2SrcElement(e: EventTarget) = {
    e.asInstanceOf[dom.Element]
  }
  val metaTitle = List(
    OCell("First", titleAction),
    OCell("Middle", titleAction),
    OCell("Last", titleAction)
  )
  var output: Element = _

  def oTable[T](titles: Seq[OCell[T]], content: Seq[Seq[OCell[T]]]) = {
    table(
      oTableHeader(titles),
      oTableBody(content)
    )
  }

  def oTableHeader[T](titles: Seq[OCell[T]]) = {
    thead(
      for (title <- titles) yield {
        th(matcher(title.action))(title.data.toString)
      })
  }

  def oTableBody[T](content: Seq[Seq[OCell[T]]]) = {
    for (line <- content) yield tr(
      for (elem <- line) yield td(matcher(elem.action))(elem.data.toString)
    )
  }

  def matcher(actions: Seq[OAction]) = {
    val params = for (a <- actions) yield {
      a match {
        case OClick(e) => onclick := {
          e
        }
        case ODblClick(e) => ondblclick := {
          e
        }
        case _ => style := "error"
      }
    }
    params.toList
  }

  @JSExportTopLevel("makeTable")
  def makeTable(id: String) = {
    output = dom.document.getElementById(id)
    new OTable()
  }

}

class OTable() {

  import OTableWizard.{metaTitle, oTable, output}

  val d = oTable(metaTitle, List(metaTitle, metaTitle))
  output.appendChild(d.render)

}


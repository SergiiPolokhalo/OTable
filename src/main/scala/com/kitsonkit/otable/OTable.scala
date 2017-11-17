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
  implicit def mouseEvent2SrcElement( me: MouseEvent) = me.target.asInstanceOf[dom.Element]
  implicit def target2SrcElement(e: EventTarget) = e.asInstanceOf[dom.Element]
  val titleAction = List(
    ODblClick(x => {
      //get Parent, find I by fa-class
      val elem:Element = x
//      elem.parentNode.childNodes.
      x.classList.toggle("fa-caret-down")
      x.classList.toggle("fa-caret-up")
    }
    ),
    OClick(x => {
      //x.innerHTML = "pppp"
    })
  )

  val metaTitle = List(
    OCell("First", titleAction),
    OCell("Middle", titleAction),
    OCell("Last", titleAction)
  )
  val metaBody = (for (row <- 1 to  metaTitle.size) yield {
    (for(col <- 1 to metaTitle.size) yield OCell(s"$row - $col")).toList
  }).toList


  var output: Element = _

  def oTable[T](titles: Seq[OCell[T]], content: Seq[Seq[OCell[T]]]) = {
    table( cls := "table table-striped",
      oTableHeader(titles),
      oTableBody(content)
    )
  }

  def oTableHeader[T](titles: Seq[OCell[T]]) = {
    thead(
      for (title <- titles) yield {
        th(matcher(title.action))(
          title.data.toString,
          i(cls := "fa fa-caret-down")
        )
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

  import OTableWizard.{metaTitle,metaBody, oTable, output}

  val d = oTable(metaTitle, metaBody)
  output.appendChild(d.render)

}


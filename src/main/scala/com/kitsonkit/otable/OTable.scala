package com.kitsonkit.otable

import org.scalajs.dom
import org.scalajs.dom.raw.MouseEvent
import org.scalajs.dom.{Element, EventTarget}
import scalatags.JsDom.all._
import scalatags.JsDom

import scala.scalajs.js.annotation.JSExportTopLevel
//TODO
//Custom header
//Add functionality for work with direction/filter
//Any size column with custom widget

trait OHeader {
  def render(): JsDom.TypedTag[dom.html.TableHeaderCell]
}

class OHeaderImpl(text:String="No title", filter:Option[String]=None, direction:Option[Boolean]=None) extends OHeader {
  override def render() = {
    th(
      span(text),
      direction match {
        case Some(d) => if (d) span("UP") else span("down")
        case None => ""
      },
      filter match {
        case Some(s) => span("[F]")
        case None => span("[*]")
      }
    )
  }
}

object OTableWizard {
  implicit def mouseEvent2SrcElement( me: MouseEvent) = me.target.asInstanceOf[dom.Element]
  implicit def target2SrcElement(e: EventTarget) = e.asInstanceOf[dom.Element]

  var output: Element = _

  def oTable():JsDom.TypedTag[dom.html.Table] = {
    val headers = List(new OHeaderImpl, new OHeaderImpl("----",Some("test")))
    table(
      //make headers
      for (h <- headers) yield {
        h.render()
      }//,
//      //make rows
//      for (i <- ds.data()) yield {
//        tr(
//          for (j <- ds.row(i)) yield {
//            td(
//              span(
//                j
//              )
//            )
//          }
//        )
//      }
    )
  }

  @JSExportTopLevel("makeTable")
  def makeTable(id: String) = {
    output = dom.document.getElementById(id)
    new OTable()
  }
}

class OTable() {
  import OTableWizard.{oTable, output}
  output.appendChild(oTable().render)
}

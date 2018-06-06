package com.kitsonkit.otable

import org.scalajs.dom
import org.scalajs.dom.raw.MouseEvent
import org.scalajs.dom.{Element, EventTarget}
import scalatags.JsDom.all._

import scala.scalajs.js.annotation.JSExportTopLevel


trait ODataSource[H,V] {
  def headers:Seq[H]
  def row(rowNum:Int):Seq[V]
  def rows:Int
}

object ODSImpl extends ODataSource[String,String] {
  override def headers: Seq[String] = Seq("A","b","_C_")

  override def row(rowNum: Int): Seq[String] = headers.map("--"+_+"--")

  override def rows: Int = 3
}

object OTableWizard {
  implicit def mouseEvent2SrcElement( me: MouseEvent) = me.target.asInstanceOf[dom.Element]
  implicit def target2SrcElement(e: EventTarget) = e.asInstanceOf[dom.Element]

  val ds = ODSImpl

  var output: Element = _

  def oTable() = {
    table(
      //make headers
      for (h <- ds.headers) yield {
        th( h )
      },
      //make rows
      for (i <- 0 until ds.rows) yield {
        tr(
          for (j <- ds.row(i)) yield {
            td(
              span(
                j
              )
            )
          }
        )
      }
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

  val d = oTable()
  output.appendChild(d.render)

}


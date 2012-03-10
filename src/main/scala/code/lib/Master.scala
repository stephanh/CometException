package code
package lib

import scala.xml.Text

import net.liftweb.actor._
import net.liftweb.common._
import net.liftweb.http._
import scala.sys.process.Process
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds._
import net.liftweb.util.Helpers._

/**
 * A snippet transforms input to output... it transforms
 * templates to dynamic content.  Lift's templates can invoke
 * snippets and the snippets are resolved in many different
 * ways including "by convention".  The snippet package
 * has named snippets and those snippets can be classes
 * that are instantiated when invoked or they can be
 * objects, singletons.  Singletons are useful if there's
 * no explicit state managed in the snippet.
 */
object Master extends LiftActor {
  case class Query(msg: String)
  case class Forward(msg: String, sender: LiftActor)
  case class Reply(msg: Any)


  override def messageHandler = {
    case Forward(msg, sender) => {
      println("received by master")
      Process("sleep 35").!
      println("replying")
      sender ! Reply(msg)
    }
  }
}

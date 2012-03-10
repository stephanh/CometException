package code
package comet

import scala.xml.Text


import net.liftweb.common._
import net.liftweb.http._
import scala.sys.process.Process
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds._
import net.liftweb.util.Helpers._


import lib.Master
import lib.Master._

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
class ActorA extends CometActor {
    

  def render = {
    "#a *" #> <div>hello</div>
  }


  override def lowPriority = {
    case Query(msg) => {
      println("Sending to Master")
      Master ! Forward(msg, this)
    }

    case Reply(msg) => {
      println("Received from master")
      partialUpdate {
        SetHtml("a", Text("a"+msg.toString))
      }
    }
  }
}

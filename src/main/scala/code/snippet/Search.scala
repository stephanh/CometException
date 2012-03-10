package code
package snippet

import net.liftweb.common.{Box, Full, Empty, Failure}
import net.liftweb.http.{S, SHtml, StatefulSnippet, RequestVar}
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds._
import net.liftweb.util.Helpers._

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
object Search {
    

  /**
   * The render method in this case returns a function
   * that transforms NodeSeq => NodeSeq.  In this case,
   * the function transforms a form input element by attaching
   * behavior to the input.  The behavior is to send a message
   * to the ChatServer and then returns JavaScript which
   * clears the input.
   */
 
  def render = {
    val paramQuery: Box[String] = S.param("q")

    paramQuery map search
    "#search_in [value]" #> paramQuery &
    "#search_in" #> SHtml.onSubmit(search)
  }

  def search(queryStr: String): JsCmd = {
    val session = S.session.openTheBox
    session.sendCometActorMessage("ActorA", Empty, Query(queryStr))
    session.sendCometActorMessage("ActorB", Empty, queryStr)
  }
}

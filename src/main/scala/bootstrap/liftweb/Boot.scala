package bootstrap.liftweb

import net.liftweb.common.{Box, Logger, Full}
import net.liftweb.http.SessionMaster
import net.liftweb.http.{LiftResponse, LiftSession,LiftRules, S, Req, Html5Properties, SessionWatcherInfo, RequestVar}
import net.liftweb.sitemap.{Loc, Menu, SiteMap, **}
import net.liftweb.sitemap.Loc.ExtLink
import net.liftweb.util.Helpers
import net.liftweb.util.LoanWrapper
import net.liftweb.http.provider.servlet.containers.Servlet30AsyncProvider


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {

  def boot {

    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    LiftRules.setSiteMap(SiteMap(Menu("Home") / "index"))

    LiftRules.ajaxPostTimeout = 50000

    // Use jQuery 1.4
    LiftRules.jsArtifacts = net.liftweb.http.js.jquery.JQuery14Artifacts

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    //LiftRules.addSyncProvider(Servlet30AsyncProvider)


    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))    
  }
}

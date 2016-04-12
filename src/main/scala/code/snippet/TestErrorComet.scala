package code.snippet

import net.liftweb.http.js.JE.JsVar
import net.liftweb.http.js.JsCmds.SetExp
import net.liftweb.http.{RoundTripHandlerFunc, RoundTripInfo, S}
import net.liftweb.json._

import scala.xml.NodeSeq

/**
  * Created by andreas on 4/8/16.
  */
object TestErrorComet {

  def render(in: NodeSeq): NodeSeq = {
    def doTest(item: JValue, roundTrip: RoundTripHandlerFunc): Unit = {
      println("TEST!")
      roundTrip.failure("testing failure!")
      roundTrip.done()
    }

    // Associate the server functions with client-side functions
    for (sess <- S.session) {
      val script = SetExp(JsVar("window", "backend"), sess.buildRoundtrip(List[RoundTripInfo](
        //  "load" -> doLoad _,
        "test" -> doTest _
        //, "remove" -> doRemove _
      )))
      S.appendGlobalJs(script)
    }
    in
  }

  }

package spinoco.protocol.mail.header

import java.time.{ZoneOffset, ZonedDateTime}

import org.scalacheck.Prop.protect
import org.scalacheck.Properties

/**
  * Created by pach on 23/10/17.
  */
object ResentDateSpec extends Properties("ResentDate") {
  import spinoco.protocol.mail.SpecUtil._
  implicit val HeaderCodec = `Resent-Date`.codec

  property("full-time") = protect {
    verify(
      "Tue, 17 Oct 2017 16:30:29 +0000"
      , `Resent-Date`(ZonedDateTime.of(2017, 10 ,17, 16, 30, 29, 0, ZoneOffset.ofHoursMinutes(0,0)))
    )
  }


  property("minute-only-time") = protect {
    verify(
      "Tue, 17 Oct 2017 16:30 +0000"
      , `Resent-Date`(ZonedDateTime.of(2017, 10 ,17, 16, 30, 0, 0, ZoneOffset.ofHoursMinutes(0,0)))
      , "Tue, 17 Oct 2017 16:30:00 +0000"
    )
  }

}
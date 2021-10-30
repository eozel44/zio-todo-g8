package eozel.zio.utils

object testJDBC {

  /*
   *
   *
   *  val kk = (for {
   *    c  <- ds.connection
   *    ps <- c.ps(fullCustomerSql).setLong(1, imsi)
   *  } yield ps)
   *    .executeQuery()
   *    .oneOption { r =>
   *      Domain.FullCustomer(
   *        Option(r.getString("imei")).filterNot(_.isEmpty()),
   *        r.getString("msisdn"),
   *        Option(r.getString("charging_method_desc")).map(PaymentType.fromStringValue),
   *        Option(r.getString("general_segment")).map(Segment.fromStringValue),
   *        Option(r.getString("general_sub_segment")).map(SegmentInfo.fromStringValue),
   *        Option(r.getString("equipment_desc"))
   *      )
   *    }
   */

  //stream
  /*
   *
   *
   *   (for {
   *    c  <- ds.connection
   *    ps <- c.ps(liveServiceSql)
   *        } yield ps)
   *    .executeQuery()
   *    .toStream(mapToTuple)
   */
}

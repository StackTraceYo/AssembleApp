package org.stacktrace.yo.geo.assemble.api

object GeoProtocol {


  sealed trait GeoMessage

  case class AddGroupLocation(groupId: String, lon: Double, lat: Double) extends GeoMessage

  case class AddNamedGroupLocation(groupId: String, locationName: String, lon: Double, lat: Double) extends GeoMessage

  case class RemoveGroupLocation(groupId: String, lon: Double, lat: Double) extends GeoMessage

  case class FindNearby(lon: Double, lat: Double, radius: Double) extends GeoMessage

}

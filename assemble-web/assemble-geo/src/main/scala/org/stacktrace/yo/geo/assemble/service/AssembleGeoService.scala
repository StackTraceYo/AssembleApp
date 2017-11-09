package org.stacktrace.yo.geo.assemble.service

import com.github.davidmoten.rtree.geometry.Geometries
import org.stacktrace.yo.geo.assemble.api.GeoProtocol.{AddGroupLocation, FindNearby, RemoveGroupLocation}
import rx.lang.scala.Observable

class AssembleGeoService {

  private lazy val geoSpace = new AssembleGeoSpace()

  def addGroup(add: AddGroupLocation): Unit = {
    val point = Geometries.point(add.lon, add.lat)
    geoSpace.addToGeoSpace(add.groupId, point)
  }

  def remove(remove: RemoveGroupLocation): Unit = {
    geoSpace.removeFromGeoSpace(remove.groupId, Geometries.point(remove.lon, remove.lat))
  }

  def radialSearch(s: FindNearby): Observable[(String, Float, Float)] = {
    geoSpace.radialSearch(Geometries.point(s.lon, s.lat), s.radius)
      .map(entry => {
        (entry.value(), entry.geometry().x(), entry.geometry().y())
      })
  }

}

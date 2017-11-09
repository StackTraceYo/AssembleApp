package org.stacktrace.yo.geo.assemble.service

import com.github.davidmoten.rtree.geometry.{Geometries, Point}
import org.stacktrace.yo.geo.assemble.api.GeoProtocol.{AddGroupLocation, FindNearby, RemoveGroupLocation}
import rx.lang.scala.Observable

class AssembleGeoService {

  private lazy val geoSpace = new GroupRTree()
  private lazy val groupSpace = scala.collection.mutable.HashMap[String, Point]()


  def addGroup(add: AddGroupLocation): Unit = {
    val point = Geometries.point(add.lon, add.lat)
    geoSpace.addToTree(add.groupId, point)
    groupSpace.put(add.groupId, point)
  }

  def remove(remove: RemoveGroupLocation): Unit = {
    groupSpace.remove(remove.groupId) match {
      case Some(point) =>
        geoSpace.removeFromTree(remove.groupId, point)
      case None =>
    }
  }

  def radialSearch(s: FindNearby): Observable[(String, Float, Float)] = {
    geoSpace.radialSearch(Geometries.point(s.lon, s.lat), s.radius)
      .map(entry => {
        (entry.value(), entry.geometry().x(), entry.geometry().y())
      })
  }

}

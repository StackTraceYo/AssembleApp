package org.stacktrace.yo.geo.assemble.geospace

import com.github.davidmoten.rtree.RTree
import com.github.davidmoten.rtree.geometry.{Geometries, Point}
import org.stacktrace.yo.geo.assemble.api.GeoProtocol.{AddGroupLocation, AddNamedGroupLocation, FindNearby, RemoveGroupLocation}
import rx.lang.scala.Observable

class AssembleGeoSpaceRegion(regionName: String) {

  private var geoSpace = new AssembleGeoSpace()

  def addGroup(add: AddGroupLocation): Unit = {
    val point = Geometries.point(add.lon, add.lat)
    geoSpace.addToGeoSpace(add.groupId, point)
  }

  def addNamedLocationGroup(add: AddNamedGroupLocation): Unit = {
    val point = Geometries.point(add.lon, add.lat)
    geoSpace.addToGeoSpace(add.groupId, point)
  }

  def remove(remove: RemoveGroupLocation): Unit = {
    geoSpace.removeFromGeoSpace(remove.groupId, Geometries.point(remove.lon, remove.lat))
  }

  def removeMany(list: Seq[RemoveGroupLocation]): Unit = {
    list.foreach(remove => geoSpace.removeFromGeoSpace(remove.groupId, Geometries.point(remove.lon, remove.lat)))
  }

  def radialSearch(s: FindNearby): Observable[(String, Float, Float)] = {
    geoSpace.radialSearch(Geometries.point(s.lon, s.lat), s.radius)
      .map(entry => {
        (entry.value(), entry.geometry().x(), entry.geometry().y())
      })
  }

  def split(newRegion: String): AssembleGeoSpaceRegion = {
    val split: (RTree[String, Point], RTree[String, Point]) = geoSpace.splitSpace
    geoSpace.replaceTree(split._1)
    new AssembleGeoSpaceRegion(newRegion).replaceTree(split._2)
  }

  private def replaceTree(tree: RTree[String, Point]): AssembleGeoSpaceRegion = {
    geoSpace.replaceTree(tree)
    this
  }
}
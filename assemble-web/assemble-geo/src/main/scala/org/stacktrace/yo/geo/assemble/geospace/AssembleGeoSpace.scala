package org.stacktrace.yo.geo.assemble.geospace

import com.github.davidmoten.rtree.Entry
import com.github.davidmoten.rtree.geometry.Point
import org.stacktrace.yo.geo.core.{GeoSpace, Position}
import rx.lang.scala.Observable

class AssembleGeoSpace extends GeoSpace[String, Point] {

  override def boundedRectangleSearch(geometry: Point, distance: Double): Observable[Entry[String, Point]] = {
    import rx.lang.scala.JavaConversions._
    val from = Position.create(geometry.y(), geometry.x())
    val boundedRectangle = createBoundedRectangle(from, distance)
    toScalaObservable(
      rTree.search(boundedRectangle)
    ).filter(x => {
      from.getDistanceToKm(Position.create(x.geometry().y(), x.geometry().x())) < distance
    })
  }

  override def radialSearch(geometry: Point, distance: Double): Observable[Entry[String, Point]] = {
    import rx.lang.scala.JavaConversions._
    val from = Position.create(geometry.y(), geometry.x())
    val boundedCircle = createBoundedCircle(from, distance)
    toScalaObservable(
      rTree.search(boundedCircle)
    ).filter(x => {
      from.getDistanceToKm(Position.create(x.geometry().y(), x.geometry().x())) < distance
    })
  }
}
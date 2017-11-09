package org.stacktrace.yo.geo.assemble.service

import com.github.davidmoten.rtree.Entry
import com.github.davidmoten.rtree.geometry.Point
import org.stacktrace.yo.geo.core.{AbstractRTree, Position}
import rx.lang.scala.Observable

class GroupRTree extends AbstractRTree[String, Point] {


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

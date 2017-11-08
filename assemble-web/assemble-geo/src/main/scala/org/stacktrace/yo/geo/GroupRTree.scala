package org.stacktrace.yo.geo

import com.github.davidmoten.rtree.geometry.{Geometries, Geometry, Point}
import com.github.davidmoten.rtree.{Entry, RTree}
import org.stacktrace.yo.geo.core.Position
import rx.lang.scala.Observable

/**
  * Created by Stacktraceyo on 11/8/17.
  */
class GroupRTree[K] {

  case class Operation(geometry: Geometry, rTree: RTree[K, Point])

  private var rTree: RTree[K, Point] = RTree.star().create[K, Point]()

  def addToTree(c: K, v: Point): Operation = {
    rTree = rTree.add(c, v)
    Operation(v, rTree)
  }

  def searchWithBoundedRectable(point: Point, distance: Double): Observable[List[Entry[K, Point]]] = {
    import rx.lang.scala.JavaConversions._
    val from = Position.create(point.y(), point.x())
    val boundedRectangle = createBounds(from, distance)
    toScalaObservable(
      rTree.search(boundedRectangle)
    ).filter(x => {
      from.getDistanceToKm(Position.create(x.geometry().y(), x.geometry().x())) < distance
    }).toList
  }

  private def createBounds(from: Position, distanceKm: Double) = {
    // this calculates a pretty accurate bounding box. Depending on the
    // performance you require you wouldn't have to be this accurate because
    // accuracy is enforced later
    val north = from.predict(distanceKm, 0)
    val south = from.predict(distanceKm, 180)
    val east = from.predict(distanceKm, 90)
    val west = from.predict(distanceKm, 270)
    Geometries.rectangle(west.getLon, south.getLat, east.getLon, north.getLat)
  }
}
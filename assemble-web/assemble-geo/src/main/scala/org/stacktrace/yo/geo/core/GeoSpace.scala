package org.stacktrace.yo.geo.core

import com.github.davidmoten.rtree.geometry.{Circle, Geometries, Geometry, Rectangle}
import com.github.davidmoten.rtree.{Entry, RTree}
import rx.lang.scala.Observable

/**
  * Created by Stacktraceyo on 11/8/17.
  */
abstract class GeoSpace[K, V <: Geometry] {

  protected var rTree: RTree[K, V] = RTree.star().create[K, V]()

  def addToGeoSpace(c: K, v: V): Unit = {
    rTree = rTree.add(c, v)
  }

  def removeFromGeoSpace(c: K, v: V): Unit = {
    rTree = rTree.delete(c, v)
  }

  def boundedRectangleSearch(geometry: V, distance: Double): Observable[Entry[K, V]]

  def radialSearch(geometry: V, distance: Double): Observable[Entry[K, V]]


  protected def createBoundedRectangle(from: Position, distanceKm: Double): Rectangle = {
    val north = from.predict(distanceKm, 0)
    val south = from.predict(distanceKm, 180)
    val east = from.predict(distanceKm, 90)
    val west = from.predict(distanceKm, 270)
    Geometries.rectangle(west.getLon, south.getLat, east.getLon, north.getLat)
  }

  protected def createBoundedCircle(from: Position, distanceKm: Double): Circle = {
    Geometries.circle(from.getLon, from.getLat, distanceKm)
  }
}
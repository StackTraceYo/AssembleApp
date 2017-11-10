package org.stacktrace.yo.geo.core

import com.github.davidmoten.rtree.geometry._
import com.github.davidmoten.rtree.{Entry, RTree, SplitterRStar}
import rx.lang.scala.Observable

/**
  * Created by Stacktraceyo on 11/8/17.
  */
abstract class GeoSpace[K, V <: Geometry] {

  var rTree: RTree[K, V] = RTree.maxChildren(4).create[K, V]()
  var swapped = false

  def addToGeoSpace(c: K, v: V): Unit = {
    if (!swapped && rTree.size() >= 10000) {
      rTree = RTree.star().create(rTree.entries().toList.toBlocking.single())
      swapped = true
    }
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

  def getEntrySize: Int = {
    rTree.size()
  }

  def replaceTree(tree: RTree[K, V]): GeoSpace[K, V] = {
    rTree = tree
    this
  }

  def splitSpace: (RTree[K, V], RTree[K, V]) = {
    import scala.collection.JavaConversions._

    val splitter = new SplitterRStar()
    val pair = splitter.split(rTree.entries().toList.toBlocking.single(), 1)
    var tree1 = RTree.maxChildren(4).create[K, V]()
    var tree2 = RTree.maxChildren(4).create[K, V]()

    asScalaBuffer(pair.group1().list()).toList
      .foreach(entry => {
        tree1 = tree1.add(entry)
      })

    asScalaBuffer(pair.group2().list()).toList
      .foreach(entry => {
        tree2 = tree2.add(entry)
      })

    (tree1, tree2)
  }

  def splitAndReplace: RTree[K, V] = {
    val (tree1, tree2) = splitSpace
    replaceTree(tree1)
    tree2
  }
}
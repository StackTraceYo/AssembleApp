package org.stacktrace.yo.geo.core

import com.github.davidmoten.rtree.geometry.{Geometries, Point}
import org.scalatest.{FlatSpec, Matchers}
import org.stacktrace.yo.geo.GroupRTree

/**
  * Created by Stacktraceyo on 11/8/17.
  */
class GroupRTreeSpec extends FlatSpec with Matchers {


  "A GroupRTree" should
    "search by lat and long" in {

    val sydney: Point = Geometries.point(151.2094, -33.86)
    val canberra: Point = Geometries.point(149.1244, -35.3075)
    val brisbane: Point = Geometries.point(153.0278, -27.4679)
    val bungendore: Point = Geometries.point(149.4500, -35.2500)

    val groupRTree = new GroupRTree[String]()
    groupRTree.addToTree("Sydney", sydney)
    groupRTree.addToTree("Brisbane", brisbane)

    val list = groupRTree.searchWithBoundedRectable(canberra, 300)
      .toBlocking.single

    list.size shouldBe 1
    list.head.value() shouldBe "Sydney"

  }

  //  it should "throw NoSuchElementException if an empty stack is popped" in {
  //
  //  }

}

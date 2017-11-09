package org.stacktrace.yo.geo.assemble.service

import com.github.davidmoten.rtree.geometry.{Geometries, Point}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Stacktraceyo on 11/8/17.
  */
class AssembleGeoSpaceSpec extends FlatSpec with Matchers {


  "AssembleGeoSpac" should
    "search by lat and long" in {

    val sydney: Point = Geometries.point(151.2094, -33.86)
    val canberra: Point = Geometries.point(149.1244, -35.3075)
    val brisbane: Point = Geometries.point(153.0278, -27.4679)
    val bungendore: Point = Geometries.point(149.4500, -35.2500)

    val groupRTree = new AssembleGeoSpace()
    groupRTree.addToGeoSpace("Sydney", sydney)
    groupRTree.addToGeoSpace("Brisbane", brisbane)

    val list = groupRTree.boundedRectangleSearch(canberra, 300)
      .toList.toBlocking.single

    list.size shouldBe 1
    list.head.value() shouldBe "Sydney"


    val list2 = groupRTree.radialSearch(canberra, 300)
      .toList.toBlocking.single

    list2.size shouldBe 1
    list2.head.value() shouldBe "Sydney"

  }

  //  it should "throw NoSuchElementException if an empty stack is popped" in {
  //
  //  }

}

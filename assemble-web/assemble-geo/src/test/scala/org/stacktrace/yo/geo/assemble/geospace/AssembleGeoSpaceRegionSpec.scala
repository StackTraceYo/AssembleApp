package org.stacktrace.yo.geo.assemble.geospace

import com.github.davidmoten.rtree.geometry.{Geometries, Point}
import org.scalatest.{FlatSpec, Matchers}
import org.stacktrace.yo.geo.assemble.api.GeoProtocol.{AddGroupLocation, FindNearby, RemoveGroupLocation}

/**
  * Created by Stacktraceyo on 11/8/17.
  */
class AssembleGeoSpaceRegionSpec extends FlatSpec with Matchers {


  "AssembleGeoSpaceRegion" should
    "search by lat and long" in {

    val sydney: Point = Geometries.point(151.2094, -33.86)
    val canberra: Point = Geometries.point(149.1244, -35.3075)
    val brisbane: Point = Geometries.point(153.0278, -27.4679)
    val bungendore: Point = Geometries.point(149.4500, -35.2500)

    val service = new AssembleGeoSpaceRegion("region-1")

    service.addGroup(AddGroupLocation("sydney", sydney.x(), sydney.y()))
    service.addGroup(AddGroupLocation("canberra", canberra.x(), canberra.y()))
    service.addGroup(AddGroupLocation("brisbane", brisbane.x(), brisbane.y()))
    service.addGroup(AddGroupLocation("bungendore", bungendore.x(), bungendore.y()))

    service.radialSearch(FindNearby(canberra.x(), canberra.y(), 300))
      .toList
      .toBlocking
      .single.length shouldBe 3
  }

  it should "remove entries" in {

    val sydney: Point = Geometries.point(151.2094, -33.86)
    val canberra: Point = Geometries.point(149.1244, -35.3075)
    val brisbane: Point = Geometries.point(153.0278, -27.4679)
    val bungendore: Point = Geometries.point(149.4500, -35.2500)

    val service = new AssembleGeoSpaceRegion("region-1")

    service.addGroup(AddGroupLocation("sydney", sydney.x(), sydney.y()))
    //    service.addGroup(AddGroupLocation("canberra", canberra.x(), canberra.y()))
    service.addGroup(AddGroupLocation("brisbane", brisbane.x(), brisbane.y()))
    service.addGroup(AddGroupLocation("bungendore", bungendore.x(), bungendore.y()))

    service.radialSearch(FindNearby(canberra.x(), canberra.y(), 300))
      .toList
      .toBlocking
      .single.length shouldBe 2

    service.removeMany(Seq(
      RemoveGroupLocation("sydney", sydney.x(), sydney.y()),
      RemoveGroupLocation("brisbane", brisbane.x(), brisbane.y())))

    val list = service.radialSearch(FindNearby(canberra.x(), canberra.y(), 300))
      .toList
      .toBlocking
      .single
    list.length shouldBe 1
  }

}

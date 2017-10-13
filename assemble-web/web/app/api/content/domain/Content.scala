package api.content.domain

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{JsPath, Reads, Writes}

/**
  * Created by Stacktraceyo on 10/11/17.
  */
object Content {

  case class Category(categoryName: String, parentCategoryName: String, subCategories: List[Category], isFinal: Boolean)

  implicit val categoryWrites: Writes[Category] = (
    (JsPath \ "categoryName").write[String] and
      (JsPath \ "parentCategoryName").write[String] and
      (JsPath \ "subCategories").lazyWrite(Writes.list[Category](categoryWrites)) and
      (JsPath \ "isFinal").write[Boolean]
    ) (unlift(Category.unapply))

  implicit val categoryReads: Reads[Category] = (
    (JsPath \ "categoryName").read[String] and
      (JsPath \ "parentCategoryName").read[String] and
      (JsPath \ "subCategories").lazyRead(Reads.list[Category](categoryReads)) and
      (JsPath \ "isFinal").read[Boolean]
    ) (Category.apply _)

  lazy val categories = Category("Initial", "Initial", List(
    buildCategory("Outdoor", "Initial",
      List(
        buildCategory("Sports", "Outdoor",
          List(
            buildCategory("Basketball", "Sports", List(), isFinal = true),
            buildCategory("Soccer", "Sports", List(), isFinal = true),
            buildCategory("Football", "Sports", List(), isFinal = true),
            buildCategory("Tennis", "Sports", List(), isFinal = true),
            buildCategory("Ultimate Frisbee", "Sports", List(), isFinal = true),
            buildCategory("Other Sports", "Sports", List(), isFinal = true)
          ), isFinal = false),
        buildCategory("Running", "Outdoor", List(), isFinal = true),
        buildCategory("Biking", "Outdoor", List(), isFinal = true),
        buildCategory("Hiking", "Outdoor", List(), isFinal = true),
        buildCategory("Other Outdoor", "Outdoor", List(), isFinal = true)
      ), isFinal = false),
    buildCategory("Indoor", "Initial", List(
      buildCategory("Video Games", "Indoor", List(
        buildCategory("LAN", "Video Games", List(), isFinal = true),
        buildCategory("Online", "Video Games", List(), isFinal = true)
      ), isFinal = false),
      buildCategory("Board Games", "Indoor", List(), isFinal = true),
      buildCategory("Music", "Indoor", List(
        buildCategory("Jam Session", "Music", List(), isFinal = true)
      ), isFinal = false)
    ), isFinal = false)
  ), isFinal = false)

  private def buildCategory(name: String, parent: String, subCategories: List[Category], isFinal: Boolean): Category = {
    Category(name, parent, subCategories, isFinal)
  }


}

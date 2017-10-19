package com.stacktrace.yo.assemble.group.serialization

import akka.serialization.SerializerWithStringManifest
import com.stacktrace.yo.assemble.access.AccessProtocol.CreatedAccess

class GroupAccessSerializer extends SerializerWithStringManifest {

  override def identifier = 9002

  // We use the manifest to determine the event (it is called for us during serializing)
  // Akka will call manifest and attach it to the message in the event journal/snapshot database
  // when toBinary is being invoked
  override def manifest(o: AnyRef): String = o.getClass.getName

  final val CreatedAccessManifest = classOf[CreatedAccess].getName


  // Event <- **Deserializer** <- Serialized(Event) <- Journal
  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef =
    manifest match {
      case CreatedAccessManifest => CreatedAccess.parseFrom(bytes)
    }


  // Event -> **Serializer** -> Serialized(Event) -> Journal
  override def toBinary(o: AnyRef): Array[Byte] = {
    o match {
      case a: CreatedAccess => a.toByteArray
    }
  }
}

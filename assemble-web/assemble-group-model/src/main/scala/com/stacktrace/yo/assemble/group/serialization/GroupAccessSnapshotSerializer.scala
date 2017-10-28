package com.stacktrace.yo.assemble.group.serialization

import akka.serialization.SerializerWithStringManifest
import com.stacktrace.yo.assemble.access.AccessProtocol.GroupAccessState

class GroupAccessSnapshotSerializer extends SerializerWithStringManifest {

  override def identifier = 9001

  // We use the manifest to determine the event (it is called for us during serializing)
  // Akka will call manifest and attach it to the message in the event journal/snapshot database
  // when toBinary is being invoked
  override def manifest(o: AnyRef): String = o.getClass.getName

  final val GroupAccessStateManifest = classOf[GroupAccessState].getName


  // Event <- **Deserializer** <- Serialized(Event) <- Journal
  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef =
    manifest match {
      case GroupAccessStateManifest => GroupAccessState.parseFrom(bytes)
    }


  // Event -> **Serializer** -> Serialized(Event) -> Journal
  override def toBinary(o: AnyRef): Array[Byte] = {
    o match {
      case c: GroupAccessState => c.toByteArray
    }
  }
}

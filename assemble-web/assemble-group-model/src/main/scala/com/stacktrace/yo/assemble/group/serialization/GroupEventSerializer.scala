package com.stacktrace.yo.assemble.group.serialization

import akka.serialization.SerializerWithStringManifest
import com.stacktrace.yo.assemble.group.GroupProtocol.{Created, Ended, GroupReferenceCreated, Joined, Left}

class GroupEventSerializer extends SerializerWithStringManifest {

  override def identifier = 9001

  // We use the manifest to determine the event (it is called for us during serializing)
  // Akka will call manifest and attach it to the message in the event journal/snapshot database
  // when toBinary is being invoked
  override def manifest(o: AnyRef): String = o.getClass.getName

  final val CreatedManifest = classOf[Created].getName
  final val JoinedManifest = classOf[Joined].getName
  final val LeftManifest = classOf[Left].getName
  final val EndedManifest = classOf[Ended].getName
  final val GroupReferenceCreatedManifest = classOf[GroupReferenceCreated].getName


  // Event <- **Deserializer** <- Serialized(Event) <- Journal
  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef =
    manifest match {
      case CreatedManifest => Created.parseFrom(bytes)
      case JoinedManifest => Joined.parseFrom(bytes)
      case LeftManifest => Left.parseFrom(bytes)
      case EndedManifest => Ended.parseFrom(bytes)
      case GroupReferenceCreatedManifest => GroupReferenceCreated.parseFrom(bytes)
    }


  // Event -> **Serializer** -> Serialized(Event) -> Journal
  override def toBinary(o: AnyRef): Array[Byte] = {
    o match {
      case a: Created => a.toByteArray
      case s: Joined => s.toByteArray
      case m: Left => m.toByteArray
      case d: Ended => d.toByteArray
      case r: GroupReferenceCreated => r.toByteArray
    }
  }
}

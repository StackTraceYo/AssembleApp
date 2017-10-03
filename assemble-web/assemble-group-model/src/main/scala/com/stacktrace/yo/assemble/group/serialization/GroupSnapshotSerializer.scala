package com.stacktrace.yo.assemble.group.serialization

import akka.serialization.SerializerWithStringManifest
import com.stacktrace.yo.assemble.group.GroupProtocol.{AssembleGroupState, DirectorReferenceState}

class GroupSnapshotSerializer extends SerializerWithStringManifest {

  override def identifier = 9002

  // We use the manifest to determine the event (it is called for us during serializing)
  // Akka will call manifest and attach it to the message in the event journal/snapshot database
  // when toBinary is being invoked
  override def manifest(o: AnyRef): String = o.getClass.getName

  final val AssembleGroupStateManifest = classOf[AssembleGroupState].getName
  final val DirectorReferenceStateManifest = classOf[DirectorReferenceState].getName


  // Event <- **Deserializer** <- Serialized(Event) <- Journal
  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef =
    manifest match {
      case AssembleGroupStateManifest => AssembleGroupState.parseFrom(bytes)
      case DirectorReferenceStateManifest => DirectorReferenceState.parseFrom(bytes)
    }


  // Event -> **Serializer** -> Serialized(Event) -> Journal
  override def toBinary(o: AnyRef): Array[Byte] = {
    o match {
      case c: AssembleGroupState => c.toByteArray
      case j: DirectorReferenceState => j.toByteArray
    }
  }
}

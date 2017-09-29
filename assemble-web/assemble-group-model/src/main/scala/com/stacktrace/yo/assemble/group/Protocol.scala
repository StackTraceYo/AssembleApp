package com.stacktrace.yo.assemble.group

import com.stacktrace.yo.assemble.group.GroupProtocol.AssembleGroupState

/**
  * Created by Stacktraceyo on 9/26/17.
  */
object Protocol {

  //Commands: Do this action
  sealed trait Command

  case class CreateGroup(hostId: String) extends Command

  case class Join() extends Command

  case class Leave() extends Command

  case class End() extends Command

  case class GetState() extends Command

  case class Ready() extends Command

  // Marker trait for subclasses generated by Protobuf
  // The rest of the events are present in target/scala-2.*/src_managed/...
  // The Events are generated by ScalaPB from the GroupProtocol.proto file
  // Events: I have done this action
  trait Event

  trait GroupState

}

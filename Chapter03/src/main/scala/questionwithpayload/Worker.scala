package questionwithpayload

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object Worker {
  sealed trait Command
  final case class Parse(text: String, replyTo: ActorRef[Worker.Response]) extends Command

  sealed trait Response
  final case object Done extends Response

  def apply(): Behavior[Command] = Behaviors.receive{
    (context, message) => message match {
      case Parse(replyTo, text) =>
        val parsed = naiveParsing(text)
        context.log.info(s" '${context.self}' DONE! Parsed Result: ${parsed}")
        replyTo ! Worker.Done(text)
        Behaviors.stopped
    }
  }

  def naiveParsing(text: String): String =
    text.replaceAll("-", "")
}

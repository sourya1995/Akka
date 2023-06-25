package errorkernel

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object Manager {
  sealed trait Command
  final case class Delegate(forms: List[String]) extends Command
  private final case class WorkerDoneAdapter(response: Worker.Response) extends Command

  def apply(): Behavior[Command] = Behaviors.setup{
    context => val adapter: ActorRef[Worker.Response] = context.messageAdapter(response => WorkerDoneAdapter(response))
      Behaviors.receiveMessage{ message =>
        message match {
          case Delegate(texts) =>
            texts.map {
              text => val worker: ActorRef[Worker.Command] = context.spawn(Worker(), s"worker$text")
                context.log.info(s"sending text '${text}' to Worker")
                worker ! Worker.Parse(adapter, text)
            }
            Behaviors.same
          case WorkerDoneAdapter(Worker.Done(text)) =>
            context.log.info(s"text '$text' has been finished")
            Behaviors.same
        }
      }
  }
}

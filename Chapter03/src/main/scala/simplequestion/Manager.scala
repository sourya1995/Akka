package simplequestion

import scala.util.{Failure, Success}
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akka.util.Timeout

import scala.concurrent.duration.SECONDS

object Manager {
  sealed trait Command
  final case class Delegate(texts: List[String]) extends Command
  private case class Report(description: String) extends Command

  def apply(): Behavior[Command] = Behaviors.setup{
    context => implicit val timeout: Timeout = Timeout(3, SECONDS)
      Behaviors.receiveMessage{ message =>
        message match {
          case Delegate(texts) =>
            texts.map { text =>
              val worker: ActorRef[Worker.Command] = context.spawn(Worker(text), s"worker- $text")
              context.ask(worker, Worker.Parse) {
                case Success(Worker.Done) =>
                  Report(s"$text read by ${worker.path.name}")
                case Failure(exception) =>
                  Report(
                    s"parsing '${text}' has failed with [${exception.getMessage()}]"
                  )
              }
            }
            Behaviors.same
          case Report(description) => context.log.info(description)
          Behaviors.same
        }
      }
  }
}

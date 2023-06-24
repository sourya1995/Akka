package Wallet

import akka.actor.typed.Behavior
import akka.actor.typed.SupervisorStrategy.resume
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.duration.DurationInt

object WalletTimer {
  sealed trait Command
  final case class Increase(amount: Int) extends Command
  final case class Deactivate(seconds: Int) extends Command
  private final case object Activate extends Command //cannot be activated externally

  def activated(total: Int): Behavior[Command] =
    Behaviors.receive { (context, message) =>
      Behaviors.withTimers{
        timers =>
          message match {
            case Increase(amount) =>
              val current = total + amount
              context.log.info(s"increasing to $current")
              resume(current)
            case Deactivate(t) =>
              timers.startSingleTimer(Activate, t.second)
              deactivated(total)
            case Activate =>
              Behaviors.same
          }
      }
    }

  def deactivated(total: Int): Behavior[Command] = {
    Behaviors.receive { (context, message) =>
      message match {
        case Increase(_) =>
          context.log.info(s"wallet is activated. Can't increase")
          Behaviors.same
        case Deactivate(t) =>
          Behaviors.same
        case Activate =>
          context.log.info(s"activating")
          activated(total)
      }
    }
  }
}

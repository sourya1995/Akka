package errorkernel

import akka.actor.typed.ActorSystem

object ErrorKernelApp extends App {
  val guardian: ActorSystem[Guardian.Command] = ActorSystem(Guardian(), "error-kernel")
  Guardian ! Guardian.Start(List("-one-", "-two-"))
}

package Wallet

import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior

object WalletApp extends App {
  val guardian: ActorSystem[Int] = ActorSystem(Wallet(), "wallet")
  guardian ! 1
  guardian ! 10

  println("press ENTER to terminate")
  scala.io.StdIn.readLine()
  guardian.terminate()
}

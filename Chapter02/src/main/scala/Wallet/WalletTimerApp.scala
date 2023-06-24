package Wallet

import akka.actor.ActorSystem

object WalletTimerApp extends App{
  val guardian: ActorSystem[WalletTimer.Command] = ActorSystem(WalletTimer(), "wallet-timer")
  guardian ! WalletTimer.Increase(1)
  guardian ! WalletTimer.Deactivate(3)

}

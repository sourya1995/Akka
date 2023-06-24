package Wallet

import akka.actor.ActorSystem

object WalletOnOffApp extends App{
  val guardian: ActorSystem[WalletResume.Command] = ActorSystem(WalletOnOff, "wallet-on-off")
  guardian ! WalletResume.Increase(1)
  guardian ! WalletResume.Deactivate
  guardian ! WalletResume.Increase(1)
  guardian ! WalletResume.Activate
  guardian ! WalletResume.Increase(1)

}

package Wallet

import akka.actor.ActorSystem

object WalletStateApp extends App{
   val guardian : ActorSystem[WalletState.Command] = ActorSystem(WalletState(0, 2), "wallet-state")
   guardian ! WalletState.Increase
   guardian ! WalletState.Increase
   guardian ! WalletState.Increase

}

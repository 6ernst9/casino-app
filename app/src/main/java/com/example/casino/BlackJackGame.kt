package com.example.casino

import android.os.Handler
import java.util.function.BiPredicate
import java.util.function.IntFunction
import kotlin.random.Random

class BlackJackGame(players : ArrayList<Player>, smallBlind : Double) {
    private var deck = Deck()
    var dealerHandScore = 0
    val dealer = Player("Dealer", 10000000000.0)
    var players = ArrayList<Player>()
    val backUpPlayers = ArrayList<Player>()
    var betPaidByPlayers = ArrayList<Double>()
    var currentTurn = Player("", 0.0)
    var status : BlackJackPhase = BlackJackPhase.BETTING
    init{
        this.players = players
        currentTurn = players[0]
        for(i in players.indices){
            backUpPlayers.add(Player(players[i].name, players[i].balance))
        }
        for(i in players.indices){
            betPaidByPlayers.add(0.0)
        }
        val randomNr = Random.nextInt(10, 50)
        for(i in 0 until randomNr){
            deck.shuffle()
        }
    }

    private fun startingRound(){
        status = BlackJackPhase.CARDING
        for(i in players.indices){
            players[i].addCards(deck.deck[deck.deck.size-1])
            deck.deck.removeAt(deck.deck.size-1)
        }
        dealer.addCards(deck.deck[deck.deck.size-1])
        deck.deck.removeAt(deck.deck.size-1)
        for(i in players.indices){
            players[i].addCards(deck.deck[deck.deck.size-1])
            deck.deck.removeAt(deck.deck.size-1)
        }
        dealer.addCards(deck.deck[deck.deck.size-1])
        deck.deck.removeAt(deck.deck.size-1)
        for(i in 0 until 2){
            println("player 1 hand: ${players[0].hand[i].color} ${players[0].hand[i].cardScore}")
            println("player 2 hand: ${players[1].hand[i].color} ${players[1].hand[i].cardScore}")
            println("player 3 hand: ${players[2].hand[i].color} ${players[2].hand[i].cardScore}")
        }
        currentTurn = players[0]
      }
//
//
    private fun showDown(){

        for(i in players.indices){
            var firstHandScore = 0
            var secondHandScore = 0
            for(j in players[i].hand.indices){
                firstHandScore +=players[i].hand[j].blackJackScore
            }
            for(j in players[i].hand.indices){
                secondHandScore +=players[i].hand[j].secondBlackJackScore
            }
            if(getMax(firstHandScore, secondHandScore)>dealerHandScore)
            {
                players[i].balance +=2*betPaidByPlayers[i]
            }
        }
        newGame()
    }
    private fun newGame(){
       status = BlackJackPhase.BETTING
        currentTurn = players[0]
        for(i in players.indices){
            backUpPlayers[i].balance = players[i].balance
            players[i].hand.clear()
        }
        players.clear()
        dealer.hand.clear()
        for(i in backUpPlayers.indices){
            players.add(backUpPlayers[i])
        }
        betPaidByPlayers.clear()
        for(i in players.indices){
            betPaidByPlayers.add(0.0)
        }
        dealerHandScore = 0
        deck.deck.clear()
        deck.resetDeck()
        deck.shuffle()
    }
    private fun getMax( val1 : Int, val2 : Int) : Int{
        if(val1>=val2){
            return val1
        }
        else{
            return val2
        }
    }
    fun makeAction(player : Player, action : BlackJackAction, price: Double) : Boolean{
        if (player != currentTurn) {
            return false
        }
        when(action){
            BlackJackAction.HIT ->{
                player.addCards(deck.deck[deck.deck.size-1])
                deck.deck.removeAt(deck.deck.size-1)
                if (bustChecker(player)){
                    println("busted")
                    nextTurn(player)
                }
            }
            BlackJackAction.STAY-> {
                nextTurn(player)

            }
            BlackJackAction.BET ->{
                player.balance -=price
                betPaidByPlayers[getPlayerIndice(player)] = price
                nextTurn(player)

            }
            else -> player.balance = player.balance
        }
        return true

    }
    fun dealerTurn(){
        var firstHandScore = 0
        var secondHandScore = 0
        status= BlackJackPhase.SHOWDOWN
        for(i in dealer.hand.indices){
            firstHandScore +=dealer.hand[i].blackJackScore
        }
        for(i in dealer.hand.indices){
            secondHandScore +=dealer.hand[i].secondBlackJackScore
        }
        if(firstHandScore>=16 && !bustChecker(dealer) ||secondHandScore >= 16&& !bustChecker(dealer) ){
            dealerHandScore = getMax(firstHandScore, secondHandScore)
            showDown()


        }
        if(firstHandScore<16 && secondHandScore<16 && !bustChecker(dealer) )
        {
            dealer.addCards(deck.deck[deck.deck.size-1])
            deck.deck.removeAt(deck.deck.size-1)
            dealerTurn()

        }
        if(bustChecker(dealer)){
            dealerBusted()
        }
    }
    fun dealerBusted(){
        for(i in players.indices){
            players[i].balance+=2*betPaidByPlayers[i]
        }
        newGame()
    }
    private fun bustChecker(player : Player) : Boolean{
        var firstHandScore = 0
        var secondHandScore = 0
        for(i in player.hand.indices){
            firstHandScore +=player.hand[i].blackJackScore
        }
        for(i in player.hand.indices){
            secondHandScore +=player.hand[i].secondBlackJackScore
        }
        if(firstHandScore>21 && secondHandScore > 21){
            return true
        }
        return false
    }
    private fun nextTurn( player: Player){
        for (i in players.indices) {
            if (player.name == players[i].name && i != players.size - 1) {
                currentTurn = players[i + 1]
                break
            }
            else if(player.name == players[i].name && i == players.size - 1 && status == BlackJackPhase.BETTING ){
                startingRound()
            }
            else if(player.name == players[i].name && i == players.size - 1 && status == BlackJackPhase.CARDING){
                currentTurn = dealer
                dealerTurn()
            }
        }
    }
    private fun getPlayerIndice(player: Player) : Int{
        for(i in players.indices){
            if(player.name == players[i].name) {
                return i
            }
        }
        return -1
    }
}
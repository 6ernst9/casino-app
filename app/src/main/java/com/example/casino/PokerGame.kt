package com.example.casino

import android.os.Handler
import java.util.function.BiPredicate
import java.util.function.IntFunction

class PokerGame(players : ArrayList<Player>, smallBlind : Double) {
    private var deck = Deck()
    var smallBlind : Double = 0.0
    var currentBet : Double = 0.0
    var winnerValue : Int = 0
    var tour = 0
    var smallBlindSpot = 0
    var players = ArrayList<Player>()
    val backUpPlayers = ArrayList<Player>()
    var betPaidByPlayers = ArrayList<Double>()
    var currentTurn = Player("", 0.0)
    var pot : Double = 0.0
    public val tableHand = ArrayList<Card>()
    val winners = ArrayList<Player>()
    var status : PokerPhases = PokerPhases.PRE_FLOP
    init{
        this.players = players
        currentTurn = players[0]
        this.smallBlind = smallBlind
        for(i in players.indices){
            backUpPlayers.add(Player(players[i].name, players[i].balance))
        }
        for(i in players.indices){
            betPaidByPlayers.add(0.0)
        }
        deck.shuffle()
        preFlop()
    }

    private fun preFlop(){
        for(i in players.indices){
            players[i].addCards(deck.deck[deck.deck.size-1])
            deck.deck.removeAt(deck.deck.size-1)
        }
        for(i in players.indices){
            players[i].addCards(deck.deck[deck.deck.size-1])
            deck.deck.removeAt(deck.deck.size-1)
        }
        for(i in 0 until 2){
            println("player 1 hand: ${players[0].hand[i].color} ${players[0].hand[i].cardScore}")
            println("player 2 hand: ${players[1].hand[i].color} ${players[1].hand[i].cardScore}")
            println("player 3 hand: ${players[2].hand[i].color} ${players[2].hand[i].cardScore}")
        }
        players[smallBlindSpot].balance = players[smallBlindSpot].balance - smallBlind
        betPaidByPlayers[smallBlindSpot] = smallBlind
        if(smallBlindSpot<players.size-1){
            players[smallBlindSpot +1].balance = players[smallBlindSpot+1].balance - 2 * smallBlind
            betPaidByPlayers[smallBlindSpot + 1] = 2 * smallBlind
        }
        else{
            players[0].balance = players[0].balance - 2 * smallBlind
            betPaidByPlayers[0] = 2 * smallBlind
        }
        pot+=3*smallBlind
        currentBet = 2*smallBlind
        if(smallBlindSpot+1==players.size-1){
            currentTurn = players[0]
        }
        else if(smallBlindSpot +1 == players.size){
            currentTurn = players[1]
        }
        else{
            currentTurn = players[smallBlindSpot+2]
        }
    }
    fun flop(){

        deck.deck.removeAt(deck.deck.size-1)

        tableHand.add(deck.deck[deck.deck.size-1])
        deck.deck.removeAt(deck.deck.size-1)
        tableHand.add(deck.deck[deck.deck.size-1])
        deck.deck.removeAt(deck.deck.size-1)
        tableHand.add(deck.deck[deck.deck.size-1])
        deck.deck.removeAt(deck.deck.size-1)
        for(i in tableHand.indices){
            println("table card $i ${tableHand[i].cardScore} ${tableHand[i].color}")
        }
    }

    fun turn(){
        deck.deck.removeAt(deck.deck.size-1)

        tableHand.add(deck.deck[deck.deck.size-1])
        deck.deck.removeAt(deck.deck.size-1)
        for(i in tableHand.indices){
            println("table card $i ${tableHand[i].cardScore} ${tableHand[i].color}")
        }
    }
    fun river(){
        deck.deck.removeAt(deck.deck.size-1)

        tableHand.add(deck.deck[deck.deck.size-1])
        deck.deck.removeAt(deck.deck.size-1)
        for(i in tableHand.indices){
            println("table card $i ${tableHand[i].cardScore} ${tableHand[i].color}")
        }
    }

    private fun showDown(){
        val playersHandScore = ArrayList<Int>()
        for(i in players.indices){
            playersHandScore.add(0)
        }
        for(i in players.indices){
            val handEvaluator = Hand(players[i].hand, this.tableHand)
            playersHandScore[i] = handEvaluator.handValue()
        }
        winnerValue = getMax(playersHandScore)
        for(i in players.indices){
            if(playersHandScore[i] == winnerValue){
                winners.add(players[i])
                players[i].balance +=pot/winners.size
            }
        }
        newGame()
    }
    private fun newGame(){
        if(smallBlindSpot>=players.size-1){
            smallBlindSpot = 0
        }
        else{
            smallBlindSpot ++
        }
        pot = 0.0
        for(i in players.indices){
            backUpPlayers[i].balance = players[i].balance
            players[i].hand.clear()
        }
        players.clear()
        for(i in backUpPlayers.indices){
            players.add(backUpPlayers[i])
        }
        tableHand.clear()
        currentBet = 0.0
        tour = 0
        betPaidByPlayers.clear()
        for(i in players.indices){
            betPaidByPlayers.add(0.0)
        }
        deck.deck.clear()
        deck.resetDeck()
        deck.shuffle()
        preFlop()
    }
    private fun getMax( scores : ArrayList<Int>) : Int{
        var max = -1
        for( i in scores.indices){
            if(scores[i]>max){
                max = scores[i]
            }
        }
        return max
    }
    public fun middleButton() : String{
        if(currentBet == 0.0){
            return "Check"
        }
        else{
            return "Call"
        }
    }
    public fun betButton() : String{
        if(currentBet==0.0){
            return "Bet"
        }
        else{
            return "Raise"
        }
    }
    fun makeAction(player : Player, action : Action, price: Double) : Boolean{
        if (player != currentTurn) {
            return false
        }
        when(action){
            Action.CHECK ->{
                winners.clear()
                if(currentBet!=0.0){
                    return false
                }
            }
            Action.CALL -> {
                winners.clear()
                for (i in players.indices) {
                    if (player == players[i]) {
                        player.balance = player.balance - (currentBet - betPaidByPlayers[i])
                        pot +=currentBet - betPaidByPlayers[i]
                        betPaidByPlayers[i] = currentBet
                    }
                }
            }
            Action.RAISE -> {
                winners.clear()
                if(price<smallBlind){
                    return false
                }
                for (i in players.indices) {
                    if (player == players[i]) {
                        currentBet += price
                        player.balance = player.balance - (currentBet- betPaidByPlayers[i])
                        pot +=(currentBet- betPaidByPlayers[i])
                        betPaidByPlayers[i] += (currentBet- betPaidByPlayers[i])
                    }
                }
            }
            Action.FOLD ->{
                winners.clear()
                var indexToRemove = 0
                for(i in players.indices){
                    if(player == players[i]){
                        indexToRemove = i
                    }
                }
                backUpPlayers[indexToRemove].balance = players[indexToRemove].balance
                players.removeAt(indexToRemove)
                betPaidByPlayers.removeAt(indexToRemove)
                if(indexToRemove!=players.size){
                    currentTurn = players[indexToRemove]
                }
                else{
                    currentTurn = players[0]
                }
            }

            else -> player.balance = player.balance
        }
        if(players.size == 1){
            winners.add(players[0])
            newGame()
        }
        for(i in players.indices){
            if(smallBlindSpot==0 && players.size>2 && player==players[i] && i ==players.size-1){
                tour++
            }
            if(smallBlindSpot <= players.size-1 && players.size>2  && player==players[i] && i ==smallBlindSpot -1){
                tour++
            }
            if(smallBlindSpot == 0 && players.size == 2 && player==players[i] && i ==1){
                tour++
            }
        }
        if(allChecked(betPaidByPlayers) && tour!=0) {
            currentBet = 0.0
            for (i in players.indices) {
                betPaidByPlayers[i] = 0.0
            }
            currentTurn = players[smallBlindSpot]
            increaseStatus(status)

            tour = 0
        }
        else {
            for (i in players.indices) {
                if (player == players[i] && i != players.size - 1) {
                    currentTurn = players[i + 1]
                    break
                }
                else if(player == players[i] && i == players.size - 1){
                    currentTurn = players[0]
                }
            }
        }
        for(i in players.indices){
            if(currentTurn == players[i]) {
                println("player ${i+1} turn, ${status}")
            }
        }
        return true

    }
    private fun getPlayerIndice(player: Player) : Int{
        for(i in players.indices){
            if(player == players[i]) {
                return i
            }
        }
        return -1
    }
    private fun allChecked(betByAllPlayers: ArrayList<Double>) : Boolean{
        for(i in 0 until betByAllPlayers.size-1){
            if(betByAllPlayers[i]!=betByAllPlayers[i+1]){
                return false
            }
        }
        return true
    }

    private fun increaseStatus(status : PokerPhases){
        when(status){
            PokerPhases.PRE_FLOP -> {
                this.status = PokerPhases.FLOP
                flop()
            }
            PokerPhases.FLOP -> {
                this.status = PokerPhases.TURN
                turn()
            }
            PokerPhases.TURN -> {
                this.status = PokerPhases.RIVER
                river()
            }
            PokerPhases.RIVER -> {
                this.status = PokerPhases.PRE_FLOP
                showDown()

            }
            else -> this.status = PokerPhases.PRE_FLOP
        }
    }
}
package com.example.casino

class Player(name: String, balance: Double) {
    var hand = ArrayList<Card>(2)
    var name : String = ""
    var balance = 0.0
    init{
        this.hand.clear()
        this.name = name
        this.balance = balance
    }
    fun addCards( card : Card){
        hand.add(card)
    }
    fun removeCards(){
        hand.removeAll(hand.toSet())
    }
}
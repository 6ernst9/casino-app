package com.example.casino

class TwoPair(hand : ArrayList<Card>) {
    var hand =ArrayList<Card>()
    var originalHand = ArrayList<Card>()
    init{
        this.hand= hand
        this.originalHand = hand
    }
    fun isTwoPair() : Boolean{
        var kindCounter : Int = 0
        for(i in 0 until hand.size){
            for( j in 0 until hand.size){
                if(hand[i].cardScore==hand[j].cardScore&& i!=j){
                    kindCounter++
                }
            }
        }
        hand = originalHand
        for(i in hand.indices){
            println("dilau ${hand[i].cardScore}")
        }
        return kindCounter == 4

    }
}
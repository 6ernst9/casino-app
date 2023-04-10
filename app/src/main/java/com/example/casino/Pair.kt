package com.example.casino

class Pair(hand : ArrayList<Card>) {
    var hand =ArrayList<Card>()
    var originalHand = ArrayList<Card>()
    init{
        this.hand= hand
        this.originalHand = hand
    }
    fun isPair() : Boolean{
        var kindCounter : Int = 0
        for(i in 0 until hand.size){
            for( j in 0 until hand.size){
                if(hand[i].cardScore==hand[j].cardScore&& i!=j){
                    kindCounter++
                }
            }
        }
        hand = originalHand
        return kindCounter == 2

    }
}
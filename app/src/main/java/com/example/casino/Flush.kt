package com.example.casino

class Flush(hand : ArrayList<Card>) {
    var hand =ArrayList<Card>()
    var originalHand = ArrayList<Card>()
    init{
        this.hand= hand
        this.originalHand = hand
    }
    fun isFlush() : Boolean{
        var colorCounter = 0
        for(i in 0 until hand.size){
           for(j in 0 until hand.size){
               if(hand[i].color == hand[j].color && i!=j)
               {
                   colorCounter ++
               }
           }
        }
        hand = originalHand
        return colorCounter >=20


    }
}
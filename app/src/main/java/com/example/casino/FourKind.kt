package com.example.casino

class FourKind(hand : ArrayList<Card>) {
    var hand =ArrayList<Card>()
    var originalHand = ArrayList<Card>()
    init{
        this.hand= hand
        this.originalHand = hand
    }
    fun isFourKind() : Boolean{
        var numberOfKind : Int = 0
        for(i in 0 until hand.size){
            for( j in 0 until hand.size){
                if(hand[i]==hand[j] && i!=j){
                    numberOfKind++
                }
            }
            if(numberOfKind==4){
                break
            }
        }
        hand = originalHand
        return numberOfKind == 4
    }
}
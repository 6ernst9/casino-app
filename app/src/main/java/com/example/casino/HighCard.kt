package com.example.casino

class HighCard(hand : ArrayList<Card>) {
    var hand =ArrayList<Card>()
    var originalHand = ArrayList<Card>()
    init{
        this.hand= hand
        this.originalHand = hand
    }
    fun isHighCard() : Boolean{
        var highestCard = 0
        for(i in 0 until hand.size){
            if(hand[i].cardScore > highestCard){
                highestCard=hand[i].cardScore
            }
        }
        val isLegal = highestCard == hand[0].cardScore || highestCard == hand[1].cardScore
        hand = originalHand
        return isLegal

    }
}
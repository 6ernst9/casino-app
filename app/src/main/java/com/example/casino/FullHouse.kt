package com.example.casino

class FullHouse(hand : ArrayList<Card>) {
    var hand =ArrayList<Card>()
    init{
        this.hand= hand
    }
    fun isFullHouse() : Boolean{
        var kindCounter : Int = 0
        for(i in 0 until hand.size){
            for( j in 0 until hand.size){
                if(hand[i].cardScore==hand[j].cardScore&& i!=j){
                    kindCounter++
                }
            }
        }
        return kindCounter == 8

    }
}
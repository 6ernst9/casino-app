package com.example.casino

class RoyalFlush(hand : ArrayList<Card>) {
    var hand =ArrayList<Card>()
    var originalHand = ArrayList<Card>()
    init{
        this.hand= hand
        this.originalHand = hand
    }
    fun isRoyalFlush() : Boolean{
        var isA : Boolean= false
        val color =Array<String>(5){""}
        var isColor =0
        var isK : Boolean= false
        var isQ : Boolean= false
        var isJ : Boolean= false
        var is10 : Boolean= false
        for(i in 0 until hand.size){
            if(hand[i].number == "A"){
                isA = true
                color[0] = hand[i].color
            }
            if(hand[i].number == "K"){
                isK = true
                color[1] = hand[i].color
            }
            if(hand[i].number == "Q"){
                isQ = true
                color[2] = hand[i].color
            }
            if(hand[i].number == "J"){
                isJ = true
                color[3] = hand[i].color
            }
            if(hand[i].number == "10"){
                is10 = true
                color[4] = hand[i].color
            }
        }
        for(i in color.indices){
            if(i in 1..4){
                if(color[i] == color[i-1])
                    isColor ++
            }
        }
        hand = originalHand
        return isColor==4 && isA && isK && isQ && isJ && isJ && is10

    }
}
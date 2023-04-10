package com.example.casino

class Straight(hand : ArrayList<Card>) {
    var hand =ArrayList<Card>()

    init{
        this.hand= hand
    }
    fun isStraight() : Boolean{
        var changerValue = Card("1", "D")
        val originalHand  = ArrayList<Card>()
        for(i in hand.indices){
            originalHand.add(hand[i])
        }
        val color =Array<String>(5){""}
        for( k in 0 until hand.size){
            for(i in 0 until hand.size){
                if(i<hand.size-1){
                    while(hand[i].cardScore>hand[i+1].cardScore){
                        for(j in 0 until hand.size-1){
                            if(hand[j].cardScore>hand[j+1].cardScore){
                                changerValue = hand[j]
                                hand[j] = hand[j+1]
                                hand[j+1] = changerValue
                            }
                        }
                    }
                }
            }
        }
        val equalValues = ArrayList<Int>(hand.size)
        for(i in 0 until hand.size){
            equalValues.add(0)
            for(j in 0 until hand.size){
                if(hand[i].cardScore == hand[j].cardScore && i!=j && i<j){
                    equalValues[i] = 1
                }
            }
        }
        var i = 0
        while(i < equalValues.size){
            if(equalValues[i]==1){
                hand.removeAt(i)
                i++
            }
            i++
        }
        for(i in 0 until hand.size-4){
            if(smallerByOne(hand[i].cardScore, hand[i+1].cardScore) &&
                smallerByOne(hand[i+1].cardScore, hand[i+2].cardScore) &&
                smallerByOne(hand[i+2].cardScore, hand[i+3].cardScore) &&
                smallerByOne(hand[i+3].cardScore, hand[i+4].cardScore)) {
                for(j in originalHand.indices){
                    if(j<hand.size){
                        hand[j] = originalHand[j]
                    }
                    else{
                        hand.add(originalHand[j])
                    }
                }
                return true
            }
        }
        for( k in 0 until hand.size){
            for(i in 0 until hand.size){
                if(i<hand.size-1){
                    while(hand[i].secondCardScore>hand[i+1].secondCardScore){
                        for(j in 0 until hand.size-1){
                            if(hand[j].secondCardScore>hand[j+1].secondCardScore){
                                changerValue = hand[j]
                                hand[j] = hand[j+1]
                                hand[j+1] = changerValue
                            }
                        }
                    }
                }
            }
        }
        for(i in 0 until hand.size-4){
            if(smallerByOne(hand[i].secondCardScore, hand[i+1].secondCardScore) &&
                smallerByOne(hand[i+1].secondCardScore, hand[i+2].secondCardScore) &&
                smallerByOne(hand[i+2].secondCardScore, hand[i+3].secondCardScore) &&
                smallerByOne(hand[i+3].secondCardScore, hand[i+4].secondCardScore)){
                for(j in originalHand.indices){
                    if(j<hand.size){
                        hand[j] = originalHand[j]
                    }
                    else{
                        hand.add(originalHand[j])
                    }
                }
                return true
            }
        }
        for(j in originalHand.indices){
            if(j<hand.size){
                hand[j] = originalHand[j]
            }
            else{
                hand.add(originalHand[j])
            }
        }
        return false


    }
    private fun smallerByOne(firstNumber: Int, secondNumber : Int): Boolean{
        if(secondNumber > firstNumber && secondNumber - firstNumber == 1)
            return true
        return false
    }
}
package com.example.casino

class Hand(playerHand : ArrayList<Card>, tableHand :ArrayList<Card> ){
    var wholeHand = ArrayList<Card>()
    var playerHand = ArrayList<Card>()
    var tableHand = ArrayList<Card>()
    init{
        this.playerHand = playerHand
        this.tableHand = tableHand
        wholeHand.add(playerHand[0])
        wholeHand.add(playerHand[1])
        for( i in 0 until tableHand.size)
        {
            if(i<=5)
                wholeHand.add(tableHand[i])
        }

    }
    fun handValue() : Int{
        var value : Int = 0
        if(RoyalFlush(wholeHand).isRoyalFlush()){
            value = 10
            return value
        }
        if(StraightFlush(wholeHand).isStraightFlush()){
            value = 9
            return value
        }

        if(FourKind(wholeHand).isFourKind()){
            value = 8
            return value
        }
        if(FullHouse(wholeHand).isFullHouse()){
            value = 7
            return value
        }
        if(Flush(wholeHand).isFlush()){
            value = 6
            return value
        }
        if(Straight(wholeHand).isStraight()){
            value = 5
            return value
        }
        if(ThreeKind(wholeHand).isThreeKind()){
            value= 4
            return value
        }
        if(TwoPair(wholeHand).isTwoPair()){
            value = 3
            return value
        }
        if(Pair(wholeHand).isPair()){
            value = 2
            return value
        }
        if(HighCard(wholeHand).isHighCard()){
            value = 1
            return value
        }
        value = 0
        return value


    }
}
package com.example.casino

class Card(number : String, color : String) {
    var number : String = ""
    var color : String = ""
    var cardScore : Int = 0
    var secondCardScore : Int = 0
    var blackJackScore : Int = 0
    var secondBlackJackScore : Int = 0
    init{
        this.number = number
        this.color = color
        this.cardScore = cardScore()
        this.secondCardScore = secondCardScore()
        this.blackJackScore = blackJackScore()
        this.secondBlackJackScore = secondBlackJackScore()

    }
    private fun cardScore() : Int{
        var cardScore = 0
        when(number){
            "0"->cardScore = 0
            "1"->cardScore = 1
            "2"->cardScore = 2
            "3"->cardScore = 3
            "4"->cardScore = 4
            "5"->cardScore = 5
            "6"->cardScore = 6
            "7"->cardScore = 7
            "8"->cardScore = 8
            "9"->cardScore = 9
            "10"->cardScore = 10
            "J"->cardScore = 11
            "Q"->cardScore = 12
            "K"->cardScore = 13
            "A"->cardScore = 14
            else->cardScore=-1
        }
        return cardScore
    }
    private fun secondCardScore() : Int{
        var cardScore = 0
        when(number){
            "0"->cardScore = 0
            "1"->cardScore = 1
            "2"->cardScore = 2
            "3"->cardScore = 3
            "4"->cardScore = 4
            "5"->cardScore = 5
            "6"->cardScore = 6
            "7"->cardScore = 7
            "8"->cardScore = 8
            "9"->cardScore = 9
            "10"->cardScore = 10
            "J"->cardScore = 11
            "Q"->cardScore = 12
            "K"->cardScore = 13
            "A"->cardScore = 1
            else->cardScore=-1
        }
        return cardScore
    }
    private fun blackJackScore(): Int{
        var cardScore = 0
        when(number){
            "2"->cardScore = 2
            "3"->cardScore = 3
            "4"->cardScore = 4
            "5"->cardScore = 5
            "6"->cardScore = 6
            "7"->cardScore = 7
            "8"->cardScore = 8
            "9"->cardScore = 9
            "10"->cardScore = 10
            "J"->cardScore = 10
            "Q"->cardScore = 10
            "K"->cardScore = 10
            "A"->cardScore = 11
            else->cardScore=-1
        }
        return cardScore
    }
    private fun secondBlackJackScore(): Int{
        var cardScore = 0
        when(number){
            "2"->cardScore = 2
            "3"->cardScore = 3
            "4"->cardScore = 4
            "5"->cardScore = 5
            "6"->cardScore = 6
            "7"->cardScore = 7
            "8"->cardScore = 8
            "9"->cardScore = 9
            "10"->cardScore = 10
            "J"->cardScore = 10
            "Q"->cardScore = 10
            "K"->cardScore = 10
            "A"->cardScore = 1
            else->cardScore=-1
        }
        return cardScore
    }
}
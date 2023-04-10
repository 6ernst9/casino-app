package com.example.casino

import kotlin.random.Random

class Deck() {
    var deck = ArrayList<Card>(52)
    var shuffledDeck = ArrayList<Card>(52)
    init{
        this.deck = resetDeck()
    }

    fun resetDeck(): ArrayList<Card> {
        deck.add(Card("2", "S"))
        deck.add(Card("3", "S"))
        deck.add(Card("4", "S"))
        deck.add(Card("5", "S"))
        deck.add(Card("6", "S"))
        deck.add(Card("7", "S"))
        deck.add(Card("8", "S"))
        deck.add(Card("9", "S"))
        deck.add(Card("10", "S"))
        deck.add(Card("J", "S"))
        deck.add(Card("Q", "S"))
        deck.add(Card("K", "S"))
        deck.add(Card("A", "S"))
        deck.add(Card("2", "H"))
        deck.add(Card("3", "H"))
        deck.add(Card("4", "H"))
        deck.add(Card("5", "H"))
        deck.add(Card("6", "H"))
        deck.add(Card("7", "H"))
        deck.add(Card("8", "H"))
        deck.add(Card("9", "H"))
        deck.add(Card("10", "H"))
        deck.add(Card("J", "H"))
        deck.add(Card("Q", "H"))
        deck.add(Card("K", "H"))
        deck.add(Card("A", "H"))
        deck.add(Card("2", "D"))
        deck.add(Card("3", "D"))
        deck.add(Card("4", "D"))
        deck.add(Card("5", "D"))
        deck.add(Card("6", "D"))
        deck.add(Card("7", "D"))
        deck.add(Card("8", "D"))
        deck.add(Card("9", "D"))
        deck.add(Card("10", "D"))
        deck.add(Card("J", "D"))
        deck.add(Card("Q", "D"))
        deck.add(Card("K", "D"))
        deck.add(Card("A", "D"))
        deck.add(Card("2", "C"))
        deck.add(Card("3", "C"))
        deck.add(Card("4", "C"))
        deck.add(Card("5", "C"))
        deck.add(Card("6", "C"))
        deck.add(Card("7", "C"))
        deck.add(Card("8", "C"))
        deck.add(Card("9", "C"))
        deck.add(Card("10", "C"))
        deck.add(Card("J", "C"))
        deck.add(Card("Q", "C"))
        deck.add(Card("K", "C"))
        deck.add(Card("A", "C"))
        return deck
    }

    fun shuffle() : ArrayList<Card> {
        val secondDeck = ArrayList<Card>()
        for( i in deck.indices){
            secondDeck.add(Card("0", "P"))
        }
        for( i in deck.indices){
            while(secondDeck[i].color == "P"){
                val randomNr = Random.nextInt( 0, deck.size)
                var sameCounter = 0
                for(j in 0 until i){
                    if(deck[randomNr] == secondDeck[j]){
                        sameCounter ++
                    }
                }
                if(sameCounter == 0){
                    secondDeck[i]=deck[randomNr]
                }
            }
        }
        this.deck = secondDeck
        return secondDeck
    }
}
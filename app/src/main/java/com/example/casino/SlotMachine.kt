package com.example.casino

import kotlin.random.Random

class SlotMachine( ) {
    var oneSlot = ArrayList<String>()
    init {
        this.oneSlot = resetSlot(oneSlot)
    }

    fun resetSlot(oneSlot : ArrayList<String>) : ArrayList<String>{
        oneSlot.clear()
        oneSlot.add("7")
        oneSlot.add("B")
        oneSlot.add("O")
        oneSlot.add("S")
        oneSlot.add("M")
        oneSlot.add("C")
        return oneSlot
    }
    fun returnWinning() : String{
        val randomNr = Random.nextInt(0, 6)
        return oneSlot[randomNr]
    }
}
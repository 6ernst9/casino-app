package com.example.casino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import java.util.*

class SlotMachineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slot_machine)
        window.setNavigationBarColor(resources.getColor(R.color.secondary_black_theme))
        var balance = 100000.0
        val slots = SlotMachine()
        val minBet = balance / 100
        var bet = minBet
        var maxBet = balance
        var totalWin = 0.0
        val firstSlot : ImageView = findViewById(R.id.firstSlot)
        val secondSlot : ImageView = findViewById(R.id.secondSlot)
        val thirdSlot : ImageView = findViewById(R.id.thirdSlot)
        val spinBtn : AppCompatButton = findViewById(R.id.spinBtn)
        val betOneBtn : AppCompatButton = findViewById(R.id.betOneBtn)
        val betMaxBtn : AppCompatButton = findViewById(R.id.betMaxBtn)
        val betNumber: TextView = findViewById(R.id.betNumber)
        val winNumber: TextView = findViewById(R.id.winNumber)
        val totalWinNumber: TextView = findViewById(R.id.totalWinNumber)
        totalWinNumber.text = totalWin.toString()
        val myBalance : TextView = findViewById(R.id.myBalance)
        winNumber.text = "0"
        myBalance.text = balance.toString()
        betNumber.text = minBet.toString()
        var isSpinning = false
        betOneBtn.setOnClickListener{
            betNumber.text = minBet.toString()
            bet = minBet
        }
        betMaxBtn.setOnClickListener{
            betNumber.text = balance.toString()
            bet = maxBet
        }
        spinBtn.setOnClickListener{
            if(!isSpinning){
                isSpinning = true
                val firstSlotValue = slots.returnWinning()
                val secondSlotValue = slots.returnWinning()
                val thirdSlotValue = slots.returnWinning()
                balance -=bet
                myBalance.text = balance.toString()
                maxBet = balance
                val timer = Timer()
                var counterTimer = 0
                val timerTask: TimerTask = object : TimerTask() {
                    override fun run() {
                        counterTimer++
                        this@SlotMachineActivity.runOnUiThread( java.lang.Runnable(){
                            if(counterTimer <= 5){
                                if(counterTimer!=5) {
                                    setDrawable(
                                        firstSlot,
                                        slots.oneSlot[counterTimer]
                                    )
                                    setDrawable(
                                        secondSlot,
                                        slots.oneSlot[counterTimer]
                                    )
                                    setDrawable(
                                        thirdSlot,
                                        slots.oneSlot[counterTimer]
                                    )
                                }
                                else{
                                    setDrawable(firstSlot, firstSlotValue)
                                    setDrawable(
                                        secondSlot,
                                        slots.oneSlot[counterTimer]
                                    )
                                    setDrawable(
                                        thirdSlot,
                                        slots.oneSlot[counterTimer]
                                    )
                                }
                            }
                            if(counterTimer in 5..10){
                                if(counterTimer!=10) {
                                    setDrawable(
                                        secondSlot,
                                        slots.oneSlot[counterTimer-5]
                                    )
                                    setDrawable(
                                        thirdSlot,
                                        slots.oneSlot[counterTimer-5]
                                    )
                                }
                                else{
                                    setDrawable(secondSlot, secondSlotValue)
                                    setDrawable(
                                        thirdSlot,
                                        slots.oneSlot[counterTimer-5]
                                    )
                                }
                            }
                            if(counterTimer in 10..15){
                                if(counterTimer!=15) {

                                    setDrawable(thirdSlot,slots.oneSlot[counterTimer-10])
                                }
                                else{
                                    setDrawable(thirdSlot, thirdSlotValue)
                                }
                            }
                        })

                        if (counterTimer == 15){
                            timer.cancel()
                        }
                    }
                }
                timer.schedule(timerTask, 0, 100)
                val winValue = verifyWinning(firstSlotValue, secondSlotValue, thirdSlotValue)
                balance+=winValue*bet
                Handler().postDelayed({
                    winNumber.text = (winValue*bet).toString()
                    myBalance.text = balance.toString()
                    totalWin+= winValue*bet
                    totalWinNumber.text = totalWin.toString()
                    isSpinning = false
                }, 1500)

            }
        }
    }
    fun verifyWinning(firstSlot: String, secondSlot: String, thirdSlot: String) : Int{
        if(firstSlot == "7" && secondSlot == "7" && thirdSlot == "7"){
            return 30
        }
        if(firstSlot == secondSlot && secondSlot == thirdSlot && thirdSlot != "7"){
            return 10
        }
        if(firstSlot == "7" && secondSlot == "7" && thirdSlot != "7" ||
            firstSlot != "7" && secondSlot == "7" && thirdSlot == "7" ||
            firstSlot == "7" && secondSlot != "7" && thirdSlot == "7"){
            return 4
        }
        if(firstSlot=="7" && secondSlot != "7" && thirdSlot != "7" ||
            firstSlot!="7" && secondSlot == "7" && thirdSlot != "7" ||
            firstSlot!="7" && secondSlot != "7" && thirdSlot == "7") {
            return 1
        }
        else
        {
            return  0
        }
    }
    fun setDrawable(slotNumber : ImageView, slotValue : String){
        when(slotValue){
            "7"->slotNumber.setImageDrawable(resources.getDrawable(R.drawable.seven))
            "O"->slotNumber.setImageDrawable(resources.getDrawable(R.drawable.orange))
            "C"->slotNumber.setImageDrawable(resources.getDrawable(R.drawable.cherry))
            "S"->slotNumber.setImageDrawable(resources.getDrawable(R.drawable.strawberry))
            "B"->slotNumber.setImageDrawable(resources.getDrawable(R.drawable.banana))
            "M"->slotNumber.setImageDrawable(resources.getDrawable(R.drawable.mellon))
            else -> slotNumber.setImageDrawable(resources.getDrawable(R.drawable.spades))
        }

    }
}
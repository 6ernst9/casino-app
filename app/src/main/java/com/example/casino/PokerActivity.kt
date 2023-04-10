package com.example.casino

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import org.w3c.dom.Text

class PokerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poker)
        val middleBtn : AppCompatButton = findViewById(R.id.checkBtn)
        val betBtn : AppCompatButton = findViewById(R.id.callBtn)
        val foldBtn : AppCompatButton = findViewById(R.id.foldBtn)
        val potValue : TextView = findViewById(R.id.potValue)
        val player1 = Player("Robert", 300000.0)
        val player2 = Player("Monty", 300000.0)
        val player3 = Player("Zack", 300000.0)
        val players = ArrayList<Player>()
        players.add(player1)
        players.add(player2)
        players.add(player3)
        val game = PokerGame(players, 5000.0)
        startGame(game)
        refreshAll(middleBtn, betBtn, potValue, game)


        middleBtn.setOnClickListener{
            if(game.currentBet == 0.0){
                println(game.makeAction(game.currentTurn, Action.CHECK, 0.0))
            }
            else{
                println(game.makeAction(game.currentTurn, Action.CALL, 0.0))
            }
            refreshAll(middleBtn, betBtn, potValue, game)
        }
        betBtn.setOnClickListener{
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.raise_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(true)
            val raiseBtn :AppCompatButton =  dialog.findViewById(R.id.raiseBtn)
            val raiseForm : EditText = dialog.findViewById(R.id.raiseForm)
            val statusText : TextView = dialog.findViewById(R.id.statusText)
            var selectedBet = 0.0
            raiseBtn.setOnClickListener{
                if(raiseForm.text!=null && raiseForm.text.toString()!=""){
                    selectedBet = raiseForm.text.toString().toInt().toDouble()
                }
                if(game.makeAction(game.currentTurn, Action.RAISE, selectedBet)){
                    println("true")
                    refreshAll(middleBtn, betBtn, potValue, game)
                    dialog.cancel()
                }
                else{
                    println("false")
                    statusText.text = "Invalid sum"
                }

            }
            dialog.show()
            refreshAll(middleBtn, betBtn, potValue, game)
        }
        foldBtn.setOnClickListener{
            game.makeAction(game.currentTurn, Action.FOLD, 0.0)
            refreshAll(middleBtn, betBtn, potValue, game)
        }


    }
    fun handValue( score : Int) : String{
        when(score){
            1-> return "High Card"
            2-> return "Pair"
            3-> return "Two Pairs"
            4-> return "Three Kind"
            5-> return "Straight"
            6-> return "Flush"
            7-> return "Full House"
            8-> return "Four Kind"
            9-> return "Straight Flush"
            10-> return "Royale Flush"
            else ->return "No Hand"
        }
    }
    fun startGame(game: PokerGame){
        val myBalance : TextView = findViewById(R.id.myBalance)
        myBalance.text = "${(game.players[0].balance/1000).toInt().toString()}k"

        val firstPlayer : TextView = findViewById(R.id.firstPlayerName)
        val secondPlayer : TextView = findViewById(R.id.secondPlayerName)
        val thirdPlayer : TextView = findViewById(R.id.thirdPlayerName)
        val fourthPlayer : TextView = findViewById(R.id.fourthPlayerName)
        val fifthPlayer : TextView = findViewById(R.id.fifthPlayerName)
        val sixthPlayer : TextView = findViewById(R.id.sixthPlayerName)
        val firstPlayerCardview : CardView = findViewById(R.id.player1Img)
        val secondPlayerCardview : CardView = findViewById(R.id.player2Img)
        val thirdPlayerCardview : CardView = findViewById(R.id.player3Img)
        val fourthPlayerCardview : CardView = findViewById(R.id.player4Img)
        val fifthPlayerCardview : CardView = findViewById(R.id.player5Img)
        val sixthPlayerCardview : CardView = findViewById(R.id.player6Img)
        val firstPlayerCardDrawable : ImageView = findViewById(R.id.firstCardDrawable)
        val secondPlayerCardDrawable : ImageView = findViewById(R.id.secondCardDrawable)
        val thirdPlayerCardDrawable : ImageView = findViewById(R.id.thirdCardDrawable)
        val fourthPlayerCardDrawable : ImageView = findViewById(R.id.fourthCardDrawable)
        val fifthPlayerCardDrawable : ImageView = findViewById(R.id.fifthCardDrawable)
        val sixthPlayerCardDrawable : ImageView = findViewById(R.id.sixthCardDrawable)
        val playersCardDrawable = ArrayList<ImageView>()
        playersCardDrawable.add(firstPlayerCardDrawable)
        playersCardDrawable.add(secondPlayerCardDrawable)
        playersCardDrawable.add(thirdPlayerCardDrawable)
        playersCardDrawable.add(fourthPlayerCardDrawable)
        playersCardDrawable.add(fifthPlayerCardDrawable)
        playersCardDrawable.add(sixthPlayerCardDrawable)
        val playersCardView = ArrayList<CardView>()
        playersCardView.add(firstPlayerCardview)
        playersCardView.add(secondPlayerCardview)
        playersCardView.add(thirdPlayerCardview)
        playersCardView.add(fourthPlayerCardview)
        playersCardView.add(fifthPlayerCardview)
        playersCardView.add(sixthPlayerCardview)

        val playersNames = ArrayList<TextView>()
        playersNames.add(firstPlayer)
        playersNames.add(secondPlayer)
        playersNames.add(thirdPlayer)
        playersNames.add(fourthPlayer)
        playersNames.add(fifthPlayer)
        playersNames.add(sixthPlayer)
        val firstPlayerBetLayout : ConstraintLayout = findViewById(R.id.firstPlayerBetLayout)
        val firstPlayerBet : TextView = findViewById(R.id.firstPlayerBetValue)
        val secondPlayerBetLayout : ConstraintLayout = findViewById(R.id.secondPlayerBetLayout)
        val secondPlayerBet : TextView = findViewById(R.id.secondPlayerBetValue)
        val thirdPlayerBetLayout : ConstraintLayout = findViewById(R.id.thirdPlayerBetLayout)
        val thirdPlayerBet : TextView = findViewById(R.id.thirdPlayerBetValue)
        val fourthPlayerBetLayout : ConstraintLayout = findViewById(R.id.fourthPlayerBetLayout)
        val fourthPlayerBet : TextView = findViewById(R.id.fourthPlayerBetValue)
        val fifthPlayerBetLayout : ConstraintLayout = findViewById(R.id.fifthPlayerBetLayout)
        val fifthPlayerBet : TextView = findViewById(R.id.fifthPlayerBetValue)
        val sixthPlayerBetLayout : ConstraintLayout = findViewById(R.id.sixthPlayerBetLayout)
        val sixthPlayerBet : TextView = findViewById(R.id.sixthPlayerBetValue)
        val playersBetLayouts = ArrayList<ConstraintLayout>()
        playersBetLayouts.add(firstPlayerBetLayout)
        playersBetLayouts.add(secondPlayerBetLayout)
        playersBetLayouts.add(thirdPlayerBetLayout)
        playersBetLayouts.add(fourthPlayerBetLayout)
        playersBetLayouts.add(fifthPlayerBetLayout)
        playersBetLayouts.add(sixthPlayerBetLayout)
        val playersBetValue = ArrayList<TextView>()
        playersBetValue.add(firstPlayerBet)
        playersBetValue.add(secondPlayerBet)
        playersBetValue.add(thirdPlayerBet)
        playersBetValue.add(fourthPlayerBet)
        playersBetValue.add(fifthPlayerBet)
        playersBetValue.add(sixthPlayerBet)
        for(i in playersNames.indices){
            if(i<=game.players.size-1){
                playersNames[i].text = game.players[i].name
                if(game.betPaidByPlayers[i]!=0.0){
                    playersBetLayouts[i].isVisible = true
                    playersBetValue[i].isVisible = true
                    playersBetValue[i].text = "${(game.betPaidByPlayers[i]/1000).toInt().toString()}k"
                }
                else{
                    playersBetLayouts[i].isVisible = false
                }
            }
            else{
                playersNames[i].text = "Invite"
                playersCardDrawable[i].setImageDrawable(resources.getDrawable(R.drawable.ic_add))
                playersBetLayouts[i].isVisible = false
            }
        }

    }
    fun refreshAll(middleBtn : AppCompatButton, betBtn : AppCompatButton, potValue : TextView, game : PokerGame){
        middleBtn.text = game.middleButton()
        betBtn.text = game.betButton()
        potValue.text = "${(game.pot)/1000.0}k"
        val statusTitle : AppCompatButton = findViewById(R.id.statusTitle)
        statusTitle.text = "${game.currentTurn.name} Turn"
        val firstPlayer : TextView = findViewById(R.id.firstPlayerName)
        val secondPlayer : TextView = findViewById(R.id.secondPlayerName)
        val thirdPlayer : TextView = findViewById(R.id.thirdPlayerName)
        val fourthPlayer : TextView = findViewById(R.id.fourthPlayerName)
        val fifthPlayer : TextView = findViewById(R.id.fifthPlayerName)
        val sixthPlayer : TextView = findViewById(R.id.sixthPlayerName)
        val firstPlayerCardview : CardView = findViewById(R.id.player1Img)
        val secondPlayerCardview : CardView = findViewById(R.id.player2Img)
        val thirdPlayerCardview : CardView = findViewById(R.id.player3Img)
        val fourthPlayerCardview : CardView = findViewById(R.id.player4Img)
        val fifthPlayerCardview : CardView = findViewById(R.id.player5Img)
        val sixthPlayerCardview : CardView = findViewById(R.id.player6Img)
        val firstPlayerCardDrawable : ImageView = findViewById(R.id.firstCardDrawable)
        val secondPlayerCardDrawable : ImageView = findViewById(R.id.secondCardDrawable)
        val thirdPlayerCardDrawable : ImageView = findViewById(R.id.thirdCardDrawable)
        val fourthPlayerCardDrawable : ImageView = findViewById(R.id.fourthCardDrawable)
        val fifthPlayerCardDrawable : ImageView = findViewById(R.id.fifthCardDrawable)
        val sixthPlayerCardDrawable : ImageView = findViewById(R.id.sixthCardDrawable)
        val playersCardDrawable = ArrayList<ImageView>()
        playersCardDrawable.add(firstPlayerCardDrawable)
        playersCardDrawable.add(secondPlayerCardDrawable)
        playersCardDrawable.add(thirdPlayerCardDrawable)
        playersCardDrawable.add(fourthPlayerCardDrawable)
        playersCardDrawable.add(fifthPlayerCardDrawable)
        playersCardDrawable.add(sixthPlayerCardDrawable)
        val playersCardView = ArrayList<CardView>()
        playersCardView.add(firstPlayerCardview)
        playersCardView.add(secondPlayerCardview)
        playersCardView.add(thirdPlayerCardview)
        playersCardView.add(fourthPlayerCardview)
        playersCardView.add(fifthPlayerCardview)
        playersCardView.add(sixthPlayerCardview)


        val playersNames = ArrayList<TextView>()
        playersNames.add(firstPlayer)
        playersNames.add(secondPlayer)
        playersNames.add(thirdPlayer)
        playersNames.add(fourthPlayer)
        playersNames.add(fifthPlayer)
        playersNames.add(sixthPlayer)
        val firstPlayerBetLayout : ConstraintLayout = findViewById(R.id.firstPlayerBetLayout)
        val firstPlayerBet : TextView = findViewById(R.id.firstPlayerBetValue)
        val secondPlayerBetLayout : ConstraintLayout = findViewById(R.id.secondPlayerBetLayout)
        val secondPlayerBet : TextView = findViewById(R.id.secondPlayerBetValue)
        val thirdPlayerBetLayout : ConstraintLayout = findViewById(R.id.thirdPlayerBetLayout)
        val thirdPlayerBet : TextView = findViewById(R.id.thirdPlayerBetValue)
        val fourthPlayerBetLayout : ConstraintLayout = findViewById(R.id.fourthPlayerBetLayout)
        val fourthPlayerBet : TextView = findViewById(R.id.fourthPlayerBetValue)
        val fifthPlayerBetLayout : ConstraintLayout = findViewById(R.id.fifthPlayerBetLayout)
        val fifthPlayerBet : TextView = findViewById(R.id.fifthPlayerBetValue)
        val playersBetLayouts = ArrayList<ConstraintLayout>()
        val sixthPlayerBetLayout : ConstraintLayout = findViewById(R.id.sixthPlayerBetLayout)
        val sixthPlayerBet : TextView = findViewById(R.id.sixthPlayerBetValue)
        playersBetLayouts.add(firstPlayerBetLayout)
        playersBetLayouts.add(secondPlayerBetLayout)
        playersBetLayouts.add(thirdPlayerBetLayout)
        playersBetLayouts.add(fourthPlayerBetLayout)
        playersBetLayouts.add(fifthPlayerBetLayout)
        playersBetLayouts.add(sixthPlayerBetLayout)
        val playersBetValue = ArrayList<TextView>()
        playersBetValue.add(firstPlayerBet)
        playersBetValue.add(secondPlayerBet)
        playersBetValue.add(thirdPlayerBet)
        playersBetValue.add(fourthPlayerBet)
        playersBetValue.add(fifthPlayerBet)
        playersBetValue.add(sixthPlayerBet)
        playersCardView[0].setCardBackgroundColor(resources.getColor(R.color.yellow))
        playersCardView[1].setCardBackgroundColor(resources.getColor(R.color.pink_theme))
        playersCardView[2].setCardBackgroundColor(resources.getColor(R.color.homecard_blue))
        playersCardView[3].setCardBackgroundColor(resources.getColor(R.color.green))
        playersCardView[4].setCardBackgroundColor(resources.getColor(R.color.theme_purple))
        playersCardView[5].setCardBackgroundColor(resources.getColor(R.color.blue_theme))
        for(i in playersNames.indices){
            if(game.status==PokerPhases.PRE_FLOP &&game.players.size>=3){
                if(game.currentTurn == game.players[2]) {
                    if (i <= game.players.size - 1) {
                        playersNames[i].text = game.players[i].name
                        playersCardDrawable[i].setImageDrawable(resources.getDrawable(R.drawable.ic_player))
                    } else {
                        playersNames[i].text = "Invite"
                        playersCardDrawable[i].setImageDrawable(resources.getDrawable(R.drawable.ic_add))
                        playersBetLayouts[i].isVisible = false
                    }
                }
            }
            else if( game.status==PokerPhases.PRE_FLOP &&game.players.size< 2 && game.currentTurn == game.players[0]){
                if(i<=game.players.size-1){
                    playersNames[i].text = game.players[i].name
                    playersCardDrawable[i].setImageDrawable(resources.getDrawable(R.drawable.ic_player))
                }
                else{
                    playersNames[i].text = "Invite"
                    playersCardDrawable[i].setImageDrawable(resources.getDrawable(R.drawable.ic_add))
                    playersBetLayouts[i].isVisible = false
                }
            }
            else{
                break
            }
        }
        var equalNumbers = 0
        for(i in game.backUpPlayers.indices){
            for(j in game.players.indices){
                if(game.players[j].name==game.backUpPlayers[i].name)
                    equalNumbers++
                if(equalNumbers==0 && j == game.players.size-1){
                    playersCardView[i].setCardBackgroundColor(resources.getColor(R.color.dark_theme))
                }
            }
            equalNumbers = 0

        }

        for(i in playersNames.indices){
            if(i<=game.players.size-1){
                if(game.betPaidByPlayers[i]!=0.0){
                    playersBetLayouts[i].isVisible = true
                    playersBetValue[i].isVisible = true
                    playersBetValue[i].text = "${(game.betPaidByPlayers[i]/1000).toInt().toString()}k"
                }
                else{
                    playersBetLayouts[i].isVisible = false
                }
            }
            else{

                playersBetLayouts[i].isVisible = false
            }
        }

        val firstCard : ConstraintLayout = findViewById(R.id.firstCard)
        val firstCardNumber: TextView = findViewById(R.id.firstCardNumber)
        val firstCardColor: ImageView = findViewById(R.id.firstCardColor)
        val secondCard : ConstraintLayout = findViewById(R.id.secondCard)
        val secondCardNumber: TextView = findViewById(R.id.secondCardNumber)
        val secondCardColor: ImageView = findViewById(R.id.secondCardColor)
        val thirdCard : ConstraintLayout = findViewById(R.id.thirdCard)
        val thirdCardNumber: TextView = findViewById(R.id.thirdCardNumber)
        val thirdCardColor: ImageView = findViewById(R.id.thirdCardColor)
        val fourthCard : ConstraintLayout = findViewById(R.id.fourthCard)
        val fourthCardNumber: TextView = findViewById(R.id.fourthCardNumber)
        val fourthCardColor: ImageView = findViewById(R.id.fourthCardColor)
        val fifthCard : ConstraintLayout = findViewById(R.id.fifthCard)
        val fifthCardNumber: TextView = findViewById(R.id.fifthCardNumber)
        val fifthCardColor: ImageView = findViewById(R.id.fifthCardColor)
        val myFirstCard : ConstraintLayout = findViewById(R.id.myFirstCard)
        val myFirstCardColor : ImageView = findViewById(R.id.myFirstCardColor)
        val myFirstCardNumber : TextView = findViewById(R.id.myFirstCardNumber)
        val mySecondCard : ConstraintLayout = findViewById(R.id.mySecondCard)
        val mySecondCardColor : ImageView = findViewById(R.id.mySecondCardColor)
        val mySecondCardNumber : TextView = findViewById(R.id.mySecondCardNumber)
        val myBalance : TextView = findViewById(R.id.myBalance)
        myBalance.text = "${(game.players[0].balance/1000).toInt().toString()}k"
        if(game.status == PokerPhases.PRE_FLOP &&game.players.size>=3){
            if(game.currentTurn == game.players[2]) {
                Handler().postDelayed({
                    myFirstCard.isVisible = true
                    mySecondCard.isVisible = true
                }, 2000)
            }
        }
        else if(game.status==PokerPhases.PRE_FLOP &&game.players.size< 2 && game.currentTurn == game.players[0]){
            Handler().postDelayed({
                myFirstCard.isVisible = true
                mySecondCard.isVisible = true
            }, 2000)
        }
        else{
            myFirstCard.isVisible = true
            mySecondCard.isVisible = true
        }
        val winnersTitle : TextView = findViewById(R.id.winnersText)
        if(game.winners.size!=0){
            myFirstCard.isVisible = false
            mySecondCard.isVisible = false
            winnersTitle.isVisible = true
            winnersTitle.text = "Winner ${handValue(game.winnerValue)}"
            for(i in game.winners.indices){
                winnersTitle.text = "${winnersTitle.text}\nPlayer ${game.winners[i].name}"
            }
            Handler().postDelayed({
                winnersTitle.isVisible = false
                myFirstCard.isVisible = true
                mySecondCard.isVisible = true
                winnersTitle.text= ""
            }, 5000)

        }

        myFirstCardNumber.text = game.players[0].hand[0].number
        setColor(myFirstCardColor, game.players[0].hand[0].color)
        mySecondCardNumber.text = game.players[0].hand[1].number
        setColor(mySecondCardColor, game.players[0].hand[1].color)
        when(game.tableHand.size){
            0->{
                firstCard.isVisible = false
                secondCard.isVisible = false
                thirdCard.isVisible = false
                fourthCard.isVisible = false
                fifthCard.isVisible = false
            }
            3->{
                firstCard.isVisible = true
                secondCard.isVisible = true
                thirdCard.isVisible = true
                firstCardNumber.text = game.tableHand[0].number
                setColor(firstCardColor, game.tableHand[0].color)
                secondCardNumber.text = game.tableHand[1].number
                setColor(secondCardColor, game.tableHand[1].color)
                thirdCardNumber.text = game.tableHand[2].number
                setColor(thirdCardColor, game.tableHand[2].color)
            }
            4->{
                firstCard.isVisible = true
                secondCard.isVisible = true
                thirdCard.isVisible = true
                fourthCard.isVisible = true
                firstCardNumber.text = game.tableHand[0].number
                setColor(firstCardColor, game.tableHand[0].color)
                secondCardNumber.text = game.tableHand[1].number
                setColor(secondCardColor, game.tableHand[1].color)
                thirdCardNumber.text = game.tableHand[2].number
                setColor(thirdCardColor, game.tableHand[2].color)
                fourthCardNumber.text = game.tableHand[3].number
                setColor(fourthCardColor, game.tableHand[3].color)
            }
            5->{
                firstCard.isVisible = true
                secondCard.isVisible = true
                thirdCard.isVisible = true
                fourthCard.isVisible = true
                fifthCard.isVisible = true
                firstCardNumber.text = game.tableHand[0].number
                setColor(firstCardColor, game.tableHand[0].color)
                secondCardNumber.text = game.tableHand[1].number
                setColor(secondCardColor, game.tableHand[1].color)
                thirdCardNumber.text = game.tableHand[2].number
                setColor(thirdCardColor, game.tableHand[2].color)
                fourthCardNumber.text = game.tableHand[3].number
                setColor(fourthCardColor, game.tableHand[3].color)
                fifthCardNumber.text = game.tableHand[4].number
                setColor(fifthCardColor, game.tableHand[4].color)
            }
            else ->{
                firstCard.isVisible = false
                secondCard.isVisible = false
                thirdCard.isVisible = false
                fourthCard.isVisible = false
                fifthCard.isVisible = false
            }
        }
    }

    fun setColor(firstCardColor: ImageView, color : String) {
        when(color){
            "S"->firstCardColor.setImageDrawable(resources.getDrawable(R.drawable.spades))
            "D"->firstCardColor.setImageDrawable(resources.getDrawable(R.drawable.diamond))
            "C"->firstCardColor.setImageDrawable(resources.getDrawable(R.drawable.clubs))
            "H"->firstCardColor.setImageDrawable(resources.getDrawable(R.drawable.hearts))
            else->firstCardColor.drawable
        }
    }
}
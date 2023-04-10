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

class BlackJackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_black_jack)
        val hitBtn : AppCompatButton = findViewById(R.id.hitBtn)
        val betBtn : AppCompatButton = findViewById(R.id.betBtn)
        val stayBtn : AppCompatButton = findViewById(R.id.stayBtn)
        val player1 = Player("Robert", 300000.0)
        val player2 = Player("Monty", 300000.0)
        val player3 = Player("Zack", 300000.0)
        val players = ArrayList<Player>()
        players.add(player1)
        players.add(player2)
        players.add(player3)
        val smallBlind = 5000.0
        val game = BlackJackGame(players, smallBlind)
        startGame(game)
        refreshAll(game)
        hitBtn.setOnClickListener{
            println(game.makeAction(game.currentTurn, BlackJackAction.HIT, 0.0))
            refreshAll(game)
        }
        stayBtn.setOnClickListener{
            println(game.makeAction(game.currentTurn, BlackJackAction.STAY, 0.0))
            refreshAll(game)
        }
        betBtn.setOnClickListener{
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.raise_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(true)
            val raiseBtn :AppCompatButton =  dialog.findViewById(R.id.raiseBtn)
            val raiseForm : EditText = dialog.findViewById(R.id.raiseForm)
            val addBtn : CardView = dialog.findViewById(R.id.addValue)
            raiseForm.setText(smallBlind.toInt().toString())
            var selectedBet = 0.0
            addBtn.setOnClickListener{
                raiseForm.setText("${(raiseForm.text.toString().toInt() + smallBlind.toInt()).toString()}")
            }
            raiseBtn.setOnClickListener{
                if(raiseForm.text!=null && raiseForm.text.toString()!=""){
                    selectedBet = raiseForm.text.toString().toInt().toDouble()
                }
                if(game.makeAction(game.currentTurn, BlackJackAction.BET, selectedBet)){
                    println("true")
                    refreshAll(game)
                    dialog.cancel()
                }
                else{
                    println("false")
                }

            }
            dialog.show()
            refreshAll(game)
        }
    }

    private fun startGame(game: BlackJackGame) {
        val myBalance : TextView = findViewById(R.id.myBalance)
        myBalance.text = "${(game.players[0].balance/1000).toInt().toString()}k"
        val hitBtn : AppCompatButton = findViewById(R.id.hitBtn)
        val betBtn : AppCompatButton = findViewById(R.id.betBtn)
        val stayBtn : AppCompatButton = findViewById(R.id.stayBtn)
        if(game.status== BlackJackPhase.BETTING){
            betBtn.isVisible = true
            hitBtn.isVisible =false
            stayBtn.isVisible=false
        }
        else{
            betBtn.isVisible = false
            hitBtn.isVisible =true
            stayBtn.isVisible=true
        }

        val firstPlayer : TextView = findViewById(R.id.firstPlayerName)
        val secondPlayer : TextView = findViewById(R.id.secondPlayerName)
        val thirdPlayer : TextView = findViewById(R.id.thirdPlayerName)

        val firstPlayerCardview : CardView = findViewById(R.id.player1Img)
        val secondPlayerCardview : CardView = findViewById(R.id.player2Img)
        val thirdPlayerCardview : CardView = findViewById(R.id.player3Img)

        val firstPlayerCardDrawable : ImageView = findViewById(R.id.firstCardDrawable)
        val secondPlayerCardDrawable : ImageView = findViewById(R.id.secondCardDrawable)
        val thirdPlayerCardDrawable : ImageView = findViewById(R.id.thirdCardDrawable)

        val playersCardDrawable = ArrayList<ImageView>()
        playersCardDrawable.add(firstPlayerCardDrawable)
        playersCardDrawable.add(secondPlayerCardDrawable)
        playersCardDrawable.add(thirdPlayerCardDrawable)

        val playersCardView = ArrayList<CardView>()
        playersCardView.add(firstPlayerCardview)
        playersCardView.add(secondPlayerCardview)
        playersCardView.add(thirdPlayerCardview)

        val playersNames = ArrayList<TextView>()
        playersNames.add(firstPlayer)
        playersNames.add(secondPlayer)
        playersNames.add(thirdPlayer)
        val firstPlayerBetLayout : ConstraintLayout = findViewById(R.id.firstPlayerBetLayout)
        val firstPlayerBet : TextView = findViewById(R.id.firstPlayerBetValue)
        val secondPlayerBetLayout : ConstraintLayout = findViewById(R.id.secondPlayerBetLayout)
        val secondPlayerBet : TextView = findViewById(R.id.secondPlayerBetValue)
        val thirdPlayerBetLayout : ConstraintLayout = findViewById(R.id.thirdPlayerBetLayout)
        val thirdPlayerBet : TextView = findViewById(R.id.thirdPlayerBetValue)

        val playersBetLayouts = ArrayList<ConstraintLayout>()
        playersBetLayouts.add(firstPlayerBetLayout)
        playersBetLayouts.add(secondPlayerBetLayout)
        playersBetLayouts.add(thirdPlayerBetLayout)

        val playersBetValue = ArrayList<TextView>()
        playersBetValue.add(firstPlayerBet)
        playersBetValue.add(secondPlayerBet)
        playersBetValue.add(thirdPlayerBet)

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
    private fun refreshAll(game: BlackJackGame){
        val hitBtn : AppCompatButton = findViewById(R.id.hitBtn)
        val betBtn : AppCompatButton = findViewById(R.id.betBtn)
        val stayBtn : AppCompatButton = findViewById(R.id.stayBtn)
        if(game.status== BlackJackPhase.BETTING){
            betBtn.isVisible = true
            hitBtn.isVisible =false
            stayBtn.isVisible=false
        }
        else{
            betBtn.isVisible = false
            hitBtn.isVisible =true
            stayBtn.isVisible=true
        }
        val statusTitle : AppCompatButton = findViewById(R.id.statusTitle)
        statusTitle.text = "${game.currentTurn.name} Turn"
        val firstPlayer : TextView = findViewById(R.id.firstPlayerName)
        val secondPlayer : TextView = findViewById(R.id.secondPlayerName)
        val thirdPlayer : TextView = findViewById(R.id.thirdPlayerName)
        val firstPlayerCardview : CardView = findViewById(R.id.player1Img)
        val secondPlayerCardview : CardView = findViewById(R.id.player2Img)
        val thirdPlayerCardview : CardView = findViewById(R.id.player3Img)
        val firstPlayerCardDrawable : ImageView = findViewById(R.id.firstCardDrawable)
        val secondPlayerCardDrawable : ImageView = findViewById(R.id.secondCardDrawable)
        val thirdPlayerCardDrawable : ImageView = findViewById(R.id.thirdCardDrawable)
        val playersCardDrawable = ArrayList<ImageView>()
        playersCardDrawable.add(firstPlayerCardDrawable)
        playersCardDrawable.add(secondPlayerCardDrawable)
        playersCardDrawable.add(thirdPlayerCardDrawable)
        val playersCardView = ArrayList<CardView>()
        playersCardView.add(firstPlayerCardview)
        playersCardView.add(secondPlayerCardview)
        playersCardView.add(thirdPlayerCardview)
        val playersNames = ArrayList<TextView>()
        playersNames.add(firstPlayer)
        playersNames.add(secondPlayer)
        playersNames.add(thirdPlayer)
        val firstPlayerBetLayout : ConstraintLayout = findViewById(R.id.firstPlayerBetLayout)
        val firstPlayerBet : TextView = findViewById(R.id.firstPlayerBetValue)
        val secondPlayerBetLayout : ConstraintLayout = findViewById(R.id.secondPlayerBetLayout)
        val secondPlayerBet : TextView = findViewById(R.id.secondPlayerBetValue)
        val thirdPlayerBetLayout : ConstraintLayout = findViewById(R.id.thirdPlayerBetLayout)
        val thirdPlayerBet : TextView = findViewById(R.id.thirdPlayerBetValue)
        val playersBetLayouts = ArrayList<ConstraintLayout>()
        playersBetLayouts.add(firstPlayerBetLayout)
        playersBetLayouts.add(secondPlayerBetLayout)
        playersBetLayouts.add(thirdPlayerBetLayout)
        val playersBetValue = ArrayList<TextView>()
        playersBetValue.add(firstPlayerBet)
        playersBetValue.add(secondPlayerBet)
        playersBetValue.add(thirdPlayerBet)
        playersCardView[0].setCardBackgroundColor(resources.getColor(R.color.yellow))
        playersCardView[1].setCardBackgroundColor(resources.getColor(R.color.pink_theme))
        playersCardView[2].setCardBackgroundColor(resources.getColor(R.color.green))
        for(i in playersNames.indices){
            if( game.status==BlackJackPhase.BETTING && game.currentTurn == game.players[0]){
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
        for(i in game.players.indices){
            if(game.betPaidByPlayers[i]!=0.0){
                playersBetLayouts[i].isVisible = true
                playersBetValue[i].text = "${(game.betPaidByPlayers[i]/1000).toInt().toString()}k"
            }
            else{
                playersBetLayouts[i].isVisible = false
            }
        }
        val player1firstCard : ConstraintLayout = findViewById(R.id.myFirstCard)
        val player1firstCardNumber: TextView = findViewById(R.id.myFirstCardNumber)
        val player1firstCardColor: ImageView = findViewById(R.id.myFirstCardColor)
        val player1secondCard : ConstraintLayout = findViewById(R.id.mySecondCard)
        val player1secondCardNumber: TextView = findViewById(R.id.mySecondCardNumber)
        val player1secondCardColor: ImageView = findViewById(R.id.mySecondCardColor)
        val player1thirdCard : ConstraintLayout = findViewById(R.id.myThirdCard)
        val player1thirdCardNumber: TextView = findViewById(R.id.myThirdCardNumber)
        val player1thirdCardColor: ImageView = findViewById(R.id.myThirdCardColor)
        val player1forthCard : ConstraintLayout = findViewById(R.id.myForthCard)
        val player1forthCardNumber: TextView = findViewById(R.id.myForthCardNumber)
        val player1forthCardColor: ImageView = findViewById(R.id.myForthCardColor)
        val player1fifthCard : ConstraintLayout = findViewById(R.id.myFifthCard)
        val player1fifthCardNumber: TextView = findViewById(R.id.myFifthCardNumber)
        val player1fifthCardColor: ImageView = findViewById(R.id.myFifthCardColor)
        val player1SixthCard : ConstraintLayout = findViewById(R.id.mySixthCard)
        val player1SixthCardNumber: TextView = findViewById(R.id.mySixthCardNumber)
        val player1SixthCardColor: ImageView = findViewById(R.id.mySixthCardColor)
        val player1SeventhCard : ConstraintLayout = findViewById(R.id.mySeventhCard)
        val player1SeventhCardNumber: TextView = findViewById(R.id.mySeventhCardNumber)
        val player1SeventhCardColor: ImageView = findViewById(R.id.mySeventhCardColor)
        val player1EightCard : ConstraintLayout = findViewById(R.id.myEightCard)
        val player1EightCardNumber: TextView = findViewById(R.id.myEightCardNumber)
        val player1EightCardColor: ImageView = findViewById(R.id.myEightCardColor)
        val player1NinthCard : ConstraintLayout = findViewById(R.id.myNinthCard)
        val player1NinthCardNumber: TextView = findViewById(R.id.myNinthCardNumber)
        val player1NinthCardColor: ImageView = findViewById(R.id.myNinthCardColor)
        val player1TenthCard : ConstraintLayout = findViewById(R.id.myTenthCard)
        val player1TenthCardNumber: TextView = findViewById(R.id.myTenthCardNumber)
        val player1TenthCardColor: ImageView = findViewById(R.id.myTenthCardColor)

        val player1Colors = ArrayList<ImageView>()
        player1Colors.add(player1firstCardColor)
        player1Colors.add(player1secondCardColor)
        player1Colors.add(player1thirdCardColor)
        player1Colors.add(player1forthCardColor)
        player1Colors.add(player1fifthCardColor)
        player1Colors.add(player1SixthCardColor)
        player1Colors.add(player1SeventhCardColor)
        player1Colors.add(player1EightCardColor)
        player1Colors.add(player1NinthCardColor)
        player1Colors.add(player1TenthCardColor)

        val player1Numbers = ArrayList<TextView>()
        player1Numbers.add(player1firstCardNumber)
        player1Numbers.add(player1secondCardNumber)
        player1Numbers.add(player1thirdCardNumber)
        player1Numbers.add(player1forthCardNumber)
        player1Numbers.add(player1fifthCardNumber)
        player1Numbers.add(player1SixthCardNumber)
        player1Numbers.add(player1SeventhCardNumber)
        player1Numbers.add(player1EightCardNumber)
        player1Numbers.add(player1NinthCardNumber)
        player1Numbers.add(player1TenthCardNumber)

        val player1Cards = ArrayList<ConstraintLayout>()
        player1Cards.add(player1firstCard)
        player1Cards.add(player1secondCard)
        player1Cards.add(player1thirdCard)
        player1Cards.add(player1forthCard)
        player1Cards.add(player1fifthCard)
        player1Cards.add(player1SixthCard)
        player1Cards.add(player1SeventhCard)
        player1Cards.add(player1EightCard)
        player1Cards.add(player1NinthCard)
        player1Cards.add(player1TenthCard)

        val player2firstCard : ConstraintLayout = findViewById(R.id.player2FirstCard)
        val player2firstCardNumber: TextView = findViewById(R.id.player2FirstCardNumber)
        val player2firstCardColor: ImageView = findViewById(R.id.player2FirstCardColor)
        val player2secondCard : ConstraintLayout = findViewById(R.id.player2SecondCard)
        val player2secondCardNumber: TextView = findViewById(R.id.player2SecondCardNumber)
        val player2secondCardColor: ImageView = findViewById(R.id.player2SecondCardColor)
        val player2thirdCard : ConstraintLayout = findViewById(R.id.player2ThirdCard)
        val player2thirdCardNumber: TextView = findViewById(R.id.player2ThirdCardNumber)
        val player2thirdCardColor: ImageView = findViewById(R.id.player2ThirdCardColor)
        val player2forthCard : ConstraintLayout = findViewById(R.id.player2ForthCard)
        val player2forthCardNumber: TextView = findViewById(R.id.player2ForthCardNumber)
        val player2forthCardColor: ImageView = findViewById(R.id.player2ForthCardColor)
        val player2fifthCard : ConstraintLayout = findViewById(R.id.player2FifthCard)
        val player2fifthCardNumber: TextView = findViewById(R.id.player2FifthCardNumber)
        val player2fifthCardColor: ImageView = findViewById(R.id.player2FifthCardColor)
        val player2SixthCard : ConstraintLayout = findViewById(R.id.player2SixthCard)
        val player2SixthCardNumber: TextView = findViewById(R.id.player2SixthCardNumber)
        val player2SixthCardColor: ImageView = findViewById(R.id.player2SixthCardColor)
        val player2SeventhCard : ConstraintLayout = findViewById(R.id.player2SeventhCard)
        val player2SeventhCardNumber: TextView = findViewById(R.id.player2SeventhCardNumber)
        val player2SeventhCardColor: ImageView = findViewById(R.id.player2SeventhCardColor)
        val player2EightCard : ConstraintLayout = findViewById(R.id.player2EightCard)
        val player2EightCardNumber: TextView = findViewById(R.id.player2EightCardNumber)
        val player2EightCardColor: ImageView = findViewById(R.id.player2EightCardColor)
        val player2NinthCard : ConstraintLayout = findViewById(R.id.player2NinthCard)
        val player2NinthCardNumber: TextView = findViewById(R.id.player2NinthCardNumber)
        val player2NinthCardColor: ImageView = findViewById(R.id.player2NinthCardColor)
        val player2TenthCard : ConstraintLayout = findViewById(R.id.player2TenthCard)
        val player2TenthCardNumber: TextView = findViewById(R.id.player2TenthCardNumber)
        val player2TenthCardColor: ImageView = findViewById(R.id.player2TenthCardColor)

        val player2Colors = ArrayList<ImageView>()
        player2Colors.add(player2firstCardColor)
        player2Colors.add(player2secondCardColor)
        player2Colors.add(player2thirdCardColor)
        player2Colors.add(player2forthCardColor)
        player2Colors.add(player2fifthCardColor)
        player2Colors.add(player2SixthCardColor)
        player2Colors.add(player2SeventhCardColor)
        player2Colors.add(player2EightCardColor)
        player2Colors.add(player2NinthCardColor)
        player2Colors.add(player2TenthCardColor)

        val player2Numbers = ArrayList<TextView>()
        player2Numbers.add(player2firstCardNumber)
        player2Numbers.add(player2secondCardNumber)
        player2Numbers.add(player2thirdCardNumber)
        player2Numbers.add(player2forthCardNumber)
        player2Numbers.add(player2fifthCardNumber)
        player2Numbers.add(player2SixthCardNumber)
        player2Numbers.add(player2SeventhCardNumber)
        player2Numbers.add(player2EightCardNumber)
        player2Numbers.add(player2NinthCardNumber)
        player2Numbers.add(player2TenthCardNumber)

        val player2Cards = ArrayList<ConstraintLayout>()
        player2Cards.add(player2firstCard)
        player2Cards.add(player2secondCard)
        player2Cards.add(player2thirdCard)
        player2Cards.add(player2forthCard)
        player2Cards.add(player2fifthCard)
        player2Cards.add(player2SixthCard)
        player2Cards.add(player2SeventhCard)
        player2Cards.add(player2EightCard)
        player2Cards.add(player2NinthCard)
        player2Cards.add(player2TenthCard)

        val player3firstCard : ConstraintLayout = findViewById(R.id.player3FirstCard)
        val player3firstCardNumber: TextView = findViewById(R.id.player3FirstCardNumber)
        val player3firstCardColor: ImageView = findViewById(R.id.player3FirstCardColor)
        val player3secondCard : ConstraintLayout = findViewById(R.id.player3SecondCard)
        val player3secondCardNumber: TextView = findViewById(R.id.player3SecondCardNumber)
        val player3secondCardColor: ImageView = findViewById(R.id.player3SecondCardColor)
        val player3thirdCard : ConstraintLayout = findViewById(R.id.player3ThirdCard)
        val player3thirdCardNumber: TextView = findViewById(R.id.player3ThirdCardNumber)
        val player3thirdCardColor: ImageView = findViewById(R.id.player3ThirdCardColor)
        val player3forthCard : ConstraintLayout = findViewById(R.id.player3ForthCard)
        val player3forthCardNumber: TextView = findViewById(R.id.player3ForthCardNumber)
        val player3forthCardColor: ImageView = findViewById(R.id.player3ForthCardColor)
        val player3fifthCard : ConstraintLayout = findViewById(R.id.player3FifthCard)
        val player3fifthCardNumber: TextView = findViewById(R.id.player3FifthCardNumber)
        val player3fifthCardColor: ImageView = findViewById(R.id.player3FifthCardColor)
        val player3SixthCard : ConstraintLayout = findViewById(R.id.player3SixthCard)
        val player3SixthCardNumber: TextView = findViewById(R.id.player3SixthCardNumber)
        val player3SixthCardColor: ImageView = findViewById(R.id.player3SixthCardColor)
        val player3SeventhCard : ConstraintLayout = findViewById(R.id.player3SeventhCard)
        val player3SeventhCardNumber: TextView = findViewById(R.id.player3SeventhCardNumber)
        val player3SeventhCardColor: ImageView = findViewById(R.id.player3SeventhCardColor)
        val player3EightCard : ConstraintLayout = findViewById(R.id.player3EightCard)
        val player3EightCardNumber: TextView = findViewById(R.id.player3EightCardNumber)
        val player3EightCardColor: ImageView = findViewById(R.id.player3EightCardColor)
        val player3NinthCard : ConstraintLayout = findViewById(R.id.player3NinthCard)
        val player3NinthCardNumber: TextView = findViewById(R.id.player3NinthCardNumber)
        val player3NinthCardColor: ImageView = findViewById(R.id.player3NinthCardColor)
        val player3TenthCard : ConstraintLayout = findViewById(R.id.player3TenthCard)
        val player3TenthCardNumber: TextView = findViewById(R.id.player3TenthCardNumber)
        val player3TenthCardColor: ImageView = findViewById(R.id.player3TenthCardColor)

        val player3Colors = ArrayList<ImageView>()
        player3Colors.add(player3firstCardColor)
        player3Colors.add(player3secondCardColor)
        player3Colors.add(player3thirdCardColor)
        player3Colors.add(player3forthCardColor)
        player3Colors.add(player3fifthCardColor)
        player3Colors.add(player3SixthCardColor)
        player3Colors.add(player3SeventhCardColor)
        player3Colors.add(player3EightCardColor)
        player3Colors.add(player3NinthCardColor)
        player3Colors.add(player3TenthCardColor)

        val player3Numbers = ArrayList<TextView>()
        player3Numbers.add(player3firstCardNumber)
        player3Numbers.add(player3secondCardNumber)
        player3Numbers.add(player3thirdCardNumber)
        player3Numbers.add(player3forthCardNumber)
        player3Numbers.add(player3fifthCardNumber)
        player3Numbers.add(player3SixthCardNumber)
        player3Numbers.add(player3SeventhCardNumber)
        player3Numbers.add(player3EightCardNumber)
        player3Numbers.add(player3NinthCardNumber)
        player3Numbers.add(player3TenthCardNumber)
        val dealerFirstCard : ConstraintLayout = findViewById(R.id.dealerFirstCard)
        val dealerFirstCardNumber: TextView = findViewById(R.id.dealerFirstCardNumber)
        val dealerFirstCardColor: ImageView = findViewById(R.id.dealerFirstCardColor)
        val dealerSecondCard : ConstraintLayout = findViewById(R.id.dealerSecondCard)
        val dealerSecondCardNumber: TextView = findViewById(R.id.dealerSecondCardNumber)
        val dealerSecondCardColor: ImageView = findViewById(R.id.dealerSecondCardColor)
        val dealerThirdCard : ConstraintLayout = findViewById(R.id.dealerThirdCard)
        val dealerThirdCardNumber: TextView = findViewById(R.id.dealerThirdCardNumber)
        val dealerThirdCardColor: ImageView = findViewById(R.id.dealerThirdCardColor)
        val dealerForthCard : ConstraintLayout = findViewById(R.id.dealerForthCard)
        val dealerForthCardNumber: TextView = findViewById(R.id.dealerForthCardNumber)
        val dealerForthCardColor: ImageView = findViewById(R.id.dealerForthCardColor)
        val dealerFifthCard : ConstraintLayout = findViewById(R.id.dealerFifthCard)
        val dealerFifthCardNumber: TextView = findViewById(R.id.dealerFifthCardNumber)
        val dealerFifthCardColor: ImageView = findViewById(R.id.dealerFifthCardColor)
        val dealerCards = ArrayList<ConstraintLayout>()
        dealerCards.add(dealerFirstCard)
        dealerCards.add(dealerSecondCard)
        dealerCards.add(dealerThirdCard)
        dealerCards.add(dealerForthCard)
        dealerCards.add(dealerFifthCard)

        val dealerColors = ArrayList<ImageView>()
        dealerColors.add(dealerFirstCardColor)
        dealerColors.add(dealerSecondCardColor)
        dealerColors.add(dealerThirdCardColor)
        dealerColors.add(dealerForthCardColor)
        dealerColors.add(dealerFifthCardColor)

        val dealerNumbers = ArrayList<TextView>()
        dealerNumbers.add(dealerFirstCardNumber)
        dealerNumbers.add(dealerSecondCardNumber)
        dealerNumbers.add(dealerThirdCardNumber)
        dealerNumbers.add(dealerForthCardNumber)
        dealerNumbers.add(dealerFifthCardNumber)

        val player3Cards = ArrayList<ConstraintLayout>()
        player3Cards.add(player3firstCard)
        player3Cards.add(player3secondCard)
        player3Cards.add(player3thirdCard)
        player3Cards.add(player3forthCard)
        player3Cards.add(player3fifthCard)
        player3Cards.add(player3SixthCard)
        player3Cards.add(player3SeventhCard)
        player3Cards.add(player3EightCard)
        player3Cards.add(player3NinthCard)
        player3Cards.add(player3TenthCard)
        for(i in player1Cards.indices){
            player1Cards[i].isVisible = false
            player2Cards[i].isVisible = false
            player3Cards[i].isVisible = false
        }
        for(i in dealerCards.indices){
            dealerCards[i].isVisible = false
        }

        val myBalance : TextView = findViewById(R.id.myBalance)
        myBalance.text = "${(game.players[0].balance/1000).toInt().toString()}k"
        for(i in game.players[0].hand.indices){
            if(i<=game.players[0].hand.size-1){
                player1Cards[i].isVisible = true
                player1Numbers[i].text = game.players[0].hand[i].number
                setColor( player1Colors[i], game.players[0].hand[i].color)
            }
        }
        if(game.players.size>=2){
            for(i in game.players[1].hand.indices){
                if(i<=game.players[1].hand.size-1){
                    player2Cards[i].isVisible = true
                    player2Numbers[i].text = game.players[1].hand[i].number
                    setColor( player2Colors[i], game.players[1].hand[i].color)
                }
            }
        }
        if(game.players.size == 3){
            for(i in game.players[2].hand.indices){
                if(i<=game.players[2].hand.size-1){
                    player3Cards[i].isVisible = true
                    player3Numbers[i].text = game.players[2].hand[i].number
                    setColor( player3Colors[i], game.players[2].hand[i].color)
                }
            }
        }
        val dealerCardBack : ImageView = findViewById(R.id.dealerSecondCardBackColor)
        if(game.dealer.hand.size==2 && game.currentTurn!=game.dealer){
            dealerCards[0].isVisible = true
            dealerNumbers[0].text = game.dealer.hand[0].number
            setColor(dealerColors[0], game.dealer.hand[0].color)
            dealerCards[1].isVisible = true
            dealerCards[1].background = resources.getDrawable(R.drawable.cardback_layout)
            dealerColors[1].isVisible = false
            dealerNumbers[1].isVisible = false
            dealerCardBack.isVisible = true

        }
        if(game.dealer.hand.size==2 && game.currentTurn==game.dealer)
        {
            dealerCards[1].background = resources.getDrawable(R.drawable.card_layout)
            dealerCardBack.isVisible = false
            dealerNumbers[1].isVisible = true
            dealerColors[1].isVisible = true
            for(i in game.dealer.hand.indices){
                dealerCards[i].isVisible
                dealerNumbers[i].text = game.dealer.hand[i].number
                setColor(dealerColors[i], game.dealer.hand[i].color)
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
package com.example.casino

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

val user = userId
class PlayFragment : Fragment() {
    public var userId = ""
    public var chipsBalance = ""
    public var saphireBalance = ""
    public val TEXT = "myUser"
    public val CHIPS_TEXT = "chipsBalance"
    public val SAPHIRES_TEXT = "saphireBalance"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        getSharedPrefs()
        val pokerLayout : ConstraintLayout = view.findViewById(R.id.pokerLayout)
        val slotsLayout : ConstraintLayout = view.findViewById(R.id.slotsLayout)
        val blackJackLayout : ConstraintLayout = view.findViewById(R.id.blackjackLayout)
        val addChipsBtn : CardView = view.findViewById(R.id.addChips)
        val collectBonusBtn : ImageView = view.findViewById(R.id.collectBonusBtn)
        collectBonusBtn.setOnClickListener{
            findNavController().navigate(R.id.action_playFragment_to_shopFragment)
        }
        addChipsBtn.setOnClickListener{
            findNavController().navigate(R.id.action_playFragment_to_shopFragment)
        }
        val addSaphiresBtn : CardView = view.findViewById(R.id.addSaphire)
        addSaphiresBtn.setOnClickListener{
            findNavController().navigate(R.id.action_playFragment_to_shopFragment)
        }
        pokerLayout.setOnClickListener{
            val intent : Intent = Intent(activity, PokerActivity::class.java)
            startActivity(intent)
        }
        blackJackLayout.setOnClickListener{
            val intent : Intent = Intent(activity, BlackJackActivity::class.java)
            startActivity(intent)
        }
        slotsLayout.setOnClickListener{
            val intent : Intent = Intent ( activity, SlotMachineActivity::class.java)
            startActivity(intent)
        }
        val myChipsBalance : TextView = view.findViewById(R.id.chipsNumber)
        val mySaphireBalance : TextView = view.findViewById(R.id.saphiresCount)
        myChipsBalance.text = chipsBalance
        mySaphireBalance.text = saphireBalance

        return view
    }

    private fun getSharedPrefs(){
        val sharedPref = this.requireActivity().getSharedPreferences("LoggedIn", AppCompatActivity.MODE_PRIVATE)
        userId = sharedPref.getString(TEXT, "").toString()
        chipsBalance = sharedPref.getString(CHIPS_TEXT, "").toString()
        saphireBalance = sharedPref.getString(SAPHIRES_TEXT, "").toString()
        println(chipsBalance)
        println(saphireBalance)

    }
}
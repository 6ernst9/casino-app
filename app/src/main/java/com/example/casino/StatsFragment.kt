package com.example.casino

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StatsFragment : Fragment() {
    public var userId = ""
    public var chipsBalance = ""
    public var saphireBalance = ""
    public var myLevel = ""
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
        val view = inflater.inflate(R.layout.fragment_stats, container, false)
        getSharedPrefs()
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


    }

}
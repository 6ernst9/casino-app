package com.example.casino

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ShopFragment : Fragment() {
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

        val view =  inflater.inflate(R.layout.fragment_shop, container, false)
        getSharedPrefs()
        val buyEightySaphires : TextView = view.findViewById(R.id.eightSaphiresBuy)
        val buyHundredSaphires : TextView = view.findViewById(R.id.hundredSaphiresBuy)
        val buyThousandSaphires : TextView = view.findViewById(R.id.thousandSaphiresBuy)
        val buyTenThousandSaphires : TextView = view.findViewById(R.id.tenThousandSaphiresBuy)
        val buySixThousandSaphires : TextView = view.findViewById(R.id.sixThousandSaphiresBuy)
        val buyHundredChips : TextView = view.findViewById(R.id.hundredChipsBuy)
        val buyMilionChips : TextView = view.findViewById(R.id.milionChipsBuy)
        val buyTenMilionChips : TextView = view.findViewById(R.id.tenMilionChipsBuy)
        val myChipsBalance : TextView = view.findViewById(R.id.chipsNumber)
        val mySaphireBalance : TextView = view.findViewById(R.id.saphiresCount)
        myChipsBalance.text = chipsBalance
        mySaphireBalance.text = saphireBalance
        buyHundredChips.setOnClickListener{
            buyDialog(100000,  60.0, "Chips")
        }
        buyMilionChips.setOnClickListener{
            buyDialog(1000000,  500.0, "Chips")
        }
        buyTenMilionChips.setOnClickListener{
            buyDialog(10000000,  4500.0, "Chips")
        }
        buyEightySaphires.setOnClickListener{
            buyDialog(80, 4.99, "Saphires")
        }
        buyHundredSaphires.setOnClickListener{
            buyDialog(500, 24.99, "Saphires")
        }
        buyThousandSaphires.setOnClickListener{
            buyDialog(2000, 49.99,"Saphires")
        }
        buyTenThousandSaphires.setOnClickListener{
            buyDialog(10000, 99.99,"Saphires")
        }
        buySixThousandSaphires.setOnClickListener{
            buyDialog(6000, 249.99,"Saphires")

        }
        return view
    }
    private fun buyDialog(buyQuantity: Long, buyValue: Double, buyCategory : String){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.buy_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        val buyBtn : AppCompatButton =  dialog.findViewById(R.id.buyBtn)
        val cancelBtn : AppCompatButton = dialog.findViewById(R.id.cancelBtn)
        val buyTitle : TextView = dialog.findViewById(R.id.buyTitle)
        buyTitle.text = "Buy ${buyQuantity.toString()} $buyCategory"
        buyBtn.setOnClickListener{
            if(buyCategory == "Chips"){
                chipsBalance = (chipsBalance.toLong().toInt() + buyQuantity).toString()
                saphireBalance = (saphireBalance.toDouble().toInt() - buyValue.toInt()).toString()
                addSharedPrefs(chipsBalance, saphireBalance)
                Firebase.database.reference.child("users").child(userId).child("chipsBalance").setValue(chipsBalance)
                getSharedPrefs()
                dialog.cancel()
            }
            if(buyCategory == "Saphires"){
                transactionDialog(buyQuantity, buyValue)
                dialog.cancel()
            }
        }
        cancelBtn.setOnClickListener{
            dialog.cancel()
        }
        dialog.show()
    }
    private  fun transactionDialog(buyQuantity: Long, buyValue: Double) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.transaction_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        val buyBtn : AppCompatButton =  dialog.findViewById(R.id.buyBtn)
        val cancelBtn : AppCompatButton = dialog.findViewById(R.id.cancelBtn)
        val buyTitle : TextView = dialog.findViewById(R.id.buyTitle)
        val cardNumberForm : EditText = dialog.findViewById(R.id.cardNumberForm)
        val cvvNumberForm : EditText = dialog.findViewById(R.id.cvvNumberForm)
        val expDateForm : EditText = dialog.findViewById(R.id.expDateForm)
        val nameForm : EditText = dialog.findViewById(R.id.nameForm)
        buyTitle.text = "Sumary -$buyValue Ron"
        val statusText = dialog.findViewById<TextView>(R.id.statusText)
        buyBtn.setOnClickListener {
            val cardNumber = cardNumberForm.text
            val cvvNumber = cvvNumberForm.text
            val expNumber = expDateForm.text
            val name = nameForm.text
            if(cardNumber.length != 16){
                statusText.isVisible = true
                statusText.text = "Invalid Card Number"
            }
            if(cvvNumber.length!=3){
                statusText.isVisible = true
                statusText.text = "Invalid CVV Number"
            }
            if(!("/" in expNumber)){
                statusText.isVisible = true
                statusText.text = "Invalid Date"
            }
            if(name.toString()=="" || name.toString() == " " ||name.length <4){
                statusText.isVisible = true
                statusText.text = "Invalid Name"
            }
            if(cardNumber.length == 16 && cvvNumber.length==3 && ("/" in expNumber) && name.toString()!="" && name.toString() != " " && name.length >=4){
                statusText.isVisible = true
                statusText.text = "Loading..."
                Handler().postDelayed({
                    saphireBalance = (saphireBalance.toDouble().toInt() + buyQuantity.toInt()).toString()
                    addSharedPrefs(chipsBalance, saphireBalance)
                    Firebase.database.reference.child("users").child(userId).child("saphireBalance").setValue(saphireBalance)
                    getSharedPrefs()
                    dialog.cancel()
                }, 3000)
        }
        }
        cancelBtn.setOnClickListener{
            dialog.cancel()
        }
        dialog.show()
    }

    private fun getSharedPrefs(){
        val sharedPref = this.requireActivity().getSharedPreferences("LoggedIn", AppCompatActivity.MODE_PRIVATE)
        userId = sharedPref.getString(TEXT, "").toString()
        chipsBalance = sharedPref.getString(CHIPS_TEXT, "").toString()
        saphireBalance = sharedPref.getString(SAPHIRES_TEXT, "").toString()

    }
    private fun addSharedPrefs(chipsBalance : String, saphireBalance : String) {
        val sharedPref = this.requireActivity().getSharedPreferences("LoggedIn", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(CHIPS_TEXT,chipsBalance)
        editor.putString(SAPHIRES_TEXT,saphireBalance)
        editor.apply()
    }

}
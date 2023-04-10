package com.example.casino

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView

class AccountFragment : Fragment() {
    public val BOOLEAN = "isLoggedIn"
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
        val view =inflater.inflate(R.layout.fragment_account, container, false)
        val logOutImg : ImageView = view.findViewById(R.id.logOutArrow)
        logOutImg.setOnClickListener{
            logOutDialog()
        }
        return view
    }

    private fun logOutDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.logout_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        val logOutBtn : AppCompatButton =  dialog.findViewById(R.id.logOutBtn)
        val cancelBtn : AppCompatButton = dialog.findViewById(R.id.cancelBtn)
        cancelBtn.setOnClickListener{
            dialog.cancel()
        }
        logOutBtn.setOnClickListener{
            Handler().postDelayed({
                val sharedPref = this.requireActivity().getSharedPreferences("LoggedIn", AppCompatActivity.MODE_PRIVATE)
                sharedPref.edit().remove(TEXT).commit()
                sharedPref.edit().remove(CHIPS_TEXT).commit()
                sharedPref.edit().remove(SAPHIRES_TEXT).commit()
                sharedPref.edit().putBoolean(BOOLEAN, false)
                val intent = Intent(activity, LogInActivity::class.java)
                startActivity(intent)
            }, 1000)

        }
        dialog.show()
    }

}
package com.example.casino

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Telephony.Mms.Part.TEXT
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import java.sql.Types.BOOLEAN

class SignUpActivity : AppCompatActivity() {

    public val TEXT = "myUser"
    public val BOOLEAN = "isLoggedIn"
    public val CHIPS_TEXT = "chipsBalance"
    public val SAPHIRES_TEXT = "saphireBalance"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val signInBtn : TextView = findViewById(R.id.signInBtn)
        signInBtn.setOnClickListener{
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.from_left, R.anim.to_right)
        }
        val emailForm : EditText = findViewById(R.id.emailForm)
        val nameForm : EditText = findViewById(R.id.nameForm)
        val passForm : EditText = findViewById(R.id.passForm)
        val enterBtn : AppCompatButton = findViewById(R.id.enterBtn)
        enterBtn.setOnClickListener{
            val email = emailForm.text.toString()
            val pass = passForm.text.toString()
            val name = nameForm.text.toString()
            if( email == "" )
            {
                emptyDialog("Email", "empty")
            }
            if(pass == ""){
                emptyDialog("Password", "empty")
            }
            if(name == ""){
                emptyDialog("Name", "empty")
            }
            if(email!="" && email!=" " && name!="" && name!=" " && pass!=""  && pass!=" "){
                email.trim{ it <=' '}
                name.trim{ it <= ' '}
                pass.trim{ it <= ' '}
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(
                    OnCompleteListener<AuthResult> {
                        if(it.isSuccessful){
                            val firebaseUser : FirebaseUser = it.result!!.user!!
                            Handler().postDelayed({
                                val intent = Intent(this, ScreenActivity::class.java)
                                val newAccountClass = Account(name, email, pass, "1000000", "80", "0", "")
                                FirebaseDatabase.getInstance().reference.child("users").child(firebaseUser.uid).setValue(newAccountClass)
                                addSharedPrefs(true, firebaseUser.uid, "1000000", "80")
                                intent.putExtra("user_id", firebaseUser.uid)
                                startActivity(intent)
                                overridePendingTransition(R.anim.from_right,  R.anim.to_left)
                                this.finish()
                            }, 2000)
                        }
                        else{
                            emptyDialog("Email", it.exception!!.message.toString())
                        }
                    }
                )
            }
        }
    }

    private fun addSharedPrefs(isLoggedIn: Boolean, myUser: String, chipsBalance : String, saphireBalance : String) {
        val sharedPref = getSharedPreferences("LoggedIn", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(TEXT,myUser)
        editor.putBoolean(BOOLEAN, isLoggedIn)
        editor.putString(CHIPS_TEXT,chipsBalance)
        editor.putString(SAPHIRES_TEXT,saphireBalance)
        editor.apply()
    }

    fun emptyDialog(emptyType : String, exeption : String){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.wrongemail_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        val retryBtn :AppCompatButton =  dialog.findViewById(R.id.retryBtn)
        val emptyTitle : TextView = dialog.findViewById(R.id.emptyTitle)
        val emptyDes : TextView = dialog.findViewById(R.id.emptyDes)
        if(exeption!="empty"){
            emptyDes.text = exeption
            emptyTitle.text = "Invalid $emptyType"
        }
        else{
            emptyTitle.text = "Empty $emptyType"
        }
        val emailForm : EditText = findViewById(R.id.emailForm)
        val nameForm : EditText = findViewById(R.id.nameForm)
        val passForm : EditText = findViewById(R.id.passForm)

        retryBtn.setOnClickListener{
            dialog.cancel()
            emailForm.text.clear()
            passForm.text.clear()
            nameForm.text.clear()
        }
        dialog.show()
    }

    override fun onBackPressed() {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.from_left, R.anim.to_right)
    }
}
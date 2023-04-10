package com.example.casino

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class LogInActivity : AppCompatActivity() {
    public val TEXT = "myUser"
    public val BOOLEAN = "isLoggedIn"
    public val CHIPS_TEXT = "chipsBalance"
    public val SAPHIRES_TEXT = "saphireBalance"
    public var loggerVerifier = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        val signUpBtn = findViewById<TextView>(R.id.signUpBtn)
        signUpBtn.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.from_right, R.anim.to_left)
        }
        val emailForm : EditText = findViewById(R.id.emailForm)
        val passForm : EditText = findViewById(R.id.passForm)
        val enterBtn : AppCompatButton = findViewById(R.id.enterBtn)
        enterBtn.setOnClickListener{
            val email = emailForm.text.toString()
            val pass = passForm.text.toString()
            if( email == "" )
            {
                emptyDialog("Email", "empty")
            }
            if(pass == ""){
                emptyDialog("Password", "empty")
            }
            if(email!="" && email!="" && pass!=""  && pass!=" "){
                email.trim{ it <=' '}
                pass.trim{ it <= ' '}
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(
                    OnCompleteListener<AuthResult> {
                        if(it.isSuccessful){
                            Handler().postDelayed({
                                val intent = Intent(this, ScreenActivity::class.java)
                                Firebase.database.reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("chipsBalance").get().addOnSuccessListener { snapshot ->
                                    Firebase.database.reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("saphireBalance").get().addOnSuccessListener { snapshot2->
                                        println(snapshot.value.toString())
                                        println(snapshot2.value.toString())
                                        addSharedPrefs(true, FirebaseAuth.getInstance().currentUser!!.uid, snapshot.value.toString(), snapshot2.value.toString())

                                    }
                                }



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
    fun emptyDialog(emptyType : String, exeption : String){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.wrongemail_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        val retryBtn :AppCompatButton =  dialog.findViewById(R.id.retryBtn)
        val emptyTitle : TextView = dialog.findViewById(R.id.emptyTitle)
        val emptyDes : TextView = dialog.findViewById(R.id.emptyDes)
        if(exeption!="empty"){
            emptyDes.text = "User not found. Please try again later"
            emptyTitle.text = "Invalid User"
        }
        else{
            emptyTitle.text = "Empty $emptyType"
        }
        val emailForm : EditText = findViewById(R.id.emailForm)
        val passForm : EditText = findViewById(R.id.passForm)

        retryBtn.setOnClickListener{
            dialog.cancel()
            emailForm.text.clear()
            passForm.text.clear()
        }
        dialog.show()
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


    override fun onBackPressed() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }
}
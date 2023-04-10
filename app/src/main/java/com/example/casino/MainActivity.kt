package com.example.casino

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    public val BOOLEAN = "isLoggedIn"
    public var loggerVerifier = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSharedPrefs()
        var duration : Long = TimeUnit.MINUTES.toMillis(2)
        var currentProgress : Int = 0
        val progressBar = findViewById<ProgressBar>(R.id.customProgressbar)
        val welcomeTitle : TextView = findViewById(R.id.welcomeTitle)
        val colorTheme = welcomeTitle.currentTextColor
        val welcomeImg : ImageView = findViewById(R.id.welcomeImg)
        val createdByImg : ImageView = findViewById(R.id.createdByLogo)
        if(colorTheme== resources.getColor(R.color.black))
        {
            welcomeImg.setImageDrawable(resources.getDrawable(R.drawable.casinowall))
            createdByImg.setImageDrawable(resources.getDrawable(R.drawable.logoblack))
        }
        else{
            welcomeImg.setImageDrawable(resources.getDrawable(R.drawable.casinowall_light))
            createdByImg.setImageDrawable(resources.getDrawable(R.drawable.logowhite))
        }

        val timer = Timer()
        val percentageText : TextView =findViewById(R.id.percentageText)
        var counterPercentage = 0
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                currentProgress++
                progressBar.progress = currentProgress
                this@MainActivity.runOnUiThread( java.lang.Runnable(){
                    if(currentProgress%3==0){
                        percentageText.text = "$currentProgress%"
                    }

                })

                if (currentProgress == 100){
                    timer.cancel()
                    if(loggerVerifier){
                        val intent = Intent(this@MainActivity, ScreenActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.from_right, R.anim.to_left)
                        finish()
                    }
                    else{
                        val intent = Intent(this@MainActivity, LogInActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.from_right, R.anim.to_left)
                        finish()
                    }
                    finish()
                }
            }
        }


        timer.schedule(timerTask, 0, 20)


    }
    private fun getSharedPrefs(){
        val sharedPref = getSharedPreferences("LoggedIn", MODE_PRIVATE)
        loggerVerifier = sharedPref.getBoolean(BOOLEAN, false)
    }

}
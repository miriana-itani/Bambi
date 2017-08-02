package com.bambi

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var goNextAct: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goNextAct = Intent(applicationContext, MainPage::class.java)
        startActivity(goNextAct)
        finish()
    }
}

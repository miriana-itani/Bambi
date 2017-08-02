package com.bambi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bambi.local.Prefs

class MainActivity : AppCompatActivity() {
    private var goNextAct: Intent?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Prefs.getInstance(applicationContext).login) {
            goNextAct = Intent(applicationContext, RegisterActivity::class.java)
        } else goNextAct = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(goNextAct)
        finish()
    }
}

package com.bambi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Miriana on 7/20/2017.
 */

class MainPage : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)
        showProgress()
    }
}

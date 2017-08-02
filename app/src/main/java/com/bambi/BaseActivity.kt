package com.bambi

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

abstract class BaseActivity : AppCompatActivity() {
    private var pd: ProgressDialog? = null
    fun showError(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }

    fun showProgress() {
        if (pd != null) {
            if (pd!!.isShowing) return
        }
        pd = ProgressDialog(this)
        pd!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pd!!.setMessage(getString(R.string.please_wait))
        pd!!.setCancelable(false)
        pd!!.show()
    }

    fun hideProgress() {
        if (pd != null)
            pd!!.dismiss()
    }
}

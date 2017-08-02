package com.bambi

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast

abstract class BaseActivity : AppCompatActivity() {
    private var pd: ProgressDialog? = null
    private var toast: Toast? = null;
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

    fun handleError(message: String?) {
        var message = message
        hideProgress()
        if (!TextUtils.isEmpty(message) && message != getString(R.string.internet_error))
            message = getString(R.string.error)
        showToast(message)
    }

    fun showToast(message: String?) {
        if (TextUtils.isEmpty(message)) {
            if (toast == null)
                toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
            else
                toast!!.setText(message)
            toast!!.show()
        }
    }
}

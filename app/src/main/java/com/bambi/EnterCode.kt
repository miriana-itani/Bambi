package com.bambi

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.bambi.local.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class EnterCode : BaseActivity() {
    private var auth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_code)
        auth = FirebaseAuth.getInstance()
        val code = findViewById(R.id.code) as EditText
        val verifyCode = findViewById(R.id.verify_code) as TextView
        val verificationID = intent.getStringExtra("verificationID")
        val phoneString = intent.getStringExtra("phoneNumber")
        verifyCode.setOnClickListener {
            val codeString: String = code.text.toString().trim()
            if (!codeString.isEmpty()) {
                showProgress()
                verifyCodeWithCode(verificationID,codeString)
            } else showError(getString(R.string.error_code))
        }
    }

    private fun verifyCodeWithCode(verificationID: String, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationID, code)
        signInWithCredentials(credential)
    }

    private fun signInWithCredentials(credentials: PhoneAuthCredential) {
        auth!!.signInWithCredential(credentials)
                .addOnCompleteListener(this, { task ->
                    hideProgress()
                    if (task.isSuccessful) {
                        val user = task.result.user
                        //send phone number to backend
                        Prefs.getInstance(applicationContext).login = true
                    } else {
                        showError(getString(R.string.please_try_again))
                    }
                })
    }
}

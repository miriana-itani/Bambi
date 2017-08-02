package com.bambi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

/**
 * Created by Miriana on 7/20/2017.
 */

public class RegisterActivity extends BaseActivity {
    private String code = "+961";
    private String phoneNumber;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final CountryCodePicker countryCodePicker = (CountryCodePicker) findViewById(R.id.ccp);
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                code = countryCodePicker.getSelectedCountryCode();
            }
        });
        final TextView number = (TextView) findViewById(R.id.phone);
        TextView letsGo = (TextView) findViewById(R.id.go);
        callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
//                signInWithPhoneAuthCredential(credential);
                hideProgress();
                startActivity(new Intent(getApplicationContext(), MainPage.class));
                finish();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                hideProgress();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                } else if (e instanceof FirebaseTooManyRequestsException) {

                }
                showError("Error");
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                hideProgress();
                Intent intent = new Intent(getApplicationContext(),EnterCode.class);
                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("verificationID",verificationId);
                startActivity(intent);
                finish();
            }
        };
        letsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = number.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNumber)) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            ("+"+code + phoneNumber),        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            RegisterActivity.this,               // Activity (for callback binding)
                            callback);
                    showProgress();
                } else showError(getString(R.string.phone_number_error));
            }
        });
    }
}

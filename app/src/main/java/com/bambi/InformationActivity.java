package com.bambi;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Miriana on 8/2/2017.
 */

public class InformationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        TextView location = (TextView) findViewById(R.id.location);
        TextView call = (TextView) findViewById(R.id.call);
        TextView email = (TextView) findViewById(R.id.email);
        TextView website = (TextView) findViewById(R.id.website);
        location.setText(getString(R.string.location)+"Haboush Main Road ,Plaza Center\n" +
                "Sour Al Thakana Main road , Karaz Centre");
        call.setText(getString(R.string.call)+"+9613921222");
        email.setText(getString(R.string.email)+"info@bambimobilya.com.lb");
        website.setText(getString(R.string.website)+"https://bambimobilya.com.lb");
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://bambimobilya.com.lb";
                try {
                    Intent i = new Intent("android.intent.action.MAIN");
                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                    i.addCategory("android.intent.category.LAUNCHER");
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                catch(ActivityNotFoundException e) {
                    // Chrome is not installed
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }
            }
        });

    }
}

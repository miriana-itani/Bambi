package com.bambi;

import android.os.Bundle;

/**
 * Created by USER on 9/6/2017.
 */

public class ItemResult extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_result);
        showProgress();

    }
}

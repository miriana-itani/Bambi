package com.bambi.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Mimi on 3/3/2016.
 * Singletone class
 */
public class Singletone {
    private static Singletone mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private Singletone(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized Singletone getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Singletone(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}

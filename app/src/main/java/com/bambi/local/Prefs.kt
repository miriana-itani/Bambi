package com.bambi.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by Miriana on 7/20/2017.
 */

class Prefs {

    fun clear() {
        prefs!!.edit().clear().apply()
    }

    var login: Boolean
        get() = prefs!!.getBoolean("login", true)
        set(isLogin) {
            prefs!!.edit().putBoolean("login", isLogin).apply()
        }

    companion object {
        protected var INSTANCE: Prefs? = null
        private var prefs: SharedPreferences? = null

        fun getInstance(context: Context): Prefs {
            if (INSTANCE == null) {
                INSTANCE = Prefs()
                prefs = PreferenceManager.getDefaultSharedPreferences(context)
            }

            return INSTANCE as Prefs
        }
    }
}

package com.app.coremodule.data.local.sharedpref

import android.content.SharedPreferences

class SharedPref(private val sharedPreferences: SharedPreferences) : ISharedPref {
    override fun getLocale(): String? {
        return sharedPreferences.getString("language", "en")
    }

}
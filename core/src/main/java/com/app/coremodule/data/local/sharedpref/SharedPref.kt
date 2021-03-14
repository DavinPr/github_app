package com.app.coremodule.data.local.sharedpref

import android.content.SharedPreferences

class SharedPref(private val sharedPreferences: SharedPreferences) : ISharedPref {
    private val fragmentTag = "fragment_tag"
    private val detailFragmentTag = "detail_fragment_tag"
    override fun putFragmentTag(tag: String) {
        val editor = sharedPreferences.edit()
        editor.putString(fragmentTag, tag)
        editor.apply()
    }

    override fun getFragmentTag(): String? =
        sharedPreferences.getString(fragmentTag, null)

    override fun putDetailFragmentTag(tag: String) {
        val editor = sharedPreferences.edit()
        editor.putString(detailFragmentTag, tag)
        editor.apply()
    }

    override fun getDetailFragmentTag(): String? =
        sharedPreferences.getString(detailFragmentTag, null)

}
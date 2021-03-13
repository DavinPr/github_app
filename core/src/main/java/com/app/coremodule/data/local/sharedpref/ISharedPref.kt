package com.app.coremodule.data.local.sharedpref

interface ISharedPref {
    fun putFragmentTag(tag: String)
    fun getFragmentTag() : String?
}
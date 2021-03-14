package com.app.coremodule.data.local.sharedpref

interface ISharedPref {
    fun putFragmentTag(tag: String)
    fun getFragmentTag() : String?
    fun putDetailFragmentTag(tag: String)
    fun getDetailFragmentTag() : String?
}
package com.chaudhry.chaudharyagencykotlin.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor

    init {
        editor = sharedPreferences.edit()
    }
    companion object {
        private var sharedPrefs: SharedPrefs? = null
        fun getInstance(context: Context?): SharedPrefs? {
            if (sharedPrefs == null) {
                sharedPrefs = context?.let { SharedPrefs(it) }
            }
            return sharedPrefs
        }
    }


    fun removePreference() {
        editor.clear().apply()
    }

    var uSerId: String?
        get() = sharedPreferences.getString("token", "")
        set(value) {
            editor.putString("token",value).apply()
        }
}
package com.skripsi.sawitku.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    companion object {
        private const val PREFS_NAME = "MyPrefs"
        private const val IS_REGISTERED = "is_registered"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false) // false is the default value if the key is not found
    }

    // You can add more functions here if needed
}

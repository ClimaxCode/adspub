package com.climax.code.applanguages

import android.content.Context
import android.content.SharedPreferences

class PrefStorage(context: Context) {


    private var appLanguageSelect = "app_lang_select_up"

    private val preferences: SharedPreferences = context.getSharedPreferences(
        Context.MODE_PRIVATE.toString(),
        Context.MODE_PRIVATE)


    var intAppLangApplied: Int
        get() = preferences.getInt(appLanguageSelect, -1)
        set(value) = preferences.edit().putInt(appLanguageSelect, value).apply()


}
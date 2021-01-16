package com.mayank_amr.spotifysdk.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/**
 * @Project spotify SDK
 * @Created_by Mayank Kumar on 13-12-2020 05:03 PM
 */

private const val KEY_EXPIRE_TIME = "key_expire_time"
private const val KEY_ACCESS_TOKEN = "key_access_token"

class PreferenceProvider(
    context: Context
) {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext);


    fun saveTokenExpireTime(savedAt: String) {
        preferences.edit().putString(KEY_EXPIRE_TIME, savedAt)
            .apply()
    }

    fun getTokenExpireTime(): String? {
        return preferences.getString(KEY_EXPIRE_TIME, null)
    }


    fun saveUserAccessToken(tokenType: String, token: String) {
        preferences.edit().putString(KEY_ACCESS_TOKEN, "$tokenType $token")
            .apply()
    }

    fun getUserAccessToken() = preferences.getString(KEY_ACCESS_TOKEN, null)

}
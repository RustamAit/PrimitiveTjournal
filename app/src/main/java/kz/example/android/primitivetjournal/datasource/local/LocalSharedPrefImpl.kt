package kz.example.android.primitivetjournal.datasource.local

import android.content.SharedPreferences
import kz.example.android.primitivetjournal.core.Constants.ACCESS_TOKEN

class LocalSharedPrefImpl(private val pref: SharedPreferences): LocalSharedPref{

    override fun setAccessToken(token: String) {
        pref.edit().putString(ACCESS_TOKEN,token).apply()
    }

    override fun getAccessToken(): String? {
        return pref.getString(ACCESS_TOKEN, null)
    }

    override fun clearAccessToken() {
        pref.edit().remove(ACCESS_TOKEN).apply()
    }


}
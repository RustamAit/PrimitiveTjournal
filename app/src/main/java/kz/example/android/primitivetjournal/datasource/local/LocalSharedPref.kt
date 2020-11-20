package kz.example.android.primitivetjournal.datasource.local

interface LocalSharedPref {

    fun setAccessToken(token: String)
    fun getAccessToken(): String?
    fun clearAccessToken()

}
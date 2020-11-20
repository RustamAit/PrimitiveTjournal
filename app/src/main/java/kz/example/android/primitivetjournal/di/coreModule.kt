package kz.example.android.primitivetjournal.di

import android.content.Context
import android.content.SharedPreferences
import kz.example.android.primitivetjournal.core.Constants
import kz.example.android.primitivetjournal.datasource.local.LocalSharedPref
import kz.example.android.primitivetjournal.datasource.local.LocalSharedPrefImpl
import kz.example.android.primitivetjournal.datasource.remote.createOkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {

    single {
        createSharedPreferences(androidContext())
    }

    single {
        LocalSharedPrefImpl(get()) as LocalSharedPref
    }

    single { createOkHttpClient(androidContext(), get()) }

}


fun createSharedPreferences(context: Context) : SharedPreferences {
    return context.applicationContext.getSharedPreferences(Constants.preference, Context.MODE_PRIVATE)
}

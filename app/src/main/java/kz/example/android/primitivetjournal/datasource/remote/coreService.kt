package kz.example.android.primitivetjournal.datasource.remote

import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.view.WindowManager
import kz.example.android.primitivetjournal.core.Constants
import kz.example.android.primitivetjournal.core.utills.Logger
import kz.example.android.primitivetjournal.datasource.local.LocalSharedPref
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createOkHttpClient(context: Context, sharedPref: LocalSharedPref): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Logger.api(message) })
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .connectTimeout(180L, TimeUnit.SECONDS)
        .readTimeout(180L, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("User-agent",
                    "tjournal-app/${Constants.API_VERSION} (${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}; " +
                            "Android/${android.os.Build.VERSION.SDK_INT}; " +
                            "${getScreenResolution(context)})").build()
            return@addInterceptor chain.proceed(request)
        }
        .addInterceptor {chain ->
            if(!sharedPref.getAccessToken().isNullOrEmpty()){
                val request = chain.request().newBuilder()
                    .addHeader("X-Device-Token", sharedPref.getAccessToken()!!)
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            return@addInterceptor chain.proceed(chain.request())
        }
        .build()
}

fun getScreenResolution(context: Context): String? {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    val width = metrics.widthPixels
    val height = metrics.heightPixels
    return "${height}x$width"
}


inline fun <reified T> createService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync()).build()
    return retrofit.create(T::class.java)
}

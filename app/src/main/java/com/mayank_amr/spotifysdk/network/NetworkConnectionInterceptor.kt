package com.mayank_amr.spotifysdk.network

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @Project FriendsFeed
 * @Created_by Mayank Kumar on 17-09-2020 03:20 PM
 */
class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable()) {
            Log.e(TAG, "intercept: Make sure you have active Internet connection ")
            throw NoInternetException("Make sure you have active Internet connection")
        }
        // throw NoInternetException("Make sure you have active Internet connection")
        return chain.proceed(chain.request())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkAvailable() =
        (applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }

}
package com.mayank_amr.spotifysdk.network

import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.Gson
import org.json.JSONException
import retrofit2.Response


/**
 * @Project FriendsFeed
 * @Created_by Mayank Kumar on 14-09-2020 10:47 AM
 */
abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        Log.d(TAG, "apiRequest: creating request..........")

        val response = call.invoke()
        Log.e(TAG, "apiRequest: completed.......... Code : " + response.code() + "\n")
        Log.e(TAG, "apiRequest: completed.......... error body : " + response.errorBody() + "\n")
        Log.e(TAG, "apiRequest: completed.......... message : " + response.message() + "\n")
        Log.e(TAG, "apiRequest: completed.......... raw : " + response.raw().header("Authorization") + "\n")




        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.toString()
            // error response is in JSON Object that is
            // Response{protocol=http/1.1, code=404, message=Not Found, url=https://friendsfeed.herokuapp.com.../../..}

            val message = StringBuilder()
            error?.let {
                try {
                    // if (response.code() == 404)
                    message.append(" ${response.code()}")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                message.append("\n")
            }
            message.append(" ${response.message()} ")

            throw ApiException(message.toString())
        }
    }
}
package com.mayank_amr.spotifysdk.network


import com.mayank_amr.spotifysdk.network.responces.searchresponce.SearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


/**
 * @Project Sample Chat
 * @Created_by Mayank Kumar on 08-09-2020 08:26 AM
 */

interface SongApi {

    // @FormUrlEncoded
    @GET("v1/search")
    suspend fun getSong(
        @Header("Authorization") acceptType: String,
        @Header("Content-Type") contentType: String,
        @Header("Accept") authorizationToken: String,
        @Query("q") searchString: String,
        @Query("type") searchType: String,
        @Query("market") searchInMarket: String,
        @Query("limit") searchResultLimit: String

    ): Response<SearchResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): SongApi {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SongApi::class.java)
        }
    }
}






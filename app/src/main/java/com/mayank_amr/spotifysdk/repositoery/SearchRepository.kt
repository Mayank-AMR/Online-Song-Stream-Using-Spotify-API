package com.mayank_amr.spotifysdk.repositoery

import com.mayank_amr.spotifysdk.network.MyApi
import com.mayank_amr.spotifysdk.network.SafeApiRequest
import com.mayank_amr.spotifysdk.network.SongApi
import com.mayank_amr.spotifysdk.network.responces.TokenResponse
import com.mayank_amr.spotifysdk.network.responces.searchresponce.SearchResponse

/**
 * @Project spotify SDK
 * @Created_by Mayank Kumar on 13-12-2020 05:20 PM
 */
private const val searchType = "track"
private const val searchInMarket = "IN"
private const val searchResultLimit = "1"
private const val contentType = "application/json"
private const val acceptType = "application/json"

class SearchRepository(
    private val api: MyApi,
    private val songsApi: SongApi
) : SafeApiRequest() {

    suspend fun getAccessToken(encodedString: String, grantType: String): TokenResponse {
        return apiRequest { api.getAccessToken(encodedString, grantType) }
    }

    suspend fun getSongOrArtist(
        accessToken: String,
        searchString: String,
    ): SearchResponse {
        return apiRequest {
            songsApi.getSong(
                accessToken,
                contentType,
                acceptType,
                searchString,
                searchType,
                searchInMarket,
                searchResultLimit
            )
        }
    }
}
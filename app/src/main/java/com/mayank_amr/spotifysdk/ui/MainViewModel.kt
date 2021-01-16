package com.mayank_amr.spotifysdk.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayank_amr.spotifysdk.network.responces.TokenResponse
import com.mayank_amr.spotifysdk.network.responces.searchresponce.SearchResponse
import com.mayank_amr.spotifysdk.repositoery.SearchRepository
import com.mayank_amr.spotifysdk.utils.PreferenceProvider
import kotlinx.coroutines.launch


/**
 * @Project spotify SDK
 * @Created_by Mayank Kumar on 13-12-2020 10:36 AM
 */

class MainViewModel(
    private val repository: SearchRepository,
    private val prefs: PreferenceProvider
) : ViewModel() {
    private val TAG = "MainViewModel"
    val ENCODED_CLIENT_ID =
        "Basic YWVhMDY3MDcyZDRhNDQyZDlkMGRmMmRjYzYwYTU5MGQ6YjQ2MGQ3M2JiMmYxNDkwNDk5MDI4OGExOGQzMjE0NTU="
    val GRANT_TYPE = "client_credentials"

    //val data: LiveData<List<MyDataObject>?> = MutableLiveData() // I receive data as LiveData outside ViewModal bz is is LiveData type. It is cast only for Initialisation

    val tokenData: LiveData<TokenResponse> = MutableLiveData()
    val searchData: LiveData<SearchResponse> = MutableLiveData()

    //private val quary = MutableLiveData(DEFAULT)


    init {
        viewModelScope.launch {
            //CoroutineScope(viewModelScope.coroutineContext).launch {
            if (prefs.getUserAccessToken() != null) {
                Log.d(TAG, "Token downloading started............. \n")
                tokenData as MutableLiveData
                tokenData.value = repository.getAccessToken(ENCODED_CLIENT_ID, GRANT_TYPE)
                prefs.saveUserAccessToken(
                    tokenData.value!!.token_type, tokenData.value!!.access_token
                )
                Log.d(
                    TAG,
                    "Token downloaded and saved........... \n ${prefs.getUserAccessToken()} \n"
                )

            }
            if (prefs.getUserAccessToken() != null) {
                Log.d(TAG, "Song downloading.........: \n")
                //Log.d(TAG, "Token -> " + prefs.getUserAccessToken()!! + ".............\n")

                searchData as MutableLiveData

                val accessToken = prefs.getUserAccessToken()!!
                val searchString = " "

                Log.d(TAG, "Song downloading................\n")
                Log.d(TAG, "Access Token  $accessToken\n")

                searchData.value = repository.getSongOrArtist(accessToken, searchString)
                Log.d(TAG, "Song downloaded.........: \n")
            }
        }
    }

    suspend fun searchSong(searchString:String) {
        if (prefs.getUserAccessToken() != null) {
            Log.d(TAG, "Song downloading.........: \n")

            searchData as MutableLiveData
            searchData.value = repository.getSongOrArtist(prefs.getUserAccessToken()!!, searchString)
        }
    }
}
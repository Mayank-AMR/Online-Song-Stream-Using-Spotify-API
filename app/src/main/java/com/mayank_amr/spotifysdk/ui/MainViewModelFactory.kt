package com.mayank_amr.spotifysdk.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mayank_amr.spotifysdk.repositoery.SearchRepository
import com.mayank_amr.spotifysdk.utils.PreferenceProvider

/**
 * @Project spotify SDK
 * @Created_by Mayank Kumar on 13-12-2020 05:18 PM
 */
//@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: SearchRepository,
    private val prefs: PreferenceProvider

) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository,prefs) as T
    }
}
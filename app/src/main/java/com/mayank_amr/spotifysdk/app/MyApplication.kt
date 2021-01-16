package com.mayank_amr.spotifysdk.app

import android.app.Application
import com.mayank_amr.spotifysdk.network.MyApi
import com.mayank_amr.spotifysdk.network.NetworkConnectionInterceptor
import com.mayank_amr.spotifysdk.network.SongApi
import com.mayank_amr.spotifysdk.repositoery.SearchRepository
import com.mayank_amr.spotifysdk.ui.MainViewModelFactory
import com.mayank_amr.spotifysdk.utils.PreferenceProvider
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * @Project spotify SDK
 * @Created_by Mayank Kumar on 13-12-2020 04:54 PM
 */
class MyApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { SongApi(instance()) }

        bind() from singleton { SearchRepository(instance(), instance()) }
        bind() from provider { MainViewModelFactory(instance(), instance()) }


        //bind() from singleton { AppDatabase(instance()) }
//        bind() from singleton { UserRepository(instance(), instance(), instance()) }
//        bind() from singleton { HomePostRepository(instance(), instance(), instance(), instance()) }
//        bind() from singleton { ProfileRepository(instance(), instance()) }
//        bind() from provider { AuthViewModelFactory(instance()) }
//        bind() from provider { AllPostViewModelFactory(instance(), instance()) }
//        bind() from provider { ProfileViewModelFactory(instance()) }

    }
}
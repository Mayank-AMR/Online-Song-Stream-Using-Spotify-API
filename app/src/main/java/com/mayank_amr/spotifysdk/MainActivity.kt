package com.mayank_amr.spotifysdk

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mayank_amr.spotifysdk.ui.MainViewModel
import com.mayank_amr.spotifysdk.ui.MainViewModelFactory
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector.ConnectionListener
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.PlayerState
import com.spotify.protocol.types.Track
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity(), KodeinAware {


    override val kodein by kodein()
    private val factory: MainViewModelFactory by instance()


    private val CLIENT_ID = "aea067072d4a442d9d0df2dcc60a590d"
    private val REDIRECT_URI =
        "https://spotauth.github.io"  //"http://localhost:8888/callback" //"com.yourdomain.yourapp://callback"
    private var mSpotifyAppRemote: SpotifyAppRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val binding:  = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        var viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
//        binding.viewmodel = viewModel
//        viewModel.authListener = this
//
//        viewModel.getSavedAccessToken().observe(this, Observer { accessToken ->
//            if (accessToken != null) {
//                Intent(this, HomeActivity::class.java).also {
//                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // no other activity in stack bz of Flag
//                    startActivity(it)
//                }
//            }
//        })

        var viewModel = ViewModelProvider(this@MainActivity, factory).get(MainViewModel::class.java)

//        viewModel.searchData.observe(this, Observer {
//            Log.e("TAG", "onCreate: ")
//            if (it.tracks.total != 0) {
//                playSong(it.tracks.items[0].uri)
//            } else {
//                Toast.makeText(
//                    this,
//                    "No result found !!",
//                    Toast.LENGTH_SHORT
//                ).show()
//
//            }
//        })

        buttonSearch.setOnClickListener(View.OnClickListener {
            if (etSearch.text!!.trim().isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter a valid text to search.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                lifecycleScope.launch {

                    viewModel.searchSong(etSearch.text!!.toString())
                    etSearch.setText("")

                    if (viewModel.searchData.value!!.tracks.total != 0) {
                        playSong(
                            viewModel.searchData.value!!.tracks.items[0].uri
                        )
                        Toast.makeText(
                            this@MainActivity,
                            "Playing ${viewModel.searchData.value!!.tracks.items[0].name}",
                            Toast.LENGTH_SHORT
                        ).show()
                        textView.text =
                            "Track Name : " + viewModel.searchData.value!!.tracks.items[0].name
                        textView2.text =
                            "Album : " + viewModel.searchData.value!!.tracks.items[0].album.name

                        textView3.text =
                            "Artist : " + viewModel.searchData.value!!.tracks.items[0].artists[0].name
                        textView4.text =
                            "Total Result Found : " + viewModel.searchData.value!!.tracks.total


                    } else {
                        Toast.makeText(this@MainActivity, "No result found !!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }


                //Toast.makeText(this, viewModel.tokenData.value!!.token_type, Toast.LENGTH_SHORT).show()
                //viewModel.tokenData
            }
        })
    }


    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart...............\n")
        //TODO: check if Spotify app installed or not if not then redirect to install else connect.
        val connectionParams = ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build()
        Log.d("MainActivity", "Connecting...............\n\n")

        SpotifyAppRemote.connect(this, connectionParams,
            object : ConnectionListener {
                override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                    mSpotifyAppRemote = spotifyAppRemote
                    Log.d("MainActivity", "Connected! Yay!")
                    Toast.makeText(this@MainActivity, "Connected", Toast.LENGTH_SHORT).show()
                    // Now you can start interacting with App Remote
                    //connected()

                    //connected("spotify:track:1OBOW5jFnqpieVQFsxu2P9")
                }

                override fun onFailure(throwable: Throwable) {
                    Log.e("MainActivity", throwable.message, throwable)

                    // Something went wrong when attempting to connect! Handle errors here
                }
            })
    }

    override fun onStop() {
        super.onStop()
        SpotifyAppRemote.disconnect(mSpotifyAppRemote)
    }

    private fun playSong(songAddress: String) {
        // Play a playlist
        //mSpotifyAppRemote!!.playerApi.play("spotify:album:0Zwt7ZPZzacRD4wPSotgam")
        mSpotifyAppRemote!!.playerApi.play(songAddress)

        // Subscribe to PlayerState
        mSpotifyAppRemote!!.playerApi
            .subscribeToPlayerState()
            .setEventCallback { playerState: PlayerState ->
                val track: Track? = playerState.track
                if (track != null) {
                    Log.d("MainActivity", track.name.toString() + " by " + track.artist.name)
                    //Toast.makeText(this@MainActivity, track.name.toString(), Toast.LENGTH_SHORT).show()

                }
            }
    }


}
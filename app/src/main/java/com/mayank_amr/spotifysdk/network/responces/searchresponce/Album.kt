package com.mayank_amr.spotifysdk.network.responces.searchresponce

data class Album(
    val album_type: String,
    val artists: List<Artist>,
    val external_urls: ExternalUrlsX,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
)
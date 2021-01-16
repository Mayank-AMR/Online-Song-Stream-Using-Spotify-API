package com.mayank_amr.spotifysdk.network.responces.searchresponce

data class Artist(
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)
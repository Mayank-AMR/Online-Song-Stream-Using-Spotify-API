package com.mayank_amr.spotifysdk.network.responces

data class TokenResponse(
    val access_token: String,
    val expires_in: Int,
    val scope: String,
    val token_type: String
)
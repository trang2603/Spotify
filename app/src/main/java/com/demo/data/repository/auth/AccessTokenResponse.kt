package com.demo.data.repository.auth

data class AccessTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
)

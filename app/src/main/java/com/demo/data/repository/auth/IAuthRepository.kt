package com.demo.data.repository.auth

interface IAuthRepository {
    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        grantType: String,
        onSuccess: (String) -> Unit,
    )
}

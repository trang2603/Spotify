package com.demo.data.repository.auth

interface IAuth {
    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        grantType: String,
        onSuccess: (String) -> Unit,
    )
}

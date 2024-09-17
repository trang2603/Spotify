package com.demo.data.repository.auth

import com.demo.data.repository.AuthRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    private val authRetrofitClient: AuthRetrofitClient,
) : IAuth {
    override fun getAccessToken(
        clientId: String,
        clientSecret: String,
        grantType: String,
        onSuccess: (String) -> Unit,
    ) {
        val tokenCall =
            authRetrofitClient.instance.getAccessToken(
                clientId = clientId,
                clientSecret = clientSecret,
                grantType = grantType,
            )
        tokenCall.enqueue(
            object : Callback<AccessTokenResponse> {
                override fun onResponse(
                    call: Call<AccessTokenResponse>,
                    response: Response<AccessTokenResponse>,
                ) {
                    if (response.isSuccessful) {
                        val accessToken = response.body()?.access_token ?: ""
                        onSuccess(accessToken)
                    }
                }

                override fun onFailure(
                    call: Call<AccessTokenResponse>,
                    t: Throwable,
                ) {
                }
            },
        )
    }
}

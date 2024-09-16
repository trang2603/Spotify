package com.demo.data.repository

import com.demo.data.repository.datasource.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthRetrofitClient {
    private const val BASE_URL =
        "https://accounts.spotify.com/"

    val instance: ApiService by lazy {
        val retrofit =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        retrofit.create(ApiService::class.java)
    }
}

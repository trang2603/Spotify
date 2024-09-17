package com.demo.data.repository.datasource

import com.demo.data.model.Playlist
import com.demo.data.model.Playlists
import com.demo.data.repository.AccessArtists
import com.demo.data.repository.AccessPlaylist
import com.demo.data.repository.AccessTokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("api/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
    ): Call<AccessTokenResponse>

    @GET("artists")
    fun getArtists(
        @Header("Authorization") accessToken: String,
        @Query("ids") ids: String,
    ): Call<AccessArtists>

    @GET("playlists/{playlist_id}")
    fun getPlaylist(
        @Header("Authorization") accessToken: String,
        @Query("playlist_id") id: String,
    ): Call<AccessPlaylist>
}

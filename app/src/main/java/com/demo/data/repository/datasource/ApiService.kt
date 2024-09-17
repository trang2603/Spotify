package com.demo.data.repository.datasource

import com.demo.data.repository.album.AccessAlbums
import com.demo.data.repository.artist.AccessArtists
import com.demo.data.repository.auth.AccessTokenResponse
import com.demo.data.repository.playlist.AccessPlaylist
import com.demo.data.repository.track.AccessTracks
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("tracks")
    fun getTracks(
        @Header("Authorization") accessToken: String,
        @Query("ids") ids: String,
        @Query("market") market: String,
    ): Call<AccessTracks>

    @GET("albums")
    fun getSeveralAlbums(
        @Header("Authorization") accessToken: String,
        @Query("ids") ids: String,
        @Query("market") market: String,
    ): Call<AccessAlbums>

    @GET("albums/{id}/tracks")
    fun getAlbumTracks(
        @Header("Authorization") accessToken: String,
        @Path("id") id: String,
    ): Call<AccessTracks>

    @GET("artists/{id}/albums")
    fun getArtistOfAlbums(
        @Header("Authorization") accessToken: String,
        @Query("id") id: String,
    ): Call<AccessAlbums>
}

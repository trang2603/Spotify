package com.demo.data.repository.playlist

import com.demo.data.model.Playlists
import com.demo.data.repository.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistRepository(
    private val retrofitClient: RetrofitClient,
) : IPlaylistRepository {
    override fun getPlaylist(
        accessToken: String,
        playlistId: String,
        onSuccess: (Playlists?) -> Unit,
    ) {
        val playlistCall =
            retrofitClient.instance.getPlaylist(
                id = playlistId,
                accessToken = "Bearer $accessToken",
            )
        playlistCall.enqueue(
            object : Callback<AccessPlaylist> {
                override fun onResponse(
                    call: Call<AccessPlaylist>,
                    response: Response<AccessPlaylist>,
                ) {
                    val playlist = response.body()?.playlists
                    onSuccess(playlist)
                }

                override fun onFailure(
                    call: Call<AccessPlaylist>,
                    t: Throwable,
                ) {
                }
            },
        )
    }
}

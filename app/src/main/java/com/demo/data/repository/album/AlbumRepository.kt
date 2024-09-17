package com.demo.data.repository.album

import com.demo.data.model.Album
import com.demo.data.model.Track
import com.demo.data.repository.RetrofitClient
import com.demo.data.repository.track.AccessTracks
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumRepository(
    private val retrofitClient: RetrofitClient,
) : IAlbum {
    override fun getAlbums(
        accessToken: String,
        albumIds: List<String>,
        market: String,
        onSuccess: (List<Album>) -> Unit,
    ) {
        val albumCall =
            retrofitClient.instance.getSeveralAlbums(
                accessToken = "Bearer $accessToken",
                ids = albumIds.joinToString(","),
                market = market,
            )
        albumCall.enqueue(
            object : Callback<AccessAlbums> {
                override fun onResponse(
                    call: Call<AccessAlbums>,
                    response: Response<AccessAlbums>,
                ) {
                    val albums = response.body()?.albums ?: emptyList()
                    onSuccess(albums)
                }

                override fun onFailure(
                    call: Call<AccessAlbums>,
                    t: Throwable,
                ) {
                }
            },
        )
    }

    override fun getAlbumTracks(
        accessToken: String,
        id: String,
        onSuccess: (List<Track>) -> Unit,
    ) {
        val albumCall =
            retrofitClient.instance.getAlbumTracks(
                accessToken = "Bearer $accessToken",
                id = id,
            )
        albumCall.enqueue(
            object : Callback<AccessTracks> {
                override fun onResponse(
                    call: Call<AccessTracks>,
                    response: Response<AccessTracks>,
                ) {
                    val tracks = response.body()?.tracks ?: emptyList()
                    onSuccess(tracks)
                }

                override fun onFailure(
                    call: Call<AccessTracks>,
                    t: Throwable,
                ) {
                }
            },
        )
    }
}

package com.demo.data.repository.artist

import com.demo.data.model.Album
import com.demo.data.model.Artists
import com.demo.data.repository.RetrofitClient
import com.demo.data.repository.album.AccessAlbums
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistRepository(
    private val retrofitClient: RetrofitClient,
) : IArtist {
    override fun getArtists(
        accessToken: String,
        artistIds: List<String>,
        onSuccess: (List<Artists>) -> Unit,
    ) {
        val artistsCall =
            retrofitClient.instance.getArtists(
                ids = artistIds.joinToString(","),
                accessToken = "Bearer $accessToken",
            )
        artistsCall.enqueue(
            object : Callback<AccessArtists> {
                override fun onResponse(
                    call: Call<AccessArtists>,
                    response: Response<AccessArtists>,
                ) {
                    val artists = response.body()?.artists ?: emptyList()
                    onSuccess(artists)
                }

                override fun onFailure(
                    call: Call<AccessArtists>,
                    t: Throwable,
                ) {
                }
            },
        )
    }

    override fun getArtistOfAlbums(
        accessToken: String,
        id: String,
        onSuccess: (List<Album>) -> Unit,
    ) {
        val artistCall =
            retrofitClient.instance.getArtistOfAlbums(
                accessToken = "Bearer $accessToken",
                id = id,
            )
        artistCall.enqueue(
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
}

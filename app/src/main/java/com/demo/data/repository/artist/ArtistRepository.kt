package com.demo.data.repository.artist

import com.demo.data.model.Artists
import com.demo.data.repository.AccessArtists
import com.demo.data.repository.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistRepository(
    private val retrofitClient: RetrofitClient,
) : IArtistRepository {
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
}

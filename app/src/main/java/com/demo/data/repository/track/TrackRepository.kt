package com.demo.data.repository.track

import com.demo.data.model.Track
import com.demo.data.repository.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrackRepository(
    private val retrofitClient: RetrofitClient,
) : ITrack {
    override fun getTracks(
        accessToken: String,
        trackIds: List<String>,
        market: String,
        onSuccess: (List<Track>) -> Unit,
    ) {
        val tracksCall =
            retrofitClient.instance.getTracks(
                ids = trackIds.joinToString(","),
                accessToken = "Bearer $accessToken",
                market = market,
            )
        tracksCall.enqueue(
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

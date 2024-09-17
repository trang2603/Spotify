package com.demo.data.repository.track

import com.demo.data.model.Track

interface ITrack {
    fun getTracks(
        accessToken: String,
        trackIds: List<String>,
        market: String,
        onSuccess: (List<Track>) -> Unit,
    )
}

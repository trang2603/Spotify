package com.demo.data.repository.artist

import com.demo.data.model.Artists

interface IArtistRepository {
    fun getArtists(
        accessToken: String,
        artistIds: List<String>,
        onSuccess: (List<Artists>) -> Unit,
    )
}

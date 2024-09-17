package com.demo.data.repository.artist

import com.demo.data.model.Album
import com.demo.data.model.Artists

interface IArtist {
    fun getArtists(
        accessToken: String,
        artistIds: List<String>,
        onSuccess: (List<Artists>) -> Unit,
    )

    fun getArtistOfAlbums(
        accessToken: String,
        id: String,
        onSuccess: (List<Album>) -> Unit,
    )
}

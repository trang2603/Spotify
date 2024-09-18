package com.demo.data.repository.album

import com.demo.data.model.Album
import com.demo.data.model.Track

interface IAlbum {
    fun getAlbums(
        accessToken: String,
        albumIds: List<String>,
        market: String,
        onSuccess: (List<Album>) -> Unit,
    )

    fun getAlbumTracks(
        accessToken: String,
        id: String,
        market: String,
        limit: Int,
        offset: Int,
        onSuccess: (List<Track>) -> Unit,
    )
}

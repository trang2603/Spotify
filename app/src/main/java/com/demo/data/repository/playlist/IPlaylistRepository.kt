package com.demo.data.repository.playlist

import com.demo.data.model.Playlists

interface IPlaylistRepository {
    fun getPlaylist(
        accessToken: String,
        playlistId: String,
        onSuccess: (Playlists?) -> Unit,
    )
}

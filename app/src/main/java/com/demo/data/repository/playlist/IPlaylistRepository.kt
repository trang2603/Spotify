package com.demo.data.repository.playlist

import com.demo.data.model.Playlist
import com.demo.data.model.Playlists
import com.demo.data.repository.AccessPlaylist

interface IPlaylistRepository {
    fun getPlaylist (
        accessToken: String,
        playlistId: String,
        onSuccess: (Playlists?) -> Unit
    )
}
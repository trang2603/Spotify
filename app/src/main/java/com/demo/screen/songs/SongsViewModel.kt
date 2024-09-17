package com.demo.screen.songs

import android.content.ContentResolver
import android.net.Uri
import com.demo.base.BaseMVVMViewModel
import com.demo.data.model.Songs
import com.demo.data.model.Track
import com.demo.data.repository.album.IAlbum
import com.demo.data.repository.auth.IAuth

class SongsViewModel(
    private val authRepository: IAuth,
    private val albumRepository: IAlbum,
//    private val artistRepository: IArtist,
) : BaseMVVMViewModel<SongsViewModel.State, SongsViewModel.Action, SongsViewModel.Mutation, SongsViewModel.Effect>() {
    override var initialState: State = State()
    private var contentResolver: ContentResolver? = null

    fun setContentResolver(resolver: ContentResolver) {
        this.contentResolver = resolver
    }

    override fun handleAction(
        state: State,
        action: Action,
    ) {
        when (action) {
            is Action.GetList -> {
                /*val newList = initData()
                sendMutation(Mutation.GetList(newList))*/
                fetchSongs(action.albumId)
            }

            /*is Action.UpdateIconPlayPause -> {
                val updateList = updateIconPlayPause(action.songs)
                sendMutation(Mutation.GetList(updateList))
            }

            is Action.UpdateIconHeart -> {
                val updateList = updateIconHeart(action.songs)
                sendMutation(Mutation.GetList(updateList))
            }*/
        }
    }

    private fun fetchSongs(
        albumId: String,
//        artistId: String,
    ) {
        val clientId = "1512f433381a498887e433ae9740d500"
        val clientSecret = "a9aa9a3e39d54cd6a8871ff5d5c5a474"
        authRepository.getAccessToken(
            clientSecret = clientSecret,
            clientId = clientId,
            grantType = "client_credentials",
            onSuccess = { accessToken ->
                fetchSongsForAlbumAndArtist(accessToken, albumId)
            },
        )
    }

    private fun fetchSongsForAlbumAndArtist(
        accessToken: String,
        albumId: String,
//        artistId: String,
    ) {
        albumRepository.getAlbumTracks(
            accessToken = accessToken,
            id = albumId,
            onSuccess = { tracks ->
                sendMutation(Mutation.GetList(tracks))
//                fetchSongsWithArtist(accessToken, artistId, songList)
            },
        )
    }

    override fun handleMutation(
        state: State,
        mutation: Mutation,
    ): State =
        when (mutation) {
            is Mutation.GetList -> {
                state.copy(data = mutation.data)
            }
        }

    private fun initData(): List<Songs> {
        val songList = mutableListOf<Songs>()
        val uri = Uri.parse("content://com.music/songs")
        val projection = arrayOf("songName", "artist", "imgSong")
        val cursor = contentResolver?.query(uri, projection, null, null, null)

        cursor?.use {
            while (it.moveToNext()) {
                val songName = it.getString(it.getColumnIndexOrThrow("songName"))
                val artist = it.getString(it.getColumnIndexOrThrow("artist"))
                val imgSong = it.getInt(it.getColumnIndexOrThrow("imgSong"))

                songList.add(
                    Songs(
                        id = it.position.toString(),
                        imgSong = imgSong,
                        songName = songName,
                        artist = artist,
                        description = it.position.toString(),
                        heart = it.position.toString(),
                        playPause = it.position.toString(),
                    ),
                )
            }
        }
        return songList
    }

    /*private fun updateIconPlayPause(songs: Songs?): List<Songs> {
        val list = state.value.data
        return list.map {
            it.copy(
                isPlaying =
                    if (it.id == songs?.id) {
                        !it.isPlaying
                    } else {
                        false
                    },
            )
        }
    }

    private fun updateIconHeart(songs: Songs?): List<Songs> {
        val list = state.value.data
        return list.map {
            it.copy(
                isFavourite =
                    if (it.id == songs?.id) {
                        !it.isFavourite
                    } else {
                        it.isFavourite
                    },
            )
        }
    }*/

    sealed class Action : BaseMVVMViewModel.MVVMAction {
        data class GetList(
            val albumId: String,
//            val artistId: String,
        ) : Action()

        /*data class UpdateIconPlayPause(
            val songs: Songs?,
        ) : Action()

        data class UpdateIconHeart(
            val songs: Songs?,
        ) : Action()*/
    }

    sealed class Mutation : BaseMVVMViewModel.MVVMMutation {
        data class GetList(
            val data: List<Track>,
        ) : Mutation()
    }

    data class State(
        val data: List<Track> = emptyList(),
    ) : BaseMVVMViewModel.MVVMState

    sealed class Effect : BaseMVVMViewModel.MVVMEffect
}

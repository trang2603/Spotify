package com.demo.screen.albums

import com.demo.base.BaseMVVMViewModel
import com.demo.data.model.Album
import com.demo.data.model.Albums
import com.demo.data.model.Songs
import com.demo.data.repository.album.IAlbum
import com.demo.data.repository.auth.IAuth

class AlbumsViewModel(
    private val authRepository: IAuth,
    private val albumRepository: IAlbum,
) : BaseMVVMViewModel<AlbumsViewModel.State, AlbumsViewModel.Action, AlbumsViewModel.Mutation, AlbumsViewModel.Effect>() {
    override var initialState: State = State()

    override fun handleAction(
        state: State,
        action: Action,
    ) {
        when (action) {
            is Action.GetList -> {
                /*val newList = initData()
                sendMutation(Mutation.GetList(newList))*/
                fetchAlbums(action.albumIds, action.market)
            }
        }
    }

    private fun fetchAlbums(
        albumIds: List<String>,
        market: String,
    ) {
        val clientId = "1512f433381a498887e433ae9740d500"
        val clientSecret = "a9aa9a3e39d54cd6a8871ff5d5c5a474"
        authRepository.getAccessToken(
            clientId = clientId,
            clientSecret = clientSecret,
            grantType = "client_credentials",
            onSuccess = { accessToken ->
                fetchAlbumsWithToken(accessToken, albumIds, market)
            },
        )
    }

    private fun fetchAlbumsWithToken(
        accessToken: String,
        albumIds: List<String>,
        market: String,
    ) {
        albumRepository.getAlbums(
            accessToken = accessToken,
            albumIds = albumIds,
            market = market,
            onSuccess = { album ->
                sendMutation(Mutation.GetList(album))
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

    private fun initData(): List<Albums> =
        List(100) { i ->
            Albums(
                id = i.toString(),
                albumName = "Album $i",
                year = "Since: 2014",
                songs = Songs(),
            )
        }

    sealed class Action : BaseMVVMViewModel.MVVMAction {
        data class GetList(
            val albumIds: List<String>,
            val market: String,
        ) : Action()
    }

    sealed class Mutation : BaseMVVMViewModel.MVVMMutation {
        data class GetList(
            val data: List<Album>,
        ) : Mutation()
    }

    data class State(
        val data: List<Album>? = null,
    ) : BaseMVVMViewModel.MVVMState

    sealed class Effect : BaseMVVMViewModel.MVVMEffect
}

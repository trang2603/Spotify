package com.demo.screen.artist

import com.demo.base.BaseMVVMViewModel
import com.demo.data.model.Artist
import com.demo.data.model.Artists
import com.demo.data.model.Songs
import com.demo.data.repository.artist.IArtistRepository
import com.demo.data.repository.auth.IAuthRepository

class ArtistViewModel(
    private val authRepository: IAuthRepository,
    private val artistRepository: IArtistRepository,
) : BaseMVVMViewModel<ArtistViewModel.State, ArtistViewModel.Action, ArtistViewModel.Mutation, ArtistViewModel.Effect>() {
    override var initialState: State = State()

    override fun handleAction(
        state: State,
        action: Action,
    ) {
        when (action) {
            is Action.GetArtists -> {
                fetchArtists(action.artistIds)
            }
        }
    }

    private fun fetchArtists(artistIds: List<String>) {
        val clientId = "1512f433381a498887e433ae9740d500"
        val clientSecret = "a9aa9a3e39d54cd6a8871ff5d5c5a474"
            authRepository.getAccessToken(
                clientId = clientId,
                clientSecret = clientSecret,
                grantType = "client_credentials",
                onSuccess = { accessToken ->
                    fetchArtistsWithToken(accessToken, artistIds)
                },
            )
    }

    private fun fetchArtistsWithToken(
        accessToken: String,
        artistIds: List<String>,
    ) {
            artistRepository.getArtists(
                accessToken = accessToken,
                artistIds = artistIds,
                onSuccess = { artists ->
                    sendMutation(Mutation.GetList(artists))
                })
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

    private fun initData(): List<Artist> =
        List(100) { i ->
            Artist("$i", "R.drawable.ic_launcher_background", Songs())
        }

    sealed class Action : BaseMVVMViewModel.MVVMAction {
        data class GetArtists(
            val artistIds: List<String>,
        ) : Action()
    }

    sealed class Mutation : BaseMVVMViewModel.MVVMMutation {
        data class GetList(
            val data: List<Artists>,
        ) : Mutation()
    }

    data class State(
        val data: List<Artists>? = null,
    ) : BaseMVVMViewModel.MVVMState

    sealed class Effect : BaseMVVMViewModel.MVVMEffect
}

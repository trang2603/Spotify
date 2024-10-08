package com.demo.screen.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.R
import com.demo.base.BaseMVVMFragment
import com.demo.data.repository.AuthRetrofitClient
import com.demo.data.repository.RetrofitClient
import com.demo.data.repository.artist.ArtistRepository
import com.demo.data.repository.auth.AuthRepository
import com.demo.databinding.FragmentArtistBinding
import com.demo.screen.artist.adapter.ArtistAdapter
import com.demo.screen.songs.SongsFragment
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ArtistFragment : BaseMVVMFragment<ArtistViewModel>() {
    private lateinit var binding: FragmentArtistBinding
    private val adapter: ArtistAdapter =
        ArtistAdapter(onArtistClick = { artist ->
            val songsFragment = SongsFragment()
            val bundle = Bundle()
            songsFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().apply {
                add(requireActivity().findViewById<View>(R.id.container).id, songsFragment)
                addToBackStack(SongsFragment::class.simpleName)
                commit()
            }
        })
    private lateinit var viewModel: ArtistViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentArtistBinding.inflate(inflater, container, false)
        val authRepository = AuthRepository(AuthRetrofitClient)
        val artistRepository = ArtistRepository(RetrofitClient)
        viewModel = ArtistViewModel(authRepository, artistRepository)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recycleView.adapter = adapter
        val artistIds = listOf("2CIMQHirSU0MQqyYHq0eOx", "57dN52uHvrHOxijzpIgu3E", "1vCWHaC5f2uS3yhpwWbIA6")
        viewModel.sendAction(ArtistViewModel.Action.GetArtists(artistIds))
    }

    override fun observerState() {
        viewModel.state
            .map { it.data }
            .distinctUntilChanged()
            .onEach {
                adapter.submitList(it?.toList() ?: listOf())
            }.launchIn(lifecycleScope)
    }

    override fun observerEffect() {
    }
}

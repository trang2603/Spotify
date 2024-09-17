package com.demo.screen.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.R
import com.demo.base.BaseMVVMFragment
import com.demo.data.repository.AuthRetrofitClient
import com.demo.data.repository.RetrofitClient
import com.demo.data.repository.album.AlbumRepository
import com.demo.data.repository.auth.AuthRepository
import com.demo.databinding.FragmentAlbumBinding
import com.demo.screen.albums.adapter.AlbumsAdapter
import com.demo.screen.songs.SongsFragment
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class AlbumsFragment : BaseMVVMFragment<AlbumsViewModel>() {
    private lateinit var binding: FragmentAlbumBinding
    private val adapter: AlbumsAdapter =
        AlbumsAdapter(onAlbumsClick = {
            val songsFragment = SongsFragment()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                add(requireActivity().findViewById<View>(R.id.container).id, songsFragment)
                addToBackStack(SongsFragment::class.simpleName)
                commit()
            }
        })
    private val authRepository = AuthRepository(AuthRetrofitClient)
    private val albumsRepository = AlbumRepository(RetrofitClient)
    private val viewModel: AlbumsViewModel = AlbumsViewModel(authRepository, albumsRepository)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAlbum.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvAlbum.adapter = adapter
        val albums = listOf("382ObEPsp2rxGrnsizN5TX", "1A2GTWGtFfWp7KSQTwWOyo", "2noRn2Aes5aoNVsU6iWThc")
        val market = "ES"
        viewModel.sendAction(AlbumsViewModel.Action.GetList(albums, market))
    }

    override fun observerState() {
        viewModel.state
            .map { it.data }
            .distinctUntilChanged()
            .onEach { list ->
                adapter.submitList(list?.toList() ?: listOf())
            }.launchIn(lifecycleScope)
    }

    override fun observerEffect() {
    }
}

package com.demo.screen.albums.adapter

import androidx.recyclerview.widget.RecyclerView
import com.demo.data.model.Album
import com.demo.databinding.ItemAlbumBinding

class AlbumsViewHolder(
    val binding: ItemAlbumBinding,
    val onAlbumsClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onAlbumsClick(position)
            }
        }
    }

    fun bind(albums: Album) {
        binding.tvAlbumName.text = albums.name
        binding.tvYear.text = albums.release_date
    }
}

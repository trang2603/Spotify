package com.demo.screen.albums.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.demo.data.model.Album
import com.demo.databinding.ItemAlbumBinding

class AlbumsAdapter(
    val onAlbumsClick: (Album) -> Unit,
) : ListAdapter<Album, AlbumsViewHolder>(AlbumsDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AlbumsViewHolder {
        val binding =
            ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumsViewHolder(binding, onAlbumsClick = {
            val item = getItem(it)
            onAlbumsClick.invoke(item)
        })
    }

    override fun onBindViewHolder(
        holder: AlbumsViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class AlbumsDiffUtil : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(
        oldItem: Album,
        newItem: Album,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Album,
        newItem: Album,
    ): Boolean = areItemsTheSame(oldItem, newItem)
}

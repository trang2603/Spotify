package com.demo.screen.artist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.demo.data.model.Artists
import com.demo.databinding.ItemArtistBinding

class ArtistAdapter(
    val onArtistClick: (Artists) -> Unit,
) : ListAdapter<Artists, ArtistViewHolder>(ArtistDiffCalback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ArtistViewHolder {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding, onArtistClick = {
            val item = getItem(it)
            onArtistClick.invoke(item)
        })
    }

    override fun onBindViewHolder(
        holder: ArtistViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bindData(item)
    }
}

class ArtistDiffCalback : DiffUtil.ItemCallback<Artists>() {
    override fun areItemsTheSame(
        oldItem: Artists,
        newItem: Artists,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Artists,
        newItem: Artists,
    ): Boolean = areItemsTheSame(oldItem, newItem)
}

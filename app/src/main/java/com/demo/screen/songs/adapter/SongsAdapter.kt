package com.demo.screen.songs.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.demo.data.model.Track
import com.demo.databinding.ItemSongsBinding

class SongsAdapter(
    val onItemLongClick: (Int, View) -> Unit,
    // communicate with fragment
    val onItemPlayPauseClick: (Track?) -> Unit,
    val onItemHeartClick: (Track?) -> Unit,
) : ListAdapter<Track, SongsViewHolder>(MusicDiffUtil()) {
    companion object {
        val UPDATE_STATUS_AUDIO = "UPDATE_STATUS_AUDIO"
        val UPDATE_STATUS_FAVOURITE = "UPDATE_STATUS_FAVOURITE"
        val UPDATE_DATA = "UPDATE_DATA"
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SongsViewHolder {
        val binding = ItemSongsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongsViewHolder(binding, onItemLongClick, onItemPlayPauseClick = {
            // TODO: push callback -> fragment handle
            onItemPlayPauseClick.invoke(getItem(it))
        }, onItemHeartClick = {
            // TODO: push callback -> fragment handle
            onItemHeartClick.invoke(getItem(it))
        })
    }

    override fun onBindViewHolder(
        holder: SongsViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bindData(item)
    }

    override fun onBindViewHolder(
        holder: SongsViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.bindData(payloads, getItem(position))
        }
    }
}

class MusicDiffUtil : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(
        oldItem: Track,
        newItem: Track,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Track,
        newItem: Track,
    ): Boolean = areItemsTheSame(oldItem, newItem)
        /*oldItem.isPlaying == newItem.isPlaying &&
            oldItem.isFavourite == newItem.isFavourite*/

    /*override fun getChangePayload(
        oldItem: Songs,
        newItem: Songs,
    ): Any? =
        if (oldItem.isPlaying != newItem.isPlaying) {
            SongsAdapter.UPDATE_STATUS_AUDIO
        } else if (oldItem.isFavourite != newItem.isFavourite) {
            SongsAdapter.UPDATE_STATUS_FAVOURITE
        } else {
            SongsAdapter.UPDATE_DATA
        }*/
}

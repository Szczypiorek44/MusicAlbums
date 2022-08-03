package com.example.vamatask.screens.albumlist.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.data.models.Album
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumViewHolder(view: View, private val callback: Callback) :
    RecyclerView.ViewHolder(view),
    View.OnClickListener {

    private lateinit var album: Album

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        callback.onAlbumClick(album)
    }

    fun bind(album: Album) {
        this.album = album

        itemView.nameTextView.text = album.name
    }

    interface Callback {
        fun onAlbumClick(album: Album)
    }

}
package com.example.vamatask.screens.albumlist.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.data.models.Album
import com.example.vamatask.utils.load
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

        itemView.apply {
            artworkImageView.load(album.artworkUrl)
            albumNameTextView.text = album.name
            artistNameTextView.text = album.artistName
        }
    }

    interface Callback {
        fun onAlbumClick(album: Album)
    }

}
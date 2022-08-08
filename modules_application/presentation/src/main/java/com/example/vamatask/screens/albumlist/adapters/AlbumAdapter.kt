package com.example.vamatask.screens.albumlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.models.Album
import com.example.vamatask.R
import com.example.vamatask.screens.albumlist.viewholders.AlbumViewHolder

class AlbumAdapter(private val viewHolderCallback: AlbumViewHolder.Callback) : RecyclerView.Adapter<AlbumViewHolder>() {

    private var albums = ArrayList<Album>()

    fun setAlbums(albums: List<Album>) {
        this.albums = ArrayList(albums)
        notifyDataSetChanged()
    }

    fun getAlbums(): List<Album> {
        return albums
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AlbumViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_album, viewGroup, false),
            viewHolderCallback
        )
    }

    override fun onBindViewHolder(viewHolder: AlbumViewHolder, i: Int) {
        viewHolder.bind(albums[i])
    }

    override fun getItemCount(): Int {
        return albums.size
    }
}
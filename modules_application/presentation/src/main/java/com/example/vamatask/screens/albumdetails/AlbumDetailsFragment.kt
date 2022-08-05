package com.example.vamatask.screens.albumdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.data.models.Album
import com.example.vamatask.R
import com.example.vamatask.utils.load
import com.example.vamatask.utils.openUrl
import kotlinx.android.synthetic.main.fragment_album_details.*
import java.text.SimpleDateFormat

class AlbumDetailsFragment(private val album: Album) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artworkImageView.load(album.artworkUrl)
        artistNameTextView.text = album.artistName
        albumNameTextView.text = album.name
        genreTextView.text = album.genres.first().name

        releaseDateTextView.text = SimpleDateFormat("MMM d, yyyy").format(album.releaseDate)
        visitTheAlbumButton.setOnClickListener { context?.openUrl(album.url) }
    }

}
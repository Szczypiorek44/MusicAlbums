package com.example.vamatask.screens.albumdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.data.models.Album
import com.example.vamatask.R
import com.example.vamatask.utils.BundleDelegate
import com.example.vamatask.utils.load
import com.example.vamatask.utils.openUrl
import kotlinx.android.synthetic.main.fragment_album_details.*
import java.text.SimpleDateFormat

class AlbumDetailsFragment : Fragment() {

    companion object {
        fun newInstance(album: Album): AlbumDetailsFragment {
            return AlbumDetailsFragment().apply {
                arguments = Bundle().also {
                    it.album = album
                }
            }
        }
    }

    private var Bundle.album by BundleDelegate.AlbumDelegate("album")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener { activity?.onBackPressed() }

        arguments?.album?.let { album ->
            artworkImageView.load(album.artworkUrl)
            artistNameTextView.text = album.artistName
            albumNameTextView.text = album.name
            genreTextView.text = album.genres.first().name

            releaseDateTextView.text = SimpleDateFormat("MMM d, yyyy").format(album.releaseDate)
            visitTheAlbumButton.setOnClickListener { context?.openUrl(album.url) }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.album = requireArguments().album
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState ?: return
        arguments?.album = savedInstanceState.album
    }

}
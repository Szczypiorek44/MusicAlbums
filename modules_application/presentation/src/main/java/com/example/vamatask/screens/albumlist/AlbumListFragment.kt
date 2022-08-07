package com.example.vamatask.screens.albumlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.data.models.Album
import com.example.vamatask.R
import com.example.vamatask.dialogs.DecisionDialog
import com.example.vamatask.screens.albumlist.AlbumListViewModel.AlbumListEvent.*
import com.example.vamatask.screens.albumlist.adapters.AlbumAdapter
import com.example.vamatask.screens.albumlist.viewholders.AlbumViewHolder
import kotlinx.android.synthetic.main.fragment_album_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumListFragment(private val onAlbumClick: (album: Album) -> Unit) : Fragment(), AlbumViewHolder.Callback {

    private val viewModel by viewModel<AlbumListViewModel>()

    private val adapter by lazy { AlbumAdapter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeEvents()

        viewModel.getAlbums()
    }

    override fun onAlbumClick(album: Album) {
        onAlbumClick.invoke(album)
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = adapter
    }

    private fun observeEvents() {
        viewModel.liveEvent.observe(viewLifecycleOwner) {
            when (it) {
                is GetAlbumSuccess -> adapter.setAlbums(it.albumList)
                is GetAlbumError -> {
                    showRetryDownloadDecisionDialog()
                    showError(it.errorMsg)
                }
                is DownloadStarted -> loadingLayout.visibility = View.VISIBLE
                is DownloadFinished -> loadingLayout.visibility = View.GONE
            }
        }
    }

    private fun showRetryDownloadDecisionDialog() {
        DecisionDialog.newInstance(R.string.failed_to_download_albums_would_you_like_to_retry)
            .apply {
                setOnLeftButtonClick { dismiss() }
                setOnRightButtonClick { dismiss() }
            }
            .show(childFragmentManager, DecisionDialog::class.java.simpleName)
    }

    private fun showError(error: String?) = Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

}
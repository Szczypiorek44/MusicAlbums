package com.example.vamatask.screens.albumlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.data.models.Album
import com.example.vamatask.R
import com.example.vamatask.dialogs.DecisionDialog
import com.example.vamatask.screens.albumlist.AlbumListViewModel.AlbumListState.*
import com.example.vamatask.screens.albumlist.adapters.AlbumAdapter
import com.example.vamatask.screens.albumlist.viewholders.AlbumViewHolder
import com.example.vamatask.utils.BundleDelegate
import kotlinx.android.synthetic.main.fragment_album_list.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumListFragment : Fragment(), AlbumViewHolder.Callback {

    private val viewModel by viewModel<AlbumListViewModel>()

    private val adapter by lazy { AlbumAdapter(this) }

    private var Bundle.albums by BundleDelegate.AlbumList("albums")

    private lateinit var onAlbumClick: (album: Album) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_album_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.setOnRefreshListener { viewModel.refreshAlbums() }
        recyclerView.adapter = adapter

        observeEvents()

        if (savedInstanceState == null && adapter.getAlbums().isEmpty()) {
            viewModel.refreshAlbums()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.albums = adapter.getAlbums()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState ?: return
        adapter.setAlbums(savedInstanceState.albums)
    }

    override fun onAlbumClick(album: Album) {
        onAlbumClick.invoke(album)
    }

    fun setOnAlbumClick(onAlbumClick: (album: Album) -> Unit) {
        this.onAlbumClick = onAlbumClick
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getState().collect {
                    when (it) {
                        DoNothing -> {}
                        is GetAlbumSuccess -> adapter.setAlbums(it.albumList)
                        is GetAlbumNetworkError -> showRetryDownloadDecisionDialog()
                        is GetAlbumOtherError -> showError(R.string.something_went_wrong)
                        is DownloadStarted -> swipeRefreshLayout.isRefreshing = true
                        is DownloadFinished -> swipeRefreshLayout.isRefreshing = false
                    }
                }
            }
        }
    }

    private fun showRetryDownloadDecisionDialog() {
        DecisionDialog.newInstance(R.string.download_albums_failed_retry)
            .apply {
                setOnLeftButtonClick { dismiss() }
                setOnRightButtonClick {
                    dismiss()
                    viewModel.refreshAlbums()
                }
            }
            .show(childFragmentManager, DecisionDialog::class.java.simpleName)
    }

    private fun showError(@StringRes errorMsg: Int) = Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()

}
package com.example.vamatask.screens.albumlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.NoNetworkException
import com.example.data.models.Album
import com.example.domain.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class AlbumListViewModel(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    companion object {
        const val TAG = "AlbumListViewModel"
    }

    private val state = MutableStateFlow<AlbumListState>(AlbumListState.DoNothing)

    init {
        observeAlbums()
    }

    fun getState(): StateFlow<AlbumListState> = state

    fun refreshAlbums() {
        Log.d(TAG, "refreshAlbums()")
        viewModelScope.launch(Dispatchers.IO) {
            state.value = AlbumListState.DownloadStarted
            try {
                albumRepository.refreshAlbums()
                Log.d(TAG, "refreshAlbums() completed")
            } catch (e: Exception) {
                Log.d(TAG, "refreshAlbums() error: ${e.localizedMessage}")
                if (e is NoNetworkException || e is SocketTimeoutException) {
                    state.value = AlbumListState.GetAlbumNetworkError
                } else {
                    state.value = AlbumListState.GetAlbumOtherError
                }
            }
            state.value = AlbumListState.DownloadFinished
        }
    }

    private fun observeAlbums() {
        Log.d(TAG, "observeAlbums()")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                albumRepository.observeAlbums().collect() {
                    Log.d(TAG, "observeAlbums() returned ${it.size} albums")
                    state.value = AlbumListState.GetAlbumSuccess(it)
                }
            } catch (e: Exception) {
                Log.d(TAG, "observeAlbums() error: ${e.localizedMessage}")
                state.value = AlbumListState.GetAlbumOtherError
            }
        }
    }

    sealed class AlbumListState {
        object DoNothing : AlbumListState()
        class GetAlbumSuccess(val albumList: List<Album>) : AlbumListState()
        object GetAlbumNetworkError : AlbumListState()
        object GetAlbumOtherError : AlbumListState()
        object DownloadStarted : AlbumListState()
        object DownloadFinished : AlbumListState()
    }

}
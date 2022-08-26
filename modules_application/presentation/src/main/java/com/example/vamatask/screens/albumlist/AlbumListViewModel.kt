package com.example.vamatask.screens.albumlist

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.data.NoNetworkException
import com.example.data.models.Album
import com.example.domain.AlbumRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.SocketTimeoutException

class AlbumListViewModel(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    companion object {
        const val TAG = "AlbumListViewModel"
    }

    private val state = MutableStateFlow<AlbumListState>(AlbumListState.DoNothing)

    private val disposables = CompositeDisposable()

    init {
        observeAlbums()
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun getState(): StateFlow<AlbumListState> = state

    fun refreshAlbums() {
        Log.d(TAG, "refreshAlbums()")
        albumRepository.refreshAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { state.value = AlbumListState.DownloadStarted }
            .doFinally { state.value = AlbumListState.DownloadFinished }
            .subscribe(
                {
                    Log.d(TAG, "refreshAlbums() completed")
                },
                {
                    Log.d(TAG, "refreshAlbums() error: ${it.localizedMessage}")
                    if (it is NoNetworkException || it is SocketTimeoutException) {
                        state.value = AlbumListState.GetAlbumNetworkError
                    } else {
                        state.value = AlbumListState.GetAlbumOtherError
                    }
                })
            .addTo(disposables)
    }

    private fun observeAlbums() {
        Log.d(TAG, "observeAlbums()")
        albumRepository.observeAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG, "observeAlbums() returned ${it.size} albums")
                    state.value = AlbumListState.GetAlbumSuccess(it)
                },
                {
                    Log.d(TAG, "observeAlbums() error: ${it.localizedMessage}")
                    state.value = AlbumListState.GetAlbumOtherError
                })
            .addTo(disposables)
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
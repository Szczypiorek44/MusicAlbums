package com.example.vamatask.screens.albumlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.data.models.Album
import com.example.domain.AlbumRepository
import com.hadilq.liveevent.LiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class AlbumListViewModel(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    companion object {
        const val TAG = "AlbumListViewModel"
    }

    private val event = LiveEvent<AlbumListEvent>()
    val liveEvent: LiveData<AlbumListEvent> = event

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun getAlbums() {
        albumRepository.getAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { event.value = AlbumListEvent.DownloadStarted }
            .doFinally { event.value = AlbumListEvent.DownloadFinished }
            .subscribe(

                {
                    Log.d(TAG, "getAlbums() returned ${it.size} albums")
                    event.value = AlbumListEvent.GetAlbumSuccess(it)
                },
                {
                    Log.d(TAG, "getAlbums() error: ${it.localizedMessage}")
                    event.value = AlbumListEvent.GetAlbumError(it.localizedMessage)
                })
            .addTo(disposables)
    }

    sealed class AlbumListEvent {
        class GetAlbumSuccess(val albumList: List<Album>) : AlbumListEvent()
        class GetAlbumError(val errorMsg: String?) : AlbumListEvent()
        object DownloadStarted : AlbumListEvent()
        object DownloadFinished : AlbumListEvent()
    }

}
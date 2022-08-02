package com.example.vamatask.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.domain.usecases.AlbumUseCases
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class ListViewModel(
    private val albumUseCases: AlbumUseCases,
) : ViewModel() {

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun getAlbums() {
        albumUseCases.getAlbums(100)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("XDD", "${it.size}")
                },
                {
                    Log.d("XDD", "error $it")

                })
            .addTo(disposables)
    }

}
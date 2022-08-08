package com.example.domain

import com.example.data.models.Album
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface AlbumRepository {
    fun observeAlbums(): Observable<List<Album>>

    fun refreshAlbums(): Completable
}
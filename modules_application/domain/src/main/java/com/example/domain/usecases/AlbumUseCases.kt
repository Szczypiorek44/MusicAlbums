package com.example.domain.usecases

import com.example.data.models.Album
import io.reactivex.rxjava3.core.Single

interface AlbumUseCases {
    fun getAlbums(amount: Int): Single<List<Album>>
}
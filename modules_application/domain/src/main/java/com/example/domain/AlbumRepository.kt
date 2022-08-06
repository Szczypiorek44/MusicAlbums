package com.example.domain

import com.example.data.models.Album
import io.reactivex.rxjava3.core.Single

interface AlbumRepository {
    fun getAlbums(): Single<List<Album>>
}
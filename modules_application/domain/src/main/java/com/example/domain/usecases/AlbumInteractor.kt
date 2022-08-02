package com.example.domain.usecases

import com.example.data.album.remote.RemoteAlbumSource
import com.example.data.models.Album
import io.reactivex.rxjava3.core.Single

class AlbumInteractor(
    private val remoteItemSource: RemoteAlbumSource
) : AlbumUseCases {

    override fun getAlbums(amount: Int): Single<List<Album>> {
        return remoteItemSource.getAlbums(amount)
            .map { response -> response.feed.albumList }
    }
}
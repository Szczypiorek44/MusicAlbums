package com.example.domain

import android.util.Log
import com.example.data.album.local.LocalDatabaseDAO
import com.example.data.album.remote.RemoteAlbumSource
import com.example.data.models.Album
import io.reactivex.rxjava3.core.Single

class AlbumRepositoryImpl(
    private val localDatabaseDAO: LocalDatabaseDAO,
    private val remoteAlbumSource: RemoteAlbumSource
) : AlbumRepository {

    companion object {
        const val TAG = "AlbumRepositoryImpl"

        const val ALBUMS_TO_DOWNLOAD_AMOUNT = 100
    }

    override fun getAlbums(): Single<List<Album>> {
        return localDatabaseDAO.getAlbums()
            .flatMap { storedAlbums ->
                if (storedAlbums.isNotEmpty()) {
                    Log.d(TAG, "returning stored albums")
                    Single.just(storedAlbums)
                } else {
                    downloadAndStoreAlbums()
                }
            }
    }

    private fun downloadAndStoreAlbums(): Single<List<Album>> {
        return downloadAlbums()
            .flatMap { storeAlbums(it) }
    }

    private fun downloadAlbums(): Single<List<Album>> {
        Log.d(TAG, "downloading albums")
        return remoteAlbumSource.getAlbums(ALBUMS_TO_DOWNLOAD_AMOUNT)
            .map { response -> response.feed.albumList }
    }

    private fun storeAlbums(albums: List<Album>): Single<List<Album>> {
        Log.d(TAG, "storing albums")
        return localDatabaseDAO.insertAlbums(albums)
            .andThen(Single.just(albums))
    }

}
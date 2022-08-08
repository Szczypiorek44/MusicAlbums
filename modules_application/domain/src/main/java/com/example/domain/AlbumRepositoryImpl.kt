package com.example.domain

import android.util.Log
import com.example.data.album.local.LocalDatabaseDAO
import com.example.data.album.remote.RemoteAlbumSource
import com.example.data.models.Album
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class AlbumRepositoryImpl(
    private val localDatabaseDAO: LocalDatabaseDAO,
    private val remoteAlbumSource: RemoteAlbumSource
) : AlbumRepository {

    companion object {
        const val TAG = "AlbumRepositoryImpl"

        const val ALBUMS_TO_DOWNLOAD_AMOUNT = 100
    }

    override fun observeAlbums(): Observable<List<Album>> =
        localDatabaseDAO.observeAlbums()
            .skip(1) //skip first value after subscribing so it will only emit values when refreshAlbums() is invoked

    override fun refreshAlbums(): Completable =
        localDatabaseDAO.getAlbums()
            .flatMapCompletable { downloadAndStoreAlbums(it) }

    private fun downloadAndStoreAlbums(storedAlbums: List<Album>): Completable =
        downloadAlbums()
            .onErrorResumeNext {
                if (storedAlbums.isNotEmpty()) {
                    Single.just(storedAlbums)
                } else {
                    Single.error(it)
                }
            }
            .flatMapCompletable { storeAlbums(it) }

    private fun downloadAlbums(): Single<List<Album>> {
        Log.d(TAG, "downloading albums")
        return remoteAlbumSource.getAlbums(ALBUMS_TO_DOWNLOAD_AMOUNT)
            .map { response -> response.feed.albumList }
            .doOnSuccess { Log.d(TAG, "downloaded ${it.size} albums") }
    }


    private fun storeAlbums(albums: List<Album>): Completable {
        Log.d(TAG, "storing albums")
        return localDatabaseDAO.clearAlbums()
            .andThen(localDatabaseDAO.insertAlbums(albums))
            .doOnComplete { Log.d(TAG, "storing albums completed") }
    }
}
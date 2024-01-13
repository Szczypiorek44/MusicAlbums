package com.example.domain

import android.util.Log
import com.example.data.album.local.LocalDatabaseDAO
import com.example.data.album.remote.RemoteAlbumSource
import com.example.data.models.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop

class AlbumRepositoryImpl(
    private val localDatabaseDAO: LocalDatabaseDAO,
    private val remoteAlbumSource: RemoteAlbumSource
) : AlbumRepository {

    companion object {
        const val TAG = "AlbumRepositoryImpl"

        const val ALBUMS_TO_DOWNLOAD_AMOUNT = 100
    }

    override suspend fun observeAlbums(): Flow<List<Album>> {
        return localDatabaseDAO.observeAlbums()
            .drop(1) //drop first value after subscribing so it will only emit values when refreshAlbums() is invoked
    }


    override suspend fun refreshAlbums() {
        val storedAlbums = localDatabaseDAO.getAlbums()
        downloadAndStoreAlbums(storedAlbums)
    }

    private suspend fun downloadAndStoreAlbums(storedAlbums: List<Album>) {
        val albumList = try {
            downloadAlbums()
        } catch (e: Exception) {
            Log.d(TAG, "Failed to download albums. Checking if can continue")
            if (storedAlbums.isNotEmpty()) {
                Log.d(TAG, "Continuing with stored albums")
                storedAlbums
            } else {
                throw e
            }
        }
        storeAlbums(albumList)
    }

    private suspend fun downloadAlbums(): List<Album> {
        Log.d(TAG, "downloading albums")
        return remoteAlbumSource.getAlbums(ALBUMS_TO_DOWNLOAD_AMOUNT).feed.albumList
            .also { Log.d(TAG, "downloaded ${it.size} albums") }
    }


    private fun storeAlbums(albums: List<Album>) {
        Log.d(TAG, "storing albums")
        localDatabaseDAO.clearAlbums()
        localDatabaseDAO.insertAlbums(albums)
        Log.d(TAG, "storing albums completed")
    }
}
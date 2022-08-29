package com.example.domain

import com.example.data.models.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    suspend fun observeAlbums(): Flow<List<Album>>

    suspend fun refreshAlbums()
}
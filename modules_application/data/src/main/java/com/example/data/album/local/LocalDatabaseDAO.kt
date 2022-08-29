package com.example.data.album.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.data.models.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDatabaseDAO {

    @Query("SELECT * FROM albums ORDER BY position ASC")
    fun observeAlbums(): Flow<List<Album>>

    @Query("SELECT * FROM albums ORDER BY position ASC")
    fun getAlbums(): List<Album>

    @Insert(onConflict = REPLACE)
    fun insertAlbums(items: List<Album>)

    @Query("Delete from albums")
    fun clearAlbums()

}
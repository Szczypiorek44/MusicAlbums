package com.example.data.album.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.data.models.Album
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface LocalDatabaseDAO {

    @Query("SELECT * FROM albums ORDER BY position ASC")
    fun observeAlbums(): Observable<List<Album>>

    @Query("SELECT * FROM albums ORDER BY position ASC")
    fun getAlbums(): Single<List<Album>>

    @Insert(onConflict = REPLACE)
    fun insertAlbums(items: List<Album>): Completable

    @Query("Delete from albums")
    fun clearAlbums(): Completable

}
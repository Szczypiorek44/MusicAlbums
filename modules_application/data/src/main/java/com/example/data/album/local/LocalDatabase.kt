package com.example.data.album.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.models.Album

@Database(entities = [(Album::class)], version = 1)
@TypeConverters(Converters::class)
internal abstract class LocalDatabase : RoomDatabase() {

    object Builder {
        fun build(context: Context): LocalDatabaseDAO {
            val database = Room.databaseBuilder(context, LocalDatabase::class.java, "vamatask.db")
                .fallbackToDestructiveMigration()
                .build()
            return database.itemsDao()
        }
    }

    abstract fun itemsDao(): LocalDatabaseDAO
}
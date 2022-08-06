package com.example.data.album.local

import androidx.room.TypeConverter
import com.example.data.models.Album
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromGenreList(value: List<Album.Genre>): String {
        return ObjectMapper().writeValueAsString(value)
    }

    @TypeConverter
    fun toGenreList(value: String): List<Album.Genre> {
        return ObjectMapper().readValue(value, object : TypeReference<List<Album.Genre>>() {})
    }
}
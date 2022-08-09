package com.example.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "albums")
@Parcelize
data class Album constructor(
    @ColumnInfo
    @PrimaryKey
    val id: Int,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val releaseDate: Date,

    @ColumnInfo
    val artistName: String,

    @ColumnInfo
    val genres: List<Genre>,

    @ColumnInfo
    val artworkUrl: String,

    @ColumnInfo
    val url: String,

    @ColumnInfo
    val position: Int?
) : Parcelable {

    @JsonCreator
    constructor(
        @JsonProperty("id")
        id: Int,

        @JsonProperty("name")
        name: String,

        @JsonProperty("releaseDate")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        releaseDate: Date,

        @JsonProperty("artistName")
        artistName: String,

        @JsonProperty("genres")
        genres: List<Genre>,

        @JsonProperty("artworkUrl100")
        artworkUrl: String,

        @JsonProperty("url")
        url: String,
    ) : this(id, name, releaseDate, artistName, genres, artworkUrl, url, null)

    @Entity(tableName = "genres")
    @Parcelize
    data class Genre constructor(
        @ColumnInfo
        @JsonProperty("id")
        val id: Int,

        @ColumnInfo
        @JsonProperty("name")
        val name: String,
    ) : Parcelable
}

fun List<Album>.copyWithUpdatedPositions(): List<Album> {
    return mutableListOf<Album>().also {
        forEachIndexed { index, album ->
            it.add(album.copy(position = index))
        }
    }
}
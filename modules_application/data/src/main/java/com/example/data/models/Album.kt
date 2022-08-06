package com.example.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "albums")
@Parcelize
class Album(
    @ColumnInfo
    @PrimaryKey
    @JsonProperty("id")
    val id: Int,

    @ColumnInfo
    @JsonProperty("name")
    val name: String,

    @ColumnInfo
    @JsonProperty("releaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val releaseDate: Date,

    @ColumnInfo
    @JsonProperty("artistName")
    val artistName: String,

    @ColumnInfo
    @JsonProperty("genres")
    val genres: List<Genre>,

    @ColumnInfo
    @JsonProperty("artworkUrl100")
    val artworkUrl: String,

    @ColumnInfo
    @JsonProperty("url")
    val url: String,
) : Parcelable {

    @Entity(tableName = "genres")
    @Parcelize
    class Genre(
        @ColumnInfo
        @JsonProperty("id")
        val id: Int,

        @ColumnInfo
        @JsonProperty("name")
        val name: String,
    ) : Parcelable
}
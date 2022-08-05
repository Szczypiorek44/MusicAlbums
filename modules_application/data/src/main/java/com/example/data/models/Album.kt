package com.example.data.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class Album(
    @JsonProperty("id")
    val id: Int,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("releaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val releaseDate: Date,

    @JsonProperty("artistName")
    val artistName: String,

    @JsonProperty("genres")
    val genres: List<Genre>,

    @JsonProperty("artworkUrl100")
    val artworkUrl: String,

    @JsonProperty("url")
    val url: String,
) : Parcelable {

    @Parcelize
    class Genre(
        @JsonProperty("id") val id: Int,
        @JsonProperty("name") val name: String,
    ) : Parcelable
}
package com.example.data.models

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

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
) {
    class Genre(
        @JsonProperty("id") val id: Int,
        @JsonProperty("name") val name: String,
    )
}
package com.example.data.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class AlbumListResponse constructor(
    @JsonProperty("feed") val feed: Feed
) {
    class Feed @JsonCreator constructor(@JsonProperty("results") albumList: List<Album>) {
        val albumList: List<Album>

        init {
            this.albumList = albumList.copyWithUpdatedPositions()
        }
    }
}
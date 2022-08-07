package com.example.data.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class AlbumListResponse internal constructor(
    @JsonProperty("feed") val feed: Feed
) {
    class Feed @JsonCreator internal constructor(@JsonProperty("results") albumList: List<Album>) {
        val albumList: List<Album>

        init {
            this.albumList = albumList.copyWithUpdatedPositions()
        }
    }
}


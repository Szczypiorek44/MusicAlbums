package com.example.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class AlbumListResponse(
    @JsonProperty("feed") val feed: Feed
) {
    data class Feed(
        @JsonProperty("results") val albumList: List<Album>
    )
}


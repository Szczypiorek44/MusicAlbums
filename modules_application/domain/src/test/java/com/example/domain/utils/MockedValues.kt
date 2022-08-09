package com.example.domain.utils

import com.example.data.models.Album
import com.example.data.models.AlbumListResponse
import java.util.*

val albumListMock = listOf(
    Album(
        1,
        "Sempiternal",
        Date(),
        "Bring Me the Horizon",
        listOf(Album.Genre(0, "Metal")),
        "https://is5-ssl.mzstatic.com/image/thumb/Music124/v4/1e/d5/e0/1ed5e015-03cd-9ef2-3797-5c112288c099/886443690707.jpg/1000x1000bb.webp",
        "https://music.apple.com/us/album/sempiternal-deluxe/609257426"
    )
)

val albumListResponseMock = AlbumListResponse(AlbumListResponse.Feed(albumListMock))
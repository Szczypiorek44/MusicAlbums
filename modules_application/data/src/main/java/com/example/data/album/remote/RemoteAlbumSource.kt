package com.example.data.album.remote

import com.example.data.models.AlbumListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteAlbumSource {

    @GET("us/music/most-played/{amount}/albums.json")
    suspend fun getAlbums(@Path("amount") amount: Int): AlbumListResponse
}
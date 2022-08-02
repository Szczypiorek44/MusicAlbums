package com.example.data.album.remote

import com.example.data.models.AlbumListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteAlbumSource {

    @GET("us/music/most-played/{amount}/albums.json")
    fun getAlbums(@Path("amount") amount: Int)
            : Single<AlbumListResponse>
}
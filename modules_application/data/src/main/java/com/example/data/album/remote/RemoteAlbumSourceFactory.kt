package com.example.data.album.remote

import retrofit2.Retrofit

internal object RemoteAlbumSourceFactory {

    fun create(retrofit: Retrofit): RemoteAlbumSource {
        return retrofit.create(RemoteAlbumSource::class.java)
    }
}
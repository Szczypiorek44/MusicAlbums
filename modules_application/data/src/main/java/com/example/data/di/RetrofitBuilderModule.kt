package com.example.data.di

import com.example.data.API_URL
import com.example.data.BuildConfig
import com.example.data.album.remote.RemoteAlbumSource
import com.example.data.album.remote.RemoteAlbumSourceFactory
import com.example.data.network.NetworkConnectionInterceptor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Suppress("RemoveExplicitTypeArguments")
val retrofitBuilderModule = module {
    single<RemoteAlbumSource> {
        RemoteAlbumSourceFactory.create(get())
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(androidContext()))
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single<Converter.Factory> {
        JacksonConverterFactory.create(get())
    }
    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }
    single<ObjectMapper> {
        ObjectMapper().apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }
}
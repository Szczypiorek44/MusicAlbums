package com.example.domain.di

import com.example.domain.AlbumRepository
import com.example.domain.AlbumRepositoryImpl
import org.koin.dsl.module

val albumModule = module {
    single<AlbumRepository> { AlbumRepositoryImpl(get(), get()) }
}
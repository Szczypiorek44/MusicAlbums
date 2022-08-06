package com.example.domain.di

import com.example.domain.AlbumRepository
import com.example.domain.AlbumRepositoryImpl
import com.example.domain.usecases.AlbumInteractor
import com.example.domain.usecases.AlbumUseCases
import org.koin.dsl.module

val albumModule = module {
    single<AlbumUseCases> { AlbumInteractor(get()) }
    single<AlbumRepository> { AlbumRepositoryImpl(get(), get()) }
}
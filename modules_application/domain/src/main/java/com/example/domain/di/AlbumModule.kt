package com.example.domain.di

import com.example.domain.usecases.AlbumInteractor
import com.example.domain.usecases.AlbumUseCases
import org.koin.dsl.module

val albumModule = module {
    single<AlbumUseCases> { AlbumInteractor(get()) }
}
package com.example.data.di

import com.example.data.album.local.LocalDatabase
import com.example.data.album.local.LocalDatabaseDAO
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@Suppress("RemoveExplicitTypeArguments")
val databaseBuilderModule = module {
    single<LocalDatabaseDAO> { LocalDatabase.Builder.build(androidContext()) }
}
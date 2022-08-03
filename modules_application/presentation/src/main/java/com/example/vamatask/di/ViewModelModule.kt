package com.example.vamatask.di

import com.example.vamatask.screens.albumlist.AlbumListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AlbumListViewModel(get()) }
}
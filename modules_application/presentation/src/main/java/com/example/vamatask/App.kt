package com.example.vamatask

import android.app.Application
import com.example.data.di.databaseBuilderModule
import com.example.data.di.retrofitBuilderModule
import com.example.domain.di.albumModule
import com.example.vamatask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(listOf(viewModelModule, albumModule, retrofitBuilderModule, databaseBuilderModule))
        }
    }

}
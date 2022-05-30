package com.example.homework6

import android.app.Application
import com.example.homework6.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                dataBaseModule,
                networkModule,
                repositoryModule,
                listFromRetrofitViewModelModule,
                listFromDatabaseViewModelModule,
                detailsViewModelModule
            )
        }

    }
}
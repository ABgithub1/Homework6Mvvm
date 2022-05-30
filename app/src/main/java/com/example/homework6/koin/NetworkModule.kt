package com.example.homework6.koin

import com.example.homework6.model.retrofit.BreakingBadApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {

    single {
        OkHttpClient
            .Builder()
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://www.breakingbadapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single {
        get<Retrofit>().create<BreakingBadApi>()
    }
}
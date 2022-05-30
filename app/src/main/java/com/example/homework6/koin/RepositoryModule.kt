package com.example.homework6.koin

import com.example.homework6.model.repositories.PersonRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single {
        PersonRepositoryImpl(dao = get(), api = get())
    }
}
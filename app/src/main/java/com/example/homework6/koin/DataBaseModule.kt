package com.example.homework6.koin

import androidx.room.Room
import com.example.homework6.model.room.PersonDatabase
import org.koin.dsl.module

val dataBaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            PersonDatabase::class.java,
            "personDatabase_db"
        )
            .build()
    }

    single{
        get<PersonDatabase>().personDao()
    }

}

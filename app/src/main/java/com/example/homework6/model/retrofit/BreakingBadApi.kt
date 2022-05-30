package com.example.homework6.model.retrofit

import com.example.homework6.model.data.Person
import retrofit2.http.GET

interface BreakingBadApi {
    @GET("characters")
    suspend fun getPersons(): List<Person>

}

// https://www.breakingbadapi.com/api/characters
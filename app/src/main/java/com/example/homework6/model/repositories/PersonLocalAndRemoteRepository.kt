package com.example.homework6.model.repositories

import com.example.homework6.model.data.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


interface PersonLocalAndRemoteRepository {

    suspend fun getAllPersonsFromDatabase(): Result<List<Person>>

    suspend fun insertPersonToDatabase(person: Person): Result<Unit>

    suspend fun loadPersonByIdFromDatabase(id: Long): Result<Person>

    suspend fun deletePersonFromDatabase(person: Person): Result<Unit>

    suspend fun subscribeChangesDb(): Result<Flow<List<Person>>>

    suspend fun getPersonListFromApi(): Result<List<Person>>

}


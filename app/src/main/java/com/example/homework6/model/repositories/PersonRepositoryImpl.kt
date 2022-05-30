package com.example.homework6.model.repositories

import com.example.homework6.model.data.Person
import com.example.homework6.model.retrofit.BreakingBadApi
import com.example.homework6.model.room.PersonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PersonRepositoryImpl(private val dao: PersonDao, private val api: BreakingBadApi) :
    PersonLocalAndRemoteRepository {

    override suspend fun getAllPersonsFromDatabase() = withContext(Dispatchers.IO) {
        kotlin.runCatching { dao.getAll() }
    }

    override suspend fun insertPersonToDatabase(person: Person) = withContext(Dispatchers.IO) {
        kotlin.runCatching { dao.insertPerson(person) }
    }

    override suspend fun loadPersonByIdFromDatabase(id: Long) = withContext(Dispatchers.IO) {
        kotlin.runCatching { dao.loadPersonById(id) }
    }

    override suspend fun deletePersonFromDatabase(person: Person) = withContext(Dispatchers.IO) {
        kotlin.runCatching { dao.delete(person) }
    }

    override suspend fun subscribeChangesDb() = withContext(Dispatchers.IO) {
        kotlin.runCatching { dao.subscribeChanges() }
    }

    override suspend fun getPersonListFromApi() = withContext(Dispatchers.IO) {
        kotlin.runCatching { api.getPersons() }
    }
}
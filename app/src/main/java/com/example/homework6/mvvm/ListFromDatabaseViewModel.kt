package com.example.homework6.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework6.model.LceState
import com.example.homework6.model.data.Person
import com.example.homework6.model.repositories.PersonRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListFromDatabaseViewModel(private val personRepository: PersonRepositoryImpl) : ViewModel() {

    private val _lceDatabaseFlow = MutableStateFlow<LceState<Flow<List<Person>>>>(LceState.Loading)
    val lceDatabaseFlow: Flow<LceState<Flow<List<Person>>>> = _lceDatabaseFlow.asStateFlow()

    init {
        viewModelScope.launch {
            personRepository.subscribeChangesDb().fold(
                onSuccess = {
                    _lceDatabaseFlow.tryEmit(LceState.Content(persons = it))
                },
                onFailure = {
                    exception -> exception
                }
            )
        }
    }

//    try {
//        val personsDb = personRepository.subscribeChangesDb1()
//        _lceDatabaseFlow.tryEmit(LceState.Content(personsDb))
//
//    } catch (e: Throwable) {
//        _lceDatabaseFlow.tryEmit(LceState.Error(e))
//    }

    suspend fun delPersonFromDb(person: Person) {
        try {
            personRepository.deletePersonFromDatabase(person)
        } catch (e: Exception) {
            println("Delete error")
        }
    }
}
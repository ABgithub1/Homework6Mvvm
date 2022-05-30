package com.example.homework6.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework6.model.LceState
import com.example.homework6.model.data.Person
import com.example.homework6.model.repositories.PersonRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListFromRetrofitViewModel(private val personRepository: PersonRepositoryImpl) : ViewModel() {

    private val _lceFlow = MutableStateFlow<LceState<List<Person>>>(LceState.Loading)
    val lceFlow: Flow<LceState<List<Person>>> = _lceFlow.asStateFlow()

    val _searchQueryFlow = MutableStateFlow("")
    val searchQueryFlow = _searchQueryFlow.asStateFlow()

    init {
        viewModelScope.launch {
            personRepository.getPersonListFromApi()
                .fold(
                    onSuccess = {
                        _lceFlow.tryEmit(LceState.Content(persons = it))
                    },
                    onFailure = {

                    }
                )
        }
        viewModelScope.launch(Dispatchers.IO) {
            searchQueryFlow.debounce { 100 }
                .mapLatest {
                    filterPersonList(it)
                }
        }

    }

    suspend fun filterPersonList(query: String = ""): List<Person> {
        return personRepository.getPersonListFromApi().fold(
            onSuccess = { it ->
                it.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            },
            onFailure = {
                emptyList()
            }
        )
    }

    suspend fun addPersonToDb(
        id: Long,
        name: String,
        nickname: String,
        birthday: String,
        status: String,
        img: String
    ) {
        val person = Person(id, name, nickname, birthday, status, img)
        personRepository.insertPersonToDatabase(person)
    }

}
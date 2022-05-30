package com.example.homework6.model.room

import androidx.room.*
import com.example.homework6.model.data.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * FROM person")
    suspend fun getAll(): List<Person>

    @Query("SELECT * FROM person WHERE id LIKE (:id)")
    suspend fun loadPersonById(id: Long): Person

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)

    @Query("SELECT * FROM person")
    fun subscribeChanges(): Flow<List<Person>>

    @Delete
    suspend fun delete(person: Person)

}
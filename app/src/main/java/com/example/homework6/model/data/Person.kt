package com.example.homework6.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val nickname: String,
    val birthday: String,
    val status: String,
    var img: String
)

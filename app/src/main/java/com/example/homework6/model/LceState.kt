package com.example.homework6.model

sealed class LceState<out T> {
    object Loading : LceState<Nothing>()

    data class Content<T>(val persons: T) : LceState<T>()

    data class Error(val throwable: Throwable) : LceState<Nothing>()
}
package me.mrsajal.flashcart.common.utils

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
){
    class Error<T>(data: T? = null, message: String): Result<T>(data, message)
    class Success<T>(data: T): Result<T>(data)
}
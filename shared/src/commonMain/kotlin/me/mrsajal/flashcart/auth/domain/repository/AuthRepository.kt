package me.mrsajal.flashcart.auth.domain.repository

import me.mrsajal.flashcart.auth.domain.model.AuthResultData
import  me.mrsajal.flashcart.common.utils.Result
interface AuthRepository {
    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        age: String? = null,
        mobile: String,
        gender: String,
        userRole: String,
    ): Result<AuthResultData>

    suspend fun signIn(email: String, password: String):Result<AuthResultData>
}
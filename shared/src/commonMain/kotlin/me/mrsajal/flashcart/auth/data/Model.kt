package me.mrsajal.flashcart.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val userDetailsParams: UserDetailsParams
)

@Serializable
data class UserDetailsParams(
    val age: String? = null,
    val mobile: String,
    val gender: String,
    val userRole: String,
)

@Serializable
data class SignInRequest(
    val email: String,
    val password: String,
)

@Serializable
data class AuthResponseData(
    val userId: String,
    val name: String,
    val imageUrl: String,
    val userRole:String,
    val token: String,
)

@Serializable
data class AuthResponse(
    val data: AuthResponseData? = null,
    val errorMessage: String? = null,
)
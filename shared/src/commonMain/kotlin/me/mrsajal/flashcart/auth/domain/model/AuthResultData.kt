package me.mrsajal.flashcart.auth.domain.model

data class AuthResultData(
    val userId: String,
    val name: String,
    val imageUrl: String?=null,
    val token: String,
    val userRole: String
)
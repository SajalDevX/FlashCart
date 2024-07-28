package me.mrsajal.flashcart.features.profile.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.profile.data.UserEntity

@Serializable
data class ProfileApiResponseData(
    val success: Boolean,
    val message: String,
    val data: UserEntity? = null
)
data class ProfileApiResponse(
    val data: ProfileApiResponseData,
    val code: HttpStatusCode
)
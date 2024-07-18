package me.mrsajal.flashcart.common.data.local

import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.auth.domain.model.AuthResultData

@Serializable
data class UserSettings(
    val userId: String="",
    val name: String="",
    val imageUrl: String? = null,
    val userRole: String="",
    val token: String="",
)

fun AuthResultData.toUserSettings(): UserSettings {
    return UserSettings(
        userId, name, imageUrl, userRole, token,
    )
}
package me.mrsajal.flashcart.auth.data

import me.mrsajal.flashcart.auth.domain.model.AuthResultData

internal fun AuthResponseData.toAuthResultData(): AuthResultData {
    return AuthResultData(userId,name, imageUrl, token, userRole)
}
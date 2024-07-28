package me.mrsajal.flashcart.features.profile.data

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.profile.domain.model.ProfileApiResponse

internal class ProfileApiService : KtorApi() {

    suspend fun getProfile(
        userToken: String
    ): ProfileApiResponse {
        val httpResponse = client.get{
            endPoint("profile")
            setToken(userToken)
        }
        return ProfileApiResponse(
            data = httpResponse.body(),
            code = httpResponse.status
        )
    }
    suspend fun updateProfile(
        userToken: String,
        updateProfile: UpdateProfileRequest,
        fileData: ByteArray,
        fileName: String
    ): ProfileApiResponse {
        val httpResponse = client.get{
            endPoint("profile")
            setToken(userToken)
            setFormData(
                params = mapOf("profile_ data" to Json.encodeToString(updateProfile)),
                fileData = fileData,
                fileName = fileName,
                fileFieldName = "profile_image",
                fileContentType = ContentType.Any
            )
        }
        return ProfileApiResponse(
            data = httpResponse.body(),
            code = httpResponse.status
        )
    }
}
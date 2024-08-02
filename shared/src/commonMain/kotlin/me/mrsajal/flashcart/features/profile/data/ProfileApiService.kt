package me.mrsajal.flashcart.features.profile.data

import co.touchlab.kermit.Logger
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.profile.domain.model.ProfileApiResponse

internal class ProfileApiService : KtorApi() {

    suspend fun getProfile(
        userToken: String
    ): ProfileApiResponse {
        val logger = Logger.withTag("ProfileApi")

        logger.i { "Fetching profile with token: $userToken" }
        val httpResponse = client.get {
            endPoint("profile")
            setToken(userToken)
        }
        logger.i { "Profile fetched with status: ${httpResponse.status}" }

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
        val httpResponse = client.put{
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
    suspend fun updateAddress(
        userToken: String,
        updateAddress: UpdateUserAddressRequest
    ): ProfileApiResponse {
        val httpResponse = client.put{
            endPoint("profile/address")
            setToken(userToken)
            setBody(updateAddress)
        }
        return ProfileApiResponse(
            data = httpResponse.body(),
            code = httpResponse.status
        )
    }
}
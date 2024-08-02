package me.mrsajal.flashcart.features.profile.data

import co.touchlab.kermit.Logger
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.profile.domain.repository.ProfileRepository
import okio.IOException

internal class ProfileRepositoryImpl(
    private val profileApi: ProfileApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : ProfileRepository {

    override suspend fun getProfile(): Result<UserEntity> {
        return withContext(dispatcher.io) {
            val logger = Logger.withTag("ProfileRepository")
            try {
                val userData = userPreferences.getUserData()
                logger.i { "UserData retrieved: $userData" }

                val apiResponse = profileApi.getProfile(userData.token)
                logger.i { "API Response: $apiResponse" }

                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        logger.i { "Profile retrieved successfully: ${apiResponse.data.data}" }
                        Result.Success(
                            data = apiResponse.data.data!!
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        logger.e { "Bad request error: ${apiResponse.data.message}" }
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        logger.e { "Unexpected error: ${apiResponse.data.message}" }
                        Result.Error(message = apiResponse.data.message)
                    }
                }

            } catch (ioException: IOException) {
                logger.e(ioException) { "No Internet" }
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                logger.e(exception) { "An unexpected error occurred: ${exception.message}" }
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun updateProfile(
        updateProfile: UpdateProfileRequest,
        fileData: ByteArray,
        fileName: String
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = profileApi.updateProfile(
                    userToken = userData.token,
                    fileData = fileData,
                    fileName = fileName,
                    updateProfile = updateProfile
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message)
                    }
                }

            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun updateAddress(updateAddress: UpdateUserAddressRequest): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = profileApi.updateAddress(
                    userToken = userData.token,
                    updateAddress = updateAddress
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message)
                    }
                }

            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }
}
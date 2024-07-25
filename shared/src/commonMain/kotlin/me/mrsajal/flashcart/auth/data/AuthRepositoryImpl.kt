package me.mrsajal.flashcart.auth.data

import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.auth.domain.model.AuthResultData
import me.mrsajal.flashcart.auth.domain.repository.AuthRepository
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.data.local.toUserSettings
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.common.utils.Result


internal class AuthRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val authService: AuthService,
    private val userPreferences: UserPreferences
) : AuthRepository {

    override suspend fun signUp(
        name: String,
        email: String,
        password: String,
        age: String?,
        mobile: String,
        gender: String,
        userRole: String,
    ): Result<AuthResultData> {
        return withContext(dispatcher.io) {
            try {
                val detailsParams = UserDetailsParams(age, mobile, gender, userRole)
                val request =
                    SignUpRequest(name, email, password, userDetailsParams = detailsParams)
                val response = authService.signUp(request)
                if (response.data == null) {
                    Result.Error(
                        message = response.errorMessage!!
                    )
                } else {
                    userPreferences.setUserData(
                        response.data.toAuthResultData().toUserSettings()
                    )
                    println("AuthRepositoryImpl User token saved: ${response.data.token}")

                    Result.Success(
                        data = response.data.toAuthResultData()
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(
                    message = "Oops, we could not send your request, try later!"
                )
            }
        }
    }

    override suspend fun signIn(email: String, password: String): Result<AuthResultData> {
        return withContext(dispatcher.io) {
            try {
                val request = SignInRequest(email, password)
                val response = authService.signIn(request)
                if (response.data == null) {
                    Result.Error(
                        message = response.errorMessage!!
                    )
                } else {
                    userPreferences.setUserData(
                        response.data.toAuthResultData().toUserSettings()
                    )

                   println("AuthRepositoryImpl User token saved: ${response.data.token}")
                    val retrievedUserData = userPreferences.getUserData()
                    println("AuthRepositoryImpl Retrieved token: ${retrievedUserData.token}")

                    Result.Success(
                        data = response.data.toAuthResultData()
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(
                    message = "Oops, we could not send your request, try later!"
                )
            }
        }
    }
}
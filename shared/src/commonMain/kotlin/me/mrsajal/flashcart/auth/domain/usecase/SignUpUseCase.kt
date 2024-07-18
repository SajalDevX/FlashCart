package me.mrsajal.flashcart.auth.domain.usecase

import me.mrsajal.flashcart.auth.domain.model.AuthResultData
import me.mrsajal.flashcart.auth.domain.repository.AuthRepository
import me.mrsajal.flashcart.common.utils.Result
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignUpUseCase : KoinComponent {
    private val repository: AuthRepository by inject()
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        age: String? = null,
        mobile: String,
        gender: String,
        userRole: String
    ): Result<AuthResultData> {
        if (email.isBlank() || "@" !in email) {
            return Result.Error(
                message = "Invalid email"
            )
        }
        if (password.isBlank() || password.length < 4) {
            return Result.Error(
                message = "Invalid password or too short!"
            )
        }

        return repository.signUp(
            name = name,
            email = email,
            password = password,
            age = age,
            mobile = mobile,
            gender = gender,
            userRole = userRole
        )
    }
}
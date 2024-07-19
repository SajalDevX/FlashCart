package me.mrsajal.flashcart.android.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.auth.domain.usecase.SignUpUseCase
import me.mrsajal.flashcart.common.utils.Result

class SignupViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    var uiState by mutableStateOf(SignUpUiState())
        private set

    fun signUp() {
        viewModelScope.launch {
            val validationResult = validateInput()
            if (validationResult != null) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    errMessage = validationResult
                )
                return@launch
            }

            uiState = uiState.copy(isAuthenticating = true)
            val authResultData = signUpUseCase(
                email = uiState.email,
                name = uiState.username,
                password = uiState.password,
                age = uiState.age,
                mobile = uiState.mobile,
                gender = uiState.gender,
                userRole = uiState.userRole
            )
            uiState = when (authResultData) {
                is Result.Success -> {
                    uiState.copy(
                        isAuthenticating = false,
                        authSuccess = true
                    )
                }

                is Result.Error -> {
                    uiState.copy(
                        isAuthenticating = false,
                        errMessage = authResultData.message
                    )
                }
            }
        }
    }

    private fun validateInput(): String? {
        val age = uiState.age?.toIntOrNull()
        if (age == null || age < 12 || age > 100) {
            return "Age must be between 12 and 100 years."
        }

        if (uiState.mobile.length != 10 || !uiState.mobile.all { it.isDigit() }) {
            return "Mobile number must be exactly 10 digits."
        }

        if (uiState.password != uiState.confirmPassword) {
            return "Password and confirm password must match."
        }

        return null
    }

    fun updateUsername(input: String) {
        uiState = uiState.copy(username = input)
    }
    fun updateEmail(input: String) {
        uiState = uiState.copy(email = input)
    }

    fun updatePassword(input: String) {
        uiState = uiState.copy(password = input)
    }

    fun updateConfirmPassword(input: String) {
        uiState = uiState.copy(confirmPassword = input)
    }

    fun updateAge(input: String) {
        uiState = uiState.copy(age = input)
    }

    fun updateMobile(input: String) {
        uiState = uiState.copy(mobile = input)
    }

    fun updateGender(input: String) {
        uiState = uiState.copy(gender = input)
    }

    fun updateRole(input: String) {
        uiState = uiState.copy(userRole = input)
    }
}

data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val gender: String = "",
    val mobile: String = "",
    val userRole: String = "",
    val age: String? = null,
    val isAuthenticating: Boolean = false,
    val errMessage: String? = null,
    val authSuccess: Boolean = false,
)

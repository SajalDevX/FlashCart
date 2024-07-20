package me.mrsajal.flashcart.android.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.auth.domain.usecase.SignInUseCase
import me.mrsajal.flashcart.common.utils.Result

class LoginViewModel(
    private val loginUseCase: SignInUseCase
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun signIn() {
        viewModelScope.launch {
            val validationResult = validateInput()
            if (validationResult!=null){
                uiState = uiState.copy(
                    isAuthenticating = false,
                    errorMessage = validationResult
                )
                return@launch
            }
            val authResultData  = loginUseCase(
                email = uiState.email,
                password = uiState.password
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
                        errorMessage = authResultData.message
                    )
                }
            }
        }
    }


    private fun validateInput(): String? {
        return when {
            validateEmail() != null -> validateEmail()
            validatePassword() != null -> validatePassword()
            else -> null
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }

    fun validatePassword(): String? {
        if (uiState.password.isBlank()) {
            return "Password cannot be empty."
        }
        return null
    }

    fun validateEmail(): String? {
        if (uiState.email.isBlank()) {
            return "Email cannot be empty."
        }
        if (!isValidEmail(uiState.email)) {
            return "Enter a valid email address."
        }
        return null
    }

    fun updateEmail(input: String) {
        uiState = uiState.copy(email = input)
    }

    fun updatePassword(input: String) {
        uiState = uiState.copy(password = input)
    }

    fun onEmailContinue(): Boolean {
        val validationResult = validateEmail()
        return if (validationResult != null) {
            uiState = uiState.copy(errorMessage = validationResult)
            false
        } else {
            uiState = uiState.copy(errorMessage = null)
            true
        }
    }

    fun onPasswordContinue(): Boolean {
        val validationResult = validatePassword()
        return if (validationResult != null) {
            uiState = uiState.copy(errorMessage = validationResult)
            false
        } else {
            uiState = uiState.copy(errorMessage = null)
            true
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isAuthenticating: Boolean = false,
    val errorMessage: String? = null,
    val authSuccess: Boolean = false,
)
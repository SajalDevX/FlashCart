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

    private val roleMapping = mapOf(
        "Customer" to "customer",
        "Admin" to "admin",
        "Seller" to "seller",
        "Super admin" to "super_admin"
    )

    fun signUp() {
        viewModelScope.launch {
            val validationResult = validateInput()
            if (validationResult != null) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    errorMessage = validationResult
                )
                return@launch
            }

            uiState = uiState.copy(
                isAuthenticating = true,
            )

            val authResultData = signUpUseCase(
                email = uiState.email,
                name = uiState.username,
                password = uiState.password,
                age = uiState.age.toString(),
                mobile = "${uiState.countryCode}${uiState.phoneNumber}",
                gender = uiState.gender,
                userRole = roleMapping[uiState.userRole]?:"customer"
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
            validateUsername() != null -> validateUsername()
            validateEmail() != null -> validateEmail()
            validatePassword() != null -> validatePassword()
            validateAge() != null -> validateAge()
            validatePhoneNumber() != null -> validatePhoneNumber()
            else -> null
        }
    }

    fun validateUsername(): String? {
        if (uiState.username.isBlank()) {
            return "Username cannot be empty."
        }
        return null
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
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

    fun validatePassword(): String? {
        if (uiState.password.isBlank()) {
            return "Password cannot be empty."
        }
        if (uiState.confirmPassword.isBlank()) {
            return "Confirm password cannot be empty."
        }
        if (uiState.password != uiState.confirmPassword) {
            return "Passwords must match."
        }
        return null
    }

    fun validateAge(): String? {
        val age = uiState.age?.toInt()
        if (age == null || age < 12 || age > 100) {
            return "Age must be between 12 and 100 years."
        }
        return null
    }

    // New validation function for phone number and country code
    fun validatePhoneNumber(): String? {
        val fullPhoneNumber = "${uiState.countryCode}${uiState.phoneNumber}"
        if (uiState.countryCode.isBlank()) {
            return "Country code cannot be empty."
        }
        if (uiState.phoneNumber.isBlank()) {
            return "Phone number cannot be empty."
        }
        if (!isValidPhoneNumber(fullPhoneNumber)) {
            return "Enter a valid phone number with country code."
        }
        return null
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // A simple regex for phone numbers that includes country code
        val phoneRegex = "^\\+[1-9][0-9]{7,14}$".toRegex()
        return phoneNumber.matches(phoneRegex)
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

    fun updatePhoneNumber(countryCode: String, phoneNumber: String) {
        uiState = uiState.copy(countryCode = countryCode, phoneNumber = phoneNumber)
    }

    fun updateGender(input: String) {
        uiState = uiState.copy(gender = input)
    }

    fun updateRole(input: String) {
        uiState = uiState.copy(userRole = input)
    }

    fun onUsernameContinue(): Boolean {
        val validationResult = validateUsername()
        return if (validationResult != null) {
            uiState = uiState.copy(errorMessage = validationResult)
            false
        } else {
            uiState = uiState.copy(errorMessage = null)
            true
        }
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

    fun onAgeContinue(): Boolean {
        val validationResult = validateAge()
        return if (validationResult != null) {
            uiState = uiState.copy(errorMessage = validationResult)
            false
        } else {
            uiState = uiState.copy(errorMessage = null)
            true
        }
    }

    fun onPhoneNumberContinue(): Boolean {
        val validationResult = validatePhoneNumber()
        return if (validationResult != null) {
            uiState = uiState.copy(errorMessage = validationResult)
            false
        } else {
            uiState = uiState.copy(errorMessage = null)
            true
        }
    }
}

data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val gender: String = "",
    val countryCode: String = "",
    val phoneNumber: String = "",
    val userRole: String = "",
    val age: String? = "",
    val isAuthenticating: Boolean = false,
    val errorMessage: String? = null,
    val authSuccess: Boolean = false,
)

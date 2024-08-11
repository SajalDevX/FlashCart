package me.mrsajal.flashcart.android.presentation.edit_profile.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.profile.domain.usecases.GetProfileUseCase
import me.mrsajal.flashcart.features.profile.domain.usecases.UpdateProfileUseCase
import me.mrsajal.flashcart.features.profile.data.UpdateProfileRequest

class EditProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState

    var nameTextFieldValue by mutableStateOf(TextFieldValue(text = ""))
        private set
    var genderTextFieldValue by mutableStateOf(TextFieldValue(text = ""))
        private set
    var ageTextFieldValue by mutableStateOf(TextFieldValue(text = ""))
        private set
    var mobileNumberTextFieldValue by mutableStateOf(TextFieldValue(text = ""))
        private set

    init {
        loadProfile()
    }

    private fun loadProfile() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            delay(1000)
            when (val result = getProfileUseCase()) {
                is Result.Success -> {
                    val profileData = result.data
                    if (profileData != null) {
                        nameTextFieldValue = TextFieldValue(profileData.name)
                    }
                    if (profileData != null) {
                        mobileNumberTextFieldValue = TextFieldValue(profileData.userDetails.mobile)
                    }
                    if (profileData != null) {
                        ageTextFieldValue = TextFieldValue(profileData.userDetails.age.toString())
                    }
                    if (profileData != null) {
                        genderTextFieldValue = TextFieldValue(profileData.userDetails.gender)
                    }

                    if (profileData != null) {
                        _uiState.update {
                            it.copy(
                                name = profileData.name,
                                mobile = profileData.userDetails.mobile,
                                age = profileData.userDetails.age?.toInt(),
                                gender = profileData.userDetails.gender,
                                profileImage = profileData.imageUrl,
                                isLoading = false
                            )
                        }
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Failed to load profile: ${result.message}"
                        )
                    }
                }
            }
        }
    }

    private fun updateProfile() {
        val state = _uiState.value
        if (state.fileData == null || state.fileName == null) {
            _uiState.update { it.copy(errorMessage = "No image selected.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(saveLoading = true) }
            delay(1000)
            _uiState.update { it.copy(saveLoading = false) }

            val updateProfileRequest = UpdateProfileRequest(
                name = state.name,
                mobile = state.mobile,
                age = state.age.toString(),
                gender = state.gender
            )

            val result = updateProfileUseCase(
                updateProfile = updateProfileRequest,
                fileData = state.fileData,
                fileName = state.fileName
            )

            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            saveLoading = false,
                            success = true,
                            successMessage = "Profile updated successfully!",
                            errorMessage = null
                        )
                    }
                }

                is Result.Error -> {
                    Log.e("ProfileScreenViewModel", "Error: ${result.message}")
                    _uiState.update {
                        it.copy(
                            saveLoading = false,
                            success = false,
                            successMessage = null,
                            errorMessage = "Failed to update profile: ${result.message}"
                        )
                    }
                }
            }
        }
    }

    fun updateName(input: TextFieldValue) {
        nameTextFieldValue = input
        _uiState.update { it.copy(name = input.text) }
    }

    fun updateMobile(input: TextFieldValue) {
        mobileNumberTextFieldValue = input
        _uiState.update { it.copy(mobile = input.text) }
    }

    fun updateAge(input: TextFieldValue) {
        ageTextFieldValue = input
        _uiState.update { it.copy(age = input.text.toIntOrNull()) }
    }

    fun updateGender(input: TextFieldValue) {
        genderTextFieldValue = input
        _uiState.update { it.copy(gender = input.text) }
    }

    fun updateFile(fileName: String, fileData: ByteArray) {
        _uiState.update { it.copy(fileName = fileName, fileData = fileData) }
    }

    fun onUiAction(uiAction: ProfileUiAction) {
        when (uiAction) {
            ProfileUiAction.SaveProfileAction -> updateProfile()
        }
    }
}

data class EditProfileUiState(
    val name: String = "",
    val age: Int? = null,
    val gender: String = "",
    val mobile: String = "",
    val profileImage: String? = null,
    val fileName: String? = null,
    val fileData: ByteArray? = null,
    val isLoading: Boolean = false,
    val saveLoading:Boolean = false,
    val success: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EditProfileUiState

        if (name != other.name) return false
        if (age != other.age) return false
        if (gender != other.gender) return false
        if (mobile != other.mobile) return false
        if (fileName != other.fileName) return false
        if (fileData != null) {
            if (other.fileData == null) return false
            if (!fileData.contentEquals(other.fileData)) return false
        } else if (other.fileData != null) return false
        if (isLoading != other.isLoading) return false
        if (saveLoading != other.saveLoading) return false
        if (success != other.success) return false
        if (successMessage != other.successMessage) return false
        if (errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (age ?: 0)
        result = 31 * result + gender.hashCode()
        result = 31 * result + mobile.hashCode()
        result = 31 * result + (fileName?.hashCode() ?: 0)
        result = 31 * result + (fileData?.contentHashCode() ?: 0)
        result = 31 * result + isLoading.hashCode()
        result = 31 * result + saveLoading.hashCode()
        result = 31 * result + success.hashCode()
        result = 31 * result + (successMessage?.hashCode() ?: 0)
        result = 31 * result + (errorMessage?.hashCode() ?: 0)
        return result
    }
}

sealed interface ProfileUiAction {
    data object SaveProfileAction : ProfileUiAction
}

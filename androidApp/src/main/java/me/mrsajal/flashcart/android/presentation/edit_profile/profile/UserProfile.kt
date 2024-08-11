package me.mrsajal.flashcart.android.presentation.edit_profile.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditProfileDataScreen(navController: NavController) {
    val viewModel: EditProfileViewModel = koinViewModel()
    val uiState = viewModel.uiState.collectAsState()
    EditProfileScreen(
        uiState.value,
        updateFile = viewModel::updateFile,
        nameTextFieldValue = viewModel.nameTextFieldValue,
        onNameTextFieldValueChange = viewModel::updateName,
        onAgeTextFieldValueChange = viewModel::updateAge,
        onMobileTextFieldValueChange = viewModel::updateMobile,
        mobileTextFieldValue = viewModel.mobileNumberTextFieldValue,
        ageTextFieldValue = viewModel.ageTextFieldValue,
        onUiAction = viewModel::onUiAction,
        onGenderTextFieldValueChange = viewModel::updateGender,
        genderTextFieldValue = viewModel.genderTextFieldValue,
    )
}
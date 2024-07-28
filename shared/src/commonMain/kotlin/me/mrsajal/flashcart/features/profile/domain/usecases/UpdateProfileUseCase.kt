package me.mrsajal.flashcart.features.profile.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.profile.data.UpdateProfileRequest
import me.mrsajal.flashcart.features.profile.domain.repository.ProfileRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdateProfileUseCase : KoinComponent {
    private val repository by inject<ProfileRepository>()

    suspend operator fun invoke(
        updateProfile: UpdateProfileRequest,
        fileData: ByteArray,
        fileName: String
    ): Result<Boolean> {
        return repository.updateProfile(updateProfile,fileData, fileName)
    }
}
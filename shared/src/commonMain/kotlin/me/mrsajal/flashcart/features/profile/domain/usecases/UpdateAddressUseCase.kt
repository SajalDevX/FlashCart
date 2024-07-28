package me.mrsajal.flashcart.features.profile.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.profile.data.UpdateUserAddressRequest
import me.mrsajal.flashcart.features.profile.domain.repository.ProfileRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdateAddressUseCase : KoinComponent {
    private val repository by inject<ProfileRepository>()

    suspend operator fun invoke(
        updateUserAddress: UpdateUserAddressRequest
    ): Result<Boolean> {
        return repository.updateAddress(updateUserAddress)
    }
}
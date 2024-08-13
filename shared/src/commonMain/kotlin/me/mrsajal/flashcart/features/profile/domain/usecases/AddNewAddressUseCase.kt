package me.mrsajal.flashcart.features.profile.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.profile.data.AddUserAddressRequest
import me.mrsajal.flashcart.features.profile.domain.repository.ProfileRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddNewAddressUseCase : KoinComponent {
    private val repository by inject<ProfileRepository>()

    suspend operator fun invoke(
        updateUserAddress: AddUserAddressRequest
    ): Result<Boolean> {
        return repository.addAddress(updateUserAddress)
    }
}
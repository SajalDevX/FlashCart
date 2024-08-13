package me.mrsajal.flashcart.features.profile.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.profile.domain.repository.ProfileRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteAddressUseCase : KoinComponent {
    private val repository by inject<ProfileRepository>()

    suspend operator fun invoke(
       index:Int
    ): Result<Boolean> {
        return repository.deleteAddress(index)
    }
}
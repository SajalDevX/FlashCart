package me.mrsajal.flashcart.features.profile.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.profile.data.UserEntity
import me.mrsajal.flashcart.features.profile.domain.repository.ProfileRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetProfileUseCase : KoinComponent {
    private val repository by inject<ProfileRepository>()

    suspend operator fun invoke(): Result<UserEntity> {
        return repository.getProfile()
    }
}
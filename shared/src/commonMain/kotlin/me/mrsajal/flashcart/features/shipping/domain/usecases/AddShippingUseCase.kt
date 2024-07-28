package me.mrsajal.flashcart.features.shipping.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.shipping.domain.model.AddShippingRequest
import me.mrsajal.flashcart.features.shipping.domain.repository.ShippingRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddShippingUseCase : KoinComponent {
    private val repository by inject<ShippingRepository>()

    suspend operator fun invoke(
        addShippingRequest: AddShippingRequest
    ): Result<Boolean> {
        return repository.addShipping(
            addShippingRequest
        )
    }
}
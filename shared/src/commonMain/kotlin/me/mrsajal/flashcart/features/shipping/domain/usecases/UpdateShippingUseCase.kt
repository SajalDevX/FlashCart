package me.mrsajal.flashcart.features.shipping.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.shipping.domain.repository.ShippingRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdateShippingUseCase : KoinComponent {
    private val repository by inject<ShippingRepository>()

    suspend operator fun invoke(
        orderId: String,
        shippingAddress: String,
        shipCity: String,
        shipPhone: Int,
        shipName: String,
        shipEmail: String,
        shipCountry: String
    ): Result<Boolean> {
        return repository.updateShipping(
            orderId, shippingAddress, shipCity, shipPhone, shipName, shipEmail, shipCountry
        )
    }
}
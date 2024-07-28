package me.mrsajal.flashcart.features.order.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.order.domain.repository.OrderRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeliverOrderUseCase : KoinComponent {
    private val repository by inject<OrderRepository>()

    suspend operator fun invoke(
        orderId: String
    ): Result<Boolean> {
        return repository.deliverOrder(orderId)
    }
}
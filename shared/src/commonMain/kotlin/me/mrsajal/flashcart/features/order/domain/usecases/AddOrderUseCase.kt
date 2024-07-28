package me.mrsajal.flashcart.features.order.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.order.data.OrderItems
import me.mrsajal.flashcart.features.order.domain.repository.OrderRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddOrderUseCase : KoinComponent {
    private val repository by inject<OrderRepository>()

    suspend operator fun invoke(
        quantity: Int,
        subTotal: Float,
        total: Float,
        shippingCharge: Float,
        orderItems: MutableList<OrderItems>
    ): Result<Boolean> {
        return repository.orderItems(
            quantity = quantity,
            subTotal = subTotal,
            total = total,
            shippingCharge=shippingCharge,
            orderItems=orderItems
        )
    }
}
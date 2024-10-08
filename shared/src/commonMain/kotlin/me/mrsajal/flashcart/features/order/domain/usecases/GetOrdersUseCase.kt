package me.mrsajal.flashcart.features.order.domain.usecases

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.order.data.RemoteOrderEntity
import me.mrsajal.flashcart.features.order.domain.repository.OrderRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetOrdersUseCase : KoinComponent {
    private val repository by inject<OrderRepository>()

    suspend operator fun invoke(
        offset: Int,
        limit: Int
    ): Result<List<RemoteOrderEntity>> {
        val result = repository.getOrders(limit = limit, offset = offset)
        Logger.log(
            Severity.Error,
            "GetOrdersUseCase",
            message = "${result.data}",
            throwable = null
        )

        return result
    }
}
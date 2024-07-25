package me.mrsajal.flashcart.features.products.domain.usecase

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.products.data.AddProductRequest
import me.mrsajal.flashcart.features.products.domain.repository.ProductRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddProductUseCase : KoinComponent {
    private val repository by inject<ProductRepository>()

    suspend operator fun invoke(
        categoryId: String,
        subCategoryId: String?,
        brandId: String?,
        productName: String,
        productCode: String?,
        productQuantity: Int,
        productDetail: String,
        price: Double,
        discountPrice: Double?,
        videoLink: String?,
        hotDeal: String? = null,
        buyOneGetOne: String? = null,
    ): Result<Boolean> {
        val add = AddProductRequest(
            categoryId,
            subCategoryId,
            brandId,
            productName,
            productCode,
            productQuantity,
            productDetail,
            price,
            discountPrice,
            videoLink,
            hotDeal,
            buyOneGetOne
        )
        return repository.addProduct(add)
    }
}
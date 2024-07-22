package me.mrsajal.flashcart.products.domain.usecase

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.products.domain.repository.ProductRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UploadProductImageUseCase : KoinComponent {
    private val repository by inject<ProductRepository>()

    suspend operator fun invoke(
        productId: String,
        imageFileName: String,
        imageBytes: ByteArray
    ): Result<Boolean> {
        return repository.uploadImage( productId,imageFileName, imageBytes)
    }
}
package me.mrsajal.flashcart.di

import me.mrsajal.flashcart.auth.data.AuthRepositoryImpl
import me.mrsajal.flashcart.auth.data.AuthService
import me.mrsajal.flashcart.auth.domain.repository.AuthRepository
import me.mrsajal.flashcart.auth.domain.usecase.SignInUseCase
import me.mrsajal.flashcart.auth.domain.usecase.SignUpUseCase
import me.mrsajal.flashcart.common.utils.provideDispatcher
import me.mrsajal.flashcart.products.data.ProductApiService
import me.mrsajal.flashcart.products.data.ProductRepositoryImpl
import me.mrsajal.flashcart.products.domain.repository.ProductRepository
import me.mrsajal.flashcart.products.domain.usecase.AddProductUseCase
import me.mrsajal.flashcart.products.domain.usecase.DeleteProductUseCase
import me.mrsajal.flashcart.products.domain.usecase.GetAllProductsUseCase
import me.mrsajal.flashcart.products.domain.usecase.GetProductByIdUseCase
import me.mrsajal.flashcart.products.domain.usecase.GetProductsByBrand
import me.mrsajal.flashcart.products.domain.usecase.GetProductsByCategory
import me.mrsajal.flashcart.products.domain.usecase.GetProductsBySubCategory
import me.mrsajal.flashcart.products.domain.usecase.SearchProductsUseCase
import me.mrsajal.flashcart.products.domain.usecase.UpdateProductUseCase
import me.mrsajal.flashcart.products.domain.usecase.UploadProductImageUseCase
import org.koin.dsl.module
import kotlin.math.sin

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
    factory { ProductApiService() }
    single<ProductRepository> { ProductRepositoryImpl(get(), get(), get()) }
    factory { AddProductUseCase() }
    factory { DeleteProductUseCase() }
    factory { GetAllProductsUseCase() }
    factory { GetProductByIdUseCase() }
    factory { UpdateProductUseCase() }
    factory { UploadProductImageUseCase() }
    factory { GetProductsByCategory() }
    factory { GetProductsBySubCategory() }
    factory { GetProductsByBrand() }
    factory { SearchProductsUseCase() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

fun getSharedModules() = listOf(authModule, utilityModule, platformModule)
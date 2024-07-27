package me.mrsajal.flashcart.di

import me.mrsajal.flashcart.auth.data.AuthRepositoryImpl
import me.mrsajal.flashcart.auth.data.AuthService
import me.mrsajal.flashcart.auth.domain.repository.AuthRepository
import me.mrsajal.flashcart.auth.domain.usecase.SignInUseCase
import me.mrsajal.flashcart.auth.domain.usecase.SignUpUseCase
import me.mrsajal.flashcart.common.utils.provideDispatcher
import me.mrsajal.flashcart.features.brands.data.BrandApiService
import me.mrsajal.flashcart.features.brands.data.BrandRepositoryImpl
import me.mrsajal.flashcart.features.brands.domain.repositpry.BrandRepository
import me.mrsajal.flashcart.features.brands.domain.usecases.AddBrandUseCase
import me.mrsajal.flashcart.features.brands.domain.usecases.DeleteBrandUseCase
import me.mrsajal.flashcart.features.brands.domain.usecases.GetBrandsUseCase
import me.mrsajal.flashcart.features.product_category.data.CategoryApiService
import me.mrsajal.flashcart.features.product_category.domain.repository.CategoryRepository
import me.mrsajal.flashcart.features.product_category.data.CategoryRepositoryImpl
import me.mrsajal.flashcart.features.product_category.domain.usecases.AddCategoryUseCase
import me.mrsajal.flashcart.features.product_category.domain.usecases.DeleteCategoryUseCase
import me.mrsajal.flashcart.features.product_category.domain.usecases.GetProductCategoryUseCase
import me.mrsajal.flashcart.features.product_review.data.ReviewApiService
import me.mrsajal.flashcart.features.product_review.data.ReviewRepositoryImpl
import me.mrsajal.flashcart.features.product_review.domain.repository.ReviewRepository
import me.mrsajal.flashcart.features.product_review.domain.usecases.AddReviewUseCase
import me.mrsajal.flashcart.features.product_review.domain.usecases.DeleteReviewUseCase
import me.mrsajal.flashcart.features.product_review.domain.usecases.EditReviewUseCase
import me.mrsajal.flashcart.features.product_review.domain.usecases.GetProductReviewUseCase
import me.mrsajal.flashcart.features.product_subcategory.data.SubCategoryApiService
import me.mrsajal.flashcart.features.product_subcategory.domain.repository.SubCategoryRepository
import me.mrsajal.flashcart.features.product_subcategory.domain.repository.SubCategoryRepositoryImpl
import me.mrsajal.flashcart.features.product_subcategory.domain.usecase.AddSubCategoryUseCase
import me.mrsajal.flashcart.features.product_subcategory.domain.usecase.DeleteSubCategoryUseCase
import me.mrsajal.flashcart.features.product_subcategory.domain.usecase.GetSubCategoryUseCase
import me.mrsajal.flashcart.features.products.data.ProductApiService
import me.mrsajal.flashcart.features.products.data.ProductRepositoryImpl
import me.mrsajal.flashcart.features.products.domain.repository.ProductRepository
import me.mrsajal.flashcart.features.products.domain.usecase.AddProductUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.DeleteProductUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.GetAllProductsUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductByIdUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductsByBrand
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductsByCategory
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductsBySubCategory
import me.mrsajal.flashcart.features.products.domain.usecase.SearchProductsUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.UpdateProductUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.UploadProductImageUseCase
import org.koin.dsl.module

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}
private val productModule = module {
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
private val reviewModule = module {
    factory { ReviewApiService() }
    single<ReviewRepository> { ReviewRepositoryImpl(get(), get(), get()) }
    factory { AddReviewUseCase() }
    factory { EditReviewUseCase() }
    factory { GetProductReviewUseCase() }
    factory { DeleteReviewUseCase() }
}
private val brandModule = module {
    factory { BrandApiService() }
    single<BrandRepository> { BrandRepositoryImpl(get(), get(), get()) }
    factory { AddBrandUseCase() }
    factory { GetBrandsUseCase() }
    factory { DeleteBrandUseCase() }
}
private val categoryModule = module {
    factory { CategoryApiService() }
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get(), get()) }
    factory { AddCategoryUseCase() }
    factory { GetProductCategoryUseCase() }
    factory { DeleteCategoryUseCase() }
}
private val subCategoryModule = module {
    factory { SubCategoryApiService() }
    single<SubCategoryRepository> { SubCategoryRepositoryImpl(get(), get(), get()) }
    factory { AddSubCategoryUseCase() }
    factory { GetSubCategoryUseCase() }
    factory { DeleteSubCategoryUseCase() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

fun getSharedModules() = listOf(
    authModule,
    utilityModule,
    platformModule,
    productModule,
    reviewModule,
    brandModule,
    categoryModule
)
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
import me.mrsajal.flashcart.features.cart.data.CartApiService
import me.mrsajal.flashcart.features.cart.domain.repository.CartRepository
import me.mrsajal.flashcart.features.cart.data.CartRepositoryImpl
import me.mrsajal.flashcart.features.cart.domain.usecases.AddItemToCartUseCase
import me.mrsajal.flashcart.features.cart.domain.usecases.DeleteAllItemsFromCart
import me.mrsajal.flashcart.features.cart.domain.usecases.DeleteItemQtyFromCartUseCase
import me.mrsajal.flashcart.features.cart.domain.usecases.GetCartItemsUseCase
import me.mrsajal.flashcart.features.cart.domain.usecases.IncreaseItemQtyInCartUseCase
import me.mrsajal.flashcart.features.order.data.OrderApiService
import me.mrsajal.flashcart.features.order.domain.repository.OrderRepository
import me.mrsajal.flashcart.features.order.domain.repository.OrderRepositoryImpl
import me.mrsajal.flashcart.features.order.domain.usecases.AddOrderUseCase
import me.mrsajal.flashcart.features.order.domain.usecases.CancelOrderUseCase
import me.mrsajal.flashcart.features.order.domain.usecases.ConfirmOrderUseCase
import me.mrsajal.flashcart.features.order.domain.usecases.DeliverOrderUseCase
import me.mrsajal.flashcart.features.order.domain.usecases.GetOrdersUseCase
import me.mrsajal.flashcart.features.order.domain.usecases.PaymentUseCase
import me.mrsajal.flashcart.features.order.domain.usecases.ReceiveOrderUseCase
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
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductDetailsUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductsByBrand
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductsByCategory
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductsBySubCategory
import me.mrsajal.flashcart.features.products.domain.usecase.SearchProductsUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.UpdateProductUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.UploadProductImageUseCase
import me.mrsajal.flashcart.features.profile.data.ProfileApiService
import me.mrsajal.flashcart.features.profile.domain.repository.ProfileRepository
import me.mrsajal.flashcart.features.profile.data.ProfileRepositoryImpl
import me.mrsajal.flashcart.features.profile.domain.usecases.GetProfileUseCase
import me.mrsajal.flashcart.features.profile.domain.usecases.AddNewAddressUseCase
import me.mrsajal.flashcart.features.profile.domain.usecases.DeleteAddressUseCase
import me.mrsajal.flashcart.features.profile.domain.usecases.UpdateProfileUseCase
import me.mrsajal.flashcart.features.shipping.data.ShippingApiService
import me.mrsajal.flashcart.features.shipping.domain.repository.ShippingRepository
import me.mrsajal.flashcart.features.shipping.domain.repository.ShippingRepositoryImpl
import me.mrsajal.flashcart.features.shipping.domain.usecases.AddShippingUseCase
import me.mrsajal.flashcart.features.shipping.domain.usecases.DeleteShippingUseCase
import me.mrsajal.flashcart.features.shipping.domain.usecases.GetShippingUseCase
import me.mrsajal.flashcart.features.shipping.domain.usecases.UpdateShippingUseCase
import me.mrsajal.flashcart.features.stocks.data.StockApiService
import me.mrsajal.flashcart.features.stocks.domain.repository.StockRepository
import me.mrsajal.flashcart.features.stocks.domain.repository.StockRepositoryImpl
import me.mrsajal.flashcart.features.stocks.domain.usecases.AddStocksUseCase
import me.mrsajal.flashcart.features.stocks.domain.usecases.GetStocksUseCase
import me.mrsajal.flashcart.features.stocks.domain.usecases.ReduceStocksUseCase
import me.mrsajal.flashcart.features.user_shop.data.ShopApiService
import me.mrsajal.flashcart.features.user_shop.domain.repository.ShopRepository
import me.mrsajal.flashcart.features.user_shop.domain.repository.ShopRepositoryImpl
import me.mrsajal.flashcart.features.user_shop.domain.usecases.AddShopCategoryUseCase
import me.mrsajal.flashcart.features.user_shop.domain.usecases.AddShopUseCase
import me.mrsajal.flashcart.features.user_shop.domain.usecases.DeleteShopCategoryUseCase
import me.mrsajal.flashcart.features.user_shop.domain.usecases.GetShopCategoryUseCase
import me.mrsajal.flashcart.features.user_shop.domain.usecases.GetShopUseCase
import me.mrsajal.flashcart.features.user_shop.domain.usecases.UpdateShopCategoryUseCase
import me.mrsajal.flashcart.features.wishlist.data.WishlistApiService
import me.mrsajal.flashcart.features.wishlist.domain.repository.WishlistRepository
import me.mrsajal.flashcart.features.wishlist.data.WishlistRepositoryImpl
import me.mrsajal.flashcart.features.wishlist.domain.usecases.AddItemsToWishlistUseCase
import me.mrsajal.flashcart.features.wishlist.domain.usecases.GetWishListItemsUseCase
import me.mrsajal.flashcart.features.wishlist.domain.usecases.RemoveItemsFromWishlistUseCase
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
    factory { GetProductDetailsUseCase() }
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
private val wishlistModule = module {
    factory { WishlistApiService() }
    single<WishlistRepository> { WishlistRepositoryImpl(get(), get(), get()) }
    factory { AddItemsToWishlistUseCase() }
    factory { RemoveItemsFromWishlistUseCase() }
    factory { GetWishListItemsUseCase() }
}
private val orderModule = module {
    factory { OrderApiService() }
    single<OrderRepository> { OrderRepositoryImpl(get(), get(), get()) }
    factory { AddOrderUseCase() }
    factory { CancelOrderUseCase() }
    factory { ConfirmOrderUseCase() }
    factory { DeliverOrderUseCase() }
    factory { GetOrdersUseCase() }
    factory { PaymentUseCase() }
    factory { ReceiveOrderUseCase() }
}
private val utilityModule = module {
    factory { provideDispatcher() }
}
private val profileModule = module {
    factory { ProfileApiService() }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get()) }
    factory { GetProfileUseCase() }
    factory { UpdateProfileUseCase() }
    factory { DeleteAddressUseCase() }
    factory { AddNewAddressUseCase() }
}
private val shippingModule = module {
    factory { ShippingApiService() }
    single<ShippingRepository> { ShippingRepositoryImpl(get(), get(), get()) }
    factory { GetShippingUseCase() }
    factory { AddShippingUseCase() }
    factory { DeleteShippingUseCase() }
    factory { UpdateShippingUseCase() }
}
private val stockModule = module {
    factory { StockApiService() }
    single<StockRepository> { StockRepositoryImpl(get(), get(), get()) }
    factory { AddStocksUseCase() }
    factory { GetStocksUseCase() }
    factory { ReduceStocksUseCase() }
}
private val shopModule = module {
    factory { ShopApiService() }
    single<ShopRepository> { ShopRepositoryImpl(get(), get(), get()) }
    factory { AddShopCategoryUseCase() }
    factory { AddShopUseCase() }
    factory { DeleteShopCategoryUseCase() }
    factory { UpdateShopCategoryUseCase() }
    factory { GetShopCategoryUseCase() }
    factory { GetShopUseCase() }
}
private val cartModule = module {
    factory { CartApiService() }
    single<CartRepository> { CartRepositoryImpl(get(), get(), get(),get()) }
    factory { AddItemToCartUseCase() }
    factory { IncreaseItemQtyInCartUseCase() }
    factory { DeleteAllItemsFromCart() }
    factory { DeleteItemQtyFromCartUseCase() }
    factory { GetCartItemsUseCase() }
}

fun getSharedModules() = listOf(
    authModule,
    utilityModule,
    platformModule,
    productModule,
    reviewModule,
    brandModule,
    categoryModule,
    subCategoryModule,
    wishlistModule,
    orderModule,
    profileModule,
    shippingModule,
    stockModule,
    shopModule,
    cartModule
)
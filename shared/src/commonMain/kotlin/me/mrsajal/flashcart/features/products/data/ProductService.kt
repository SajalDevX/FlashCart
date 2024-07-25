package me.mrsajal.flashcart.features.products.data

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Logger.Companion.setMinSeverity
import co.touchlab.kermit.Severity
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.products.domain.model.ProductApiResponse

internal class ProductApiService : KtorApi() {


    suspend fun addProduct(
        userToken: String,
        addProduct: AddProductRequest,
    ): ProductApiResponse {
        val httpResponse = client.post {
            endPoint(path = "product/seller")
            setBody(addProduct)
            setToken(userToken)
        }
        return ProductApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun updateProduct(
        userToken: String,
        productId: String,
        updateProduct: UpdateProductRequest
    ): ProductApiResponse {
        val httpResponse = client.put {
            endPoint(path = "product/seller/update")
            parameter("productId", productId)
            setBody(updateProduct)
            setToken(userToken)
        }
        return ProductApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun getProductById(
        userToken: String,
        productId: String
    ): ProductApiResponse {
        val httpResponse = client.get {
            endPoint(path = "product/seller")
            parameter("productId", productId)
            setToken(userToken)
        }
        return ProductApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun uploadImage(
        userToken: String,
        productId: String,
        imageFileName: String,
        imageBytes: ByteArray
    ): ProductApiResponse {
        val httpResponse = client.submitFormWithBinaryData(
            url = "product/seller/img",
            formData = formData {
                append("productId", productId)
                append("file", imageBytes, Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=$imageFileName")
                    append(HttpHeaders.ContentType, ContentType.Application.OctetStream.toString())
                })
            }
        ) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $userToken")
            }
        }

        return ProductApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun deleteProduct(
        userToken: String,
        productId: String
    ): ProductApiResponse {
        val httpResponse = client.delete {
            endPoint("product/$productId")
            setToken(userToken)
        }
        return ProductApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    private val logger = Logger.withTag("ProductRepository").apply {
        setMinSeverity(Severity.Debug)  // Change to desired severity level
    }

    suspend fun getProductDetail(
        userToken: String,
        productId: String
    ): ProductApiResponse {
        val httpResponse = client.get {
            endPoint(path = "product/$productId")
            setToken(userToken)
        }
        return ProductApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun getProductsForAll(
        userToken: String,
        limit: Int,
        offset: Int,
        maxPrice: Double?,
        minPrice: Double?,
        categoryId: String?,
        searchQuery: String?,
        subCategoryId: String?,
        brandId: String?,
    ): ProductApiResponse {
        val httpResponse = client.get {
            endPoint(path = "product/get")
            parameter("limit", limit)
            parameter("offset", offset)
            parameter("maxPrice", maxPrice)
            parameter("minPrice", minPrice)
            parameter("categoryId", categoryId)
            parameter("searchQuery", searchQuery)
            parameter("subCategoryId", subCategoryId)
            parameter("brandId", brandId)
            setToken(userToken)
        }
        return ProductApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }
}

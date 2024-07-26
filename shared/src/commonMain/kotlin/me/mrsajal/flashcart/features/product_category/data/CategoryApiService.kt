package me.mrsajal.flashcart.features.product_category.data

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.product_category.domain.model.CategoryTextParams
import me.mrsajal.flashcart.features.product_category.domain.model.ProductCategoryResponse

internal class CategoryApiService : KtorApi() {

    suspend fun addCategory(
        userToken: String,
        categoryData: CategoryTextParams,
        fileData: ByteArray,
        fileName: String
    ): ProductCategoryResponse {
        val httpResponse = client.post {
            endPoint("product-category")
            setToken(userToken)
            setFormData(
                params = mapOf("category_data" to Json.encodeToString(categoryData)),
                fileData = fileData,
                fileName = fileName,
                fileFieldName = "category_image",
                fileContentType = ContentType.Any
            )
        }
        return ProductCategoryResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun getCategories(
        userToken: String,
        limit: Int,
        offset: Int
    ): ProductCategoryResponse {
        val httpResponse = client.get {
            endPoint("product-category")
            parameter("limit", limit)
            parameter("offset", offset)
            setToken(userToken)
        }
        return ProductCategoryResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun deleteCategory(
        userToken:String,
        categoryId: String
    ): ProductCategoryResponse {
        val httpResponse = client.delete {
            endPoint("product-category")
            parameter("categoryId", categoryId)
            setToken(userToken)
        }
        return ProductCategoryResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }
}
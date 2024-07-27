package me.mrsajal.flashcart.features.product_subcategory.data

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.product_subcategory.domain.model.ProductSubCategoryResponse
import me.mrsajal.flashcart.features.product_subcategory.domain.model.SubCategoryTextParams

internal class SubCategoryApiService : KtorApi() {

    suspend fun createSubcategory(
        subCategoryData : SubCategoryTextParams,
        fileData: ByteArray,
        fileName: String,
        userToken: String
    ): ProductSubCategoryResponse {
        val httpResponse = client.post {
            endPoint("product-sub-category")
            setToken(userToken)
            setFormData(
                params = mapOf("subCategory_data" to Json.encodeToString(subCategoryData)),
                fileData = fileData,
                fileName = fileName,
                fileFieldName = "subCategory_image",
                fileContentType = ContentType.Any
            )
        }
        return ProductSubCategoryResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun getSubCategories(
        userToken: String,
        categoryId:String,
        limit: Int,
        offset: Int
    ): ProductSubCategoryResponse {
        val httpResponse = client.get {
            endPoint("product-sub-category")
            parameter("categoryId",categoryId)
            parameter("limit", limit)
            parameter("offset", offset)
            setToken(userToken)
        }
        return ProductSubCategoryResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun deleteSubCategory(
        userToken:String,
        subCategoryId: String,
    ): ProductSubCategoryResponse {
        val httpResponse = client.delete {
            endPoint("product-sub-category")
            parameter("subCategoryId", subCategoryId)
            setToken(userToken)
        }
        return ProductSubCategoryResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }
}
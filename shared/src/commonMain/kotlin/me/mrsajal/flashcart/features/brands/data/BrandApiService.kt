package me.mrsajal.flashcart.features.brands.data

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.brands.domain.model.BrandApiResponse
import me.mrsajal.flashcart.features.brands.domain.model.BrandTextParams

internal class BrandApiService : KtorApi() {

    suspend fun addBrand(
        userToken: String,
        brandData: BrandTextParams,
        fileData: ByteArray,
        fileName: String
    ): BrandApiResponse {
        val httpResponse = client.post {
            endPoint("brand")
            setToken(userToken)
            setFormData(
                params = mapOf("brand_data" to Json.encodeToString(brandData)),
                fileData = fileData,
                fileName = fileName,
                fileFieldName = "brand_image",
                fileContentType = ContentType.Any
            )
        }
        return BrandApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun deleteBrand(
        userToken: String,
        brandId: String
    ): BrandApiResponse {
        val httpResponse = client.delete {
            endPoint("brand")
            parameter("brandId", brandId)
            setToken(userToken)
        }
        return BrandApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun getBrands(
        userToken: String,
        offset: Int,
        limit: Int
    ): BrandApiResponse {
        val httpResponse = client.get {
            endPoint("brand")
            parameter("offset", offset)
            parameter("limit", limit)
            setToken(userToken)
        }
        return BrandApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }
}
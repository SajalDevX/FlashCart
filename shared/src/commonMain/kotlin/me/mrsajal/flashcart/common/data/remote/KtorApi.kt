package me.mrsajal.flashcart.common.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import me.mrsajal.flashcart.common.utils.Constants

internal abstract class KtorApi {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    fun HttpRequestBuilder.endPoint(path:String){
        url{
            takeFrom(Constants.BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }
    }
    fun HttpRequestBuilder.setToken(token:String){
        headers{
            append(name = "Authorization", value = "Bearer $token")
        }
    }
    @OptIn(InternalAPI::class)
    fun HttpRequestBuilder.setFormData(
        params: Map<String, String>,
        fileData: ByteArray? = null,
        fileName: String = "file",
        fileFieldName: String = "file",
        fileContentType: ContentType = ContentType.Application.OctetStream
    ) {
        body = MultiPartFormDataContent(
            formData {
                params.forEach { (key, value) ->
                    append(key, value)
                }
                fileData?.let {
                    append(fileFieldName, it, Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=$fileName")
                        append(HttpHeaders.ContentType, fileContentType.toString())
                    })
                }
            }
        )
    }


}
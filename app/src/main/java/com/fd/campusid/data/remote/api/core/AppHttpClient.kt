package com.fd.campusid.data.remote.api.core

import com.fd.campusid.helper.JsonHelper
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlin.reflect.KClass

open class AppHttpClient(private val httpClient: HttpClient) : IAppHttpClient {

    companion object {
        const val BASE_URL = "http://universities.hipolabs.com"
    }

    /**
     * Generic GET request handler
     * @param endpoint The API endpoint
     * @param parameters Optional query parameters
     * @return ApiResponse<T> containing the result or error
     */
    override suspend fun <T : Any> get(
        endpoint: String,
        parameters: Map<String, String>,
        type: KClass<T>
    ): ApiResponse<T> {
        return try {
            val response = httpClient.get(BASE_URL + endpoint) {
                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers {
                    // bearerAuth(token = "")
                    contentType(ContentType.Application.Json)
                }
            }

            when (response.status) {
                HttpStatusCode.OK -> {
                    val formattedResponseBody = getFormattedResponseBody(response.bodyAsText())
                    ApiResponse.Success(JsonHelper.fromJson(formattedResponseBody, type))
                }

                else -> ApiResponse.Error(
                    code = response.status.value,
                    message = getErrorMessages(response.bodyAsText())
                )
            }
        } catch (e: Exception) {
            ApiResponse.Error(
                code = 500,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }

    /**
     * Generic POST request handler
     * @param endpoint The API endpoint
     * @param body The request body
     * @param parameters Optional query parameters
     * @return ApiResponse<T> containing the result or error
     */
    override suspend fun <T : Any> post(
        endpoint: String,
        body: Any,
        parameters: Map<String, String>,
        type: KClass<T>
    ): ApiResponse<T> {
        return try {
            val response = httpClient.post(BASE_URL + endpoint) {
                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers {
                    // bearerAuth(token = "")
                    contentType(ContentType.Application.Json)
                }
                setBody(body)
            }

            when (response.status) {
                HttpStatusCode.OK, HttpStatusCode.Created -> {
                    val formattedResponseBody = getFormattedResponseBody(response.bodyAsText())
                    ApiResponse.Success(JsonHelper.fromJson(formattedResponseBody, type))
                }

                else -> ApiResponse.Error(
                    code = response.status.value,
                    message = getErrorMessages(response.bodyAsText())
                )
            }
        } catch (e: Exception) {
            ApiResponse.Error(
                code = 500,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }

    /**
     * Uploads an image as ByteArray to the server
     * @param endpoint The API endpoint
     * @param fileParameter The name of the file parameter
     * @param fileAsByteArray The file as ByteArray
     * @param fileType The type of the file
     * @param fileName The name of the file
     * @param parameters Optional query parameters
     * @return ApiResponse<T> containing the result or error
     */
    override suspend fun <T : Any> upload(
        endpoint: String,
        fileParameter: String,
        fileAsByteArray: ByteArray?,
        fileType: String,
        fileName: String,
        parameters: Map<String, String>,
        type: KClass<T>
    ): ApiResponse<T> {
        if (fileAsByteArray == null) {
            return ApiResponse.Error(
                code = 400,
                message = "File is required"
            )
        }
        return try {
            val response = httpClient.post(BASE_URL + endpoint) {
                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers {
                    // bearerAuth(token = "")
                }

                setBody(MultiPartFormDataContent(
                    formData {
                        append(fileParameter, fileAsByteArray, Headers.build {
                            append(HttpHeaders.ContentType, fileType)
                            append(HttpHeaders.ContentDisposition, "filename=$fileName")
                        })
                    }
                ))
            }

            when (response.status) {
                HttpStatusCode.OK, HttpStatusCode.Created -> {
                    val formattedResponseBody = getFormattedResponseBody(response.bodyAsText())
                    ApiResponse.Success(JsonHelper.fromJson(formattedResponseBody, type))
                }

                else -> ApiResponse.Error(
                    code = response.status.value,
                    message = getErrorMessages(response.bodyAsText())
                )
            }
        } catch (e: Exception) {
            ApiResponse.Error(
                code = 500,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }

    private fun getFormattedResponseBody(responseBody: String?): String {
        if (responseBody.isNullOrEmpty()) return "{}"
        if (responseBody.length > 1 && responseBody[0] == '[') {
            // wrap with {}
            return "{ \"data\" : $responseBody}"
        }
        return responseBody
    }

    private fun getErrorMessages(responseBody: String): String {
        try {
            val jsonObject = Json.parseToJsonElement(responseBody) as? JsonObject ?: return responseBody
            val message = jsonObject["message"]?.toString() ?: return responseBody
            return message
        } catch (e: Exception) {
            return responseBody
        }
    }
}
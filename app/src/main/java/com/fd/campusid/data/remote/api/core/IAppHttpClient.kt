package com.fd.campusid.data.remote.api.core

import kotlin.reflect.KClass

interface IAppHttpClient {

    suspend fun<T : Any> get(
        endpoint: String,
        parameters: Map<String, String> = emptyMap(),
        type: KClass<T>
    ): ApiResponse<T>

    suspend fun<T : Any> post(
        endpoint: String,
        body: Any,
        parameters: Map<String, String> = emptyMap(),
        type: KClass<T>
    ): ApiResponse<T>

    suspend fun<T : Any> upload(
        endpoint: String,
        fileParameter: String,
        fileAsByteArray: ByteArray?,
        fileType: String,
        fileName: String,
        parameters: Map<String, String> = emptyMap(),
        type: KClass<T>
    ): ApiResponse<T>

}
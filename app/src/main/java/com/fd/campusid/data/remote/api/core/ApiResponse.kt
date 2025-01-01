package com.fd.campusid.data.remote.api.core

import com.fd.campusid.helper.StatusCode

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val code: Int = StatusCode.BAD_REQUEST, val message: String) : ApiResponse<Nothing>()
}
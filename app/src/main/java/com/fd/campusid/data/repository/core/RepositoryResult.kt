package com.fd.campusid.data.repository.core

import com.fd.campusid.helper.StatusCode

sealed class RepositoryResult<out T> {
    data class Success<T>(val data: T) : RepositoryResult<T>()
    data class Error(val code: Int = StatusCode.BAD_REQUEST, val message: String) : RepositoryResult<Nothing>()
}
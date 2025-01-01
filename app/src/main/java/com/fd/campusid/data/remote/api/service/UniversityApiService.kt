package com.fd.campusid.data.remote.api.service

import com.fd.campusid.data.remote.api.core.ApiResponse
import com.fd.campusid.data.remote.api.core.IAppHttpClient
import com.fd.campusid.data.remote.api.request.UniversitiesRequest
import com.fd.campusid.data.remote.api.response.UniversitiesResponse


class UniversityApiService(private val appHttpClient: IAppHttpClient) {

    suspend fun download(request: UniversitiesRequest): ApiResponse<UniversitiesResponse> {
        return appHttpClient.get(
            endpoint = "/search",
            parameters = mapOf(
                "country" to request.country,
                "offset" to request.offset.toString(),
                "limit" to request.limit.toString()
            ),
            type = UniversitiesResponse::class
        )
    }

}
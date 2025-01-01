package com.fd.campusid.data.remote.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UniversitiesResponse(
    @SerialName("data")
    var data: List<UniversitiesResponseItem?>? = null
)
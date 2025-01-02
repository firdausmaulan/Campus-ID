package com.fd.campusid.data.repository.university.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DownloadUniversity(
    @SerialName("offset")
    var offset: Int? = null,
    @SerialName("total")
    var total: Int? = null,
    @SerialName("status")
    var status: String? = null
)
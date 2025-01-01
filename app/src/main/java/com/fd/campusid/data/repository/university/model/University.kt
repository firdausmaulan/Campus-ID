package com.fd.campusid.data.repository.university.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class University(
    @SerialName("web_pages")
    var webPages: String? = null,
    @SerialName("alpha_two_code")
    var alphaTwoCode: String? = null,
    @SerialName("state-province")
    var stateProvince: String? = null,
    @SerialName("domains")
    var domains: String? = null,
    @SerialName("country")
    var country: String? = null,
    @SerialName("name")
    var name: String? = null
)
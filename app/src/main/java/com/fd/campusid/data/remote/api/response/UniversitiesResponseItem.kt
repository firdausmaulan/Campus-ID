package com.fd.campusid.data.remote.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UniversitiesResponseItem(
    @SerialName("web_pages")
    var webPages: List<String?>? = null,
    @SerialName("alpha_two_code")
    var alphaTwoCode: String? = null,
    @SerialName("state-province")
    var stateProvince: String? = null,
    @SerialName("domains")
    var domains: List<String?>? = null,
    @SerialName("country")
    var country: String? = null,
    @SerialName("name")
    var name: String? = null
)
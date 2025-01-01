package com.fd.campusid.data.remote.api.request

import com.fd.campusid.helper.Constant

data class UniversitiesRequest(
    var country: String = "indonesia",
    var offset: Int = 1,
    var limit: Int = Constant.DEFAULT_LIMIT
)

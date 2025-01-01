package com.fd.campusid.data.local.preference

class AppPreference(private val corePreference: ICorePreference) {

    companion object {
        const val DOWNLOAD_STATUS = "download_status"
        const val LAST_PAGE = "last_page"
    }

    suspend fun saveDownloadStatus(status: String) {
        corePreference.save(DOWNLOAD_STATUS, status)
    }

    suspend fun getDownloadStatus(): String {
        return corePreference.getString(DOWNLOAD_STATUS) ?: ""
    }

    suspend fun saveLastPage(page: Int) {
        corePreference.save(LAST_PAGE, page)
    }

    suspend fun getLastPage(): Int {
        return corePreference.getInt(LAST_PAGE) ?: 1
    }

}
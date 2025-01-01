package com.fd.campusid.ui.download

import com.fd.campusid.data.repository.university.model.DownloadUniversity

sealed class DownloadState {
    data object Loading : DownloadState()
    data class Error(val message: String) : DownloadState()
    data class InProgress(val download: DownloadUniversity) : DownloadState()
    data object Completed : DownloadState()
}
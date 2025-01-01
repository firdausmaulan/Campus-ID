package com.fd.campusid.ui.download

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fd.campusid.data.repository.university.UniversityRepository
import com.fd.campusid.data.repository.core.RepositoryResult
import com.fd.campusid.data.repository.university.model.DownloadUniversity
import kotlinx.coroutines.launch

class DownloadViewModel(private val universityRepository: UniversityRepository) : ViewModel() {

    var state by mutableStateOf<DownloadState>(DownloadState.Loading)

    var inProgressMessage by mutableStateOf("")

    fun isDownloadCompleted() {
        viewModelScope.launch {
            val isCompleted = universityRepository.isDownloadCompleted()
            if (isCompleted) {
                state = DownloadState.Completed
            } else {
                downloadUniversities()
            }
        }
    }

    fun downloadUniversities() {
        viewModelScope.launch {
            val result = universityRepository.download()
            if (result is RepositoryResult.Success) {
                state = DownloadState.InProgress(result.data)
                getInProgressMessage(result.data)
                if (result.data.status == DownloadStatus.COMPLETED) {
                    state = DownloadState.Completed
                    return@launch
                } else {
                    downloadUniversities()
                }
            } else if (result is RepositoryResult.Error) {
                state = DownloadState.Error(result.message)
            }
        }
    }

    private fun getInProgressMessage(download: DownloadUniversity) {
        inProgressMessage = "${download.total} universities downloaded from ${download.page} page"
    }
}
package com.fd.campusid.data.repository.university

import com.fd.campusid.data.repository.core.RepositoryResult
import com.fd.campusid.data.repository.university.model.DownloadUniversity
import com.fd.campusid.data.repository.university.model.University

interface UniversityRepository {
    suspend fun isDownloadCompleted(): Boolean
    suspend fun download(): RepositoryResult<DownloadUniversity>
    suspend fun search(query: String, page : Int): RepositoryResult<List<University>>
    suspend fun refreshDelay()
}
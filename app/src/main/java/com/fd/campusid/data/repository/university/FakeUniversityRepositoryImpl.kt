package com.fd.campusid.data.repository.university

import com.fd.campusid.data.repository.core.RepositoryResult
import com.fd.campusid.data.repository.university.model.DownloadUniversity
import com.fd.campusid.data.repository.university.model.University

class FakeUniversityRepositoryImpl : UniversityRepository {

    override suspend fun isDownloadCompleted(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun download(): RepositoryResult<DownloadUniversity> {
        TODO("Not yet implemented")
    }

    override suspend fun search(query: String, page: Int): RepositoryResult<List<University>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshDelay() {
        TODO("Not yet implemented")
    }
}
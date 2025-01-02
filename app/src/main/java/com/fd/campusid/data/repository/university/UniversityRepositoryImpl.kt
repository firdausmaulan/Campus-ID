package com.fd.campusid.data.repository.university

import com.fd.campusid.data.local.database.service.IUniversityDbService
import com.fd.campusid.data.local.preference.AppPreference
import com.fd.campusid.data.mapper.UniversityMapper
import com.fd.campusid.data.remote.api.core.ApiResponse
import com.fd.campusid.data.remote.api.request.UniversitiesRequest
import com.fd.campusid.data.remote.api.service.UniversityApiService
import com.fd.campusid.data.repository.core.RepositoryResult
import com.fd.campusid.data.repository.university.model.DownloadUniversity
import com.fd.campusid.data.repository.university.model.University
import com.fd.campusid.helper.Constant
import com.fd.campusid.helper.StatusCode
import com.fd.campusid.ui.download.DownloadStatus
import kotlinx.coroutines.delay

class UniversityRepositoryImpl(
    private val apiService: UniversityApiService,
    private val universityDbService: IUniversityDbService,
    private val appPreference: AppPreference
) : UniversityRepository {

    override suspend fun isDownloadCompleted(): Boolean {
        val downloadStatus = appPreference.getDownloadStatus()
        return downloadStatus == DownloadStatus.COMPLETED
    }

    override suspend fun download(): RepositoryResult<DownloadUniversity> {
        val offset = appPreference.getLastPage() * Constant.DEFAULT_LIMIT
        val request = UniversitiesRequest(
            offset = offset
        )
        val response = apiService.download(request)
        if (response is ApiResponse.Success) {
            val universityEntities = UniversityMapper.mapToEntityList(response.data.data)
            universityDbService.save(universityEntities)
            appPreference.saveLastPage(request.offset)
            var downloadStatus = DownloadStatus.IN_PROGRESS
            if (response.data.data?.isEmpty() == true) {
                downloadStatus = DownloadStatus.COMPLETED
                appPreference.saveDownloadStatus(downloadStatus)
            }
            val total = universityDbService.countAll()
            val downloadUniversity = DownloadUniversity(
                offset = offset,
                total = total,
                status = downloadStatus
            )
            appPreference.saveLastPage(request.offset + 1)
            return RepositoryResult.Success(downloadUniversity)
        } else if (response is ApiResponse.Error) {
            return RepositoryResult.Error(
                code = response.code,
                message = response.message
            )
        }
        return RepositoryResult.Error(
            code = StatusCode.SERVER_ERROR,
            message = "Unknown error occurred"
        )
    }

    override suspend fun search(query: String, page: Int): RepositoryResult<List<University>> {
        val universityEntities = universityDbService.getAll(
            query = query,
            offset = page * Constant.DEFAULT_LIMIT,
            limit = Constant.DEFAULT_LIMIT
        )
        val universities = UniversityMapper.mapToRepositoryModelList(universityEntities)
        return RepositoryResult.Success(universities)
    }

    override suspend fun refreshDelay() {
        delay(1000)
    }
}
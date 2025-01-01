package com.fd.campusid.data.mapper

import com.fd.campusid.data.local.database.entity.UniversityEntity
import com.fd.campusid.data.remote.api.response.UniversitiesResponseItem
import com.fd.campusid.data.repository.university.model.University

object UniversityMapper {

    private fun mapToEntity(universityResponse: UniversitiesResponseItem): UniversityEntity {
        return UniversityEntity(
            webPages = universityResponse.webPages?.get(0) ?: "-",
            alphaTwoCode = universityResponse.alphaTwoCode ?: "-",
            stateProvince = universityResponse.stateProvince ?: "-",
            domains = universityResponse.domains?.get(0) ?: "-",
            country = universityResponse.country ?: "-",
            name = universityResponse.name ?: "-"
        )
    }

    suspend fun mapToEntityList(universityResponseList: List<UniversitiesResponseItem?>?): List<UniversityEntity> {
        val universityEntityList = mutableListOf<UniversityEntity>()
        universityResponseList?.forEach { university ->
            university?.let {
                universityEntityList.add(mapToEntity(it))
            }
        }
        return universityEntityList
    }

    private fun mapToRepositoryModel(universityEntity: UniversityEntity): University {
        return University(
            webPages = universityEntity.webPages ?: "-",
            alphaTwoCode = universityEntity.alphaTwoCode ?: "-",
            stateProvince = universityEntity.stateProvince ?: "-",
            domains = universityEntity.domains ?: "-",
            country = universityEntity.country ?: "-",
            name = universityEntity.name ?: "-"
        )
    }

    suspend fun mapToRepositoryModelList(universityEntityList: List<UniversityEntity>): List<University> {
        val universityList = mutableListOf<University>()
        universityEntityList.forEach {
            universityList.add(mapToRepositoryModel(it))
        }
        return universityList
    }
}
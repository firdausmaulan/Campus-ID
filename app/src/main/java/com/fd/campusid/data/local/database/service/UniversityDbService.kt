package com.fd.campusid.data.local.database.service

import com.fd.campusid.data.local.database.core.AppDatabase
import com.fd.campusid.data.local.database.entity.UniversityEntity

class UniversityDbService(private val appDatabase: AppDatabase) : IUniversityDbService {

    override suspend fun save(universities: List<UniversityEntity>) {
        appDatabase.universityDao.insert(universities)
    }

    override suspend fun getAll(query: String, offset: Int, limit: Int): List<UniversityEntity> {
        return appDatabase.universityDao.getUniversities(query, offset, limit)
    }

    override suspend fun countAll(): Int {
        return appDatabase.universityDao.countAll()
    }
}
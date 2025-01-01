package com.fd.campusid.data.local.database.service

import com.fd.campusid.data.local.database.entity.UniversityEntity

interface IUniversityDbService {
    suspend fun save(universities: List<UniversityEntity>)
    suspend fun getAll(query: String, offset: Int, limit: Int): List<UniversityEntity>
    suspend fun countAll(): Int
}
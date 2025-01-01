package com.fd.campusid.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fd.campusid.data.local.database.entity.UniversityEntity

@Dao
interface UniversityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(university: UniversityEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(universities: List<UniversityEntity>): List<Long>

    @Query(
        "SELECT * FROM t_universities " +
                "WHERE :query = '' " +
                "OR LOWER(name) LIKE '%' || LOWER(:query) || '%' " +
                "OR LOWER(domains) LIKE '%' || LOWER(:query) || '%' " +
                "ORDER BY name DESC " +
                "LIMIT :limit OFFSET :offset"
    )
    suspend fun getUniversities(query: String = "", offset: Int, limit: Int): List<UniversityEntity>

    @Query("SELECT COUNT(*) FROM t_universities")
    suspend fun countAll(): Int
}
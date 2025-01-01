package com.fd.campusid.data.local.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fd.campusid.data.local.database.dao.UniversityDao
import com.fd.campusid.data.local.database.entity.UniversityEntity

@Database(
    entities = [UniversityEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract val universityDao: UniversityDao


}
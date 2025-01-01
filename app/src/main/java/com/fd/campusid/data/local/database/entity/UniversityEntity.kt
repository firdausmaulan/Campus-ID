package com.fd.campusid.data.local.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "t_universities",
    indices = [Index(value = ["id"]), Index(value = ["name"], unique = true)]
)
data class UniversityEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var webPages: String? = null,
    var alphaTwoCode: String? = null,
    var stateProvince: String? = null,
    var domains: String? = null,
    var country: String? = null,
    var name: String? = null
)
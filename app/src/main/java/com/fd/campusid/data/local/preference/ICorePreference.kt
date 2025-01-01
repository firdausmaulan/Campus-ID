package com.fd.campusid.data.local.preference

interface ICorePreference {

    suspend fun save(key: String, value: String)
    suspend fun save(key: String, value: Int)
    suspend fun save(key: String, value: Boolean)
    suspend fun save(key: String, value: Float)
    suspend fun save(key: String, value: Long)

    suspend fun getString(key: String): String?
    suspend fun getInt(key: String): Int?
    suspend fun getBoolean(key: String): Boolean?
    suspend fun getFloat(key: String): Float?
    suspend fun getLong(key: String): Long?

    suspend fun remove(key: String)

    suspend fun clear()

}
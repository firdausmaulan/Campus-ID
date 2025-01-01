package com.fd.campusid.di

import com.fd.campusid.data.local.database.service.IUniversityDbService
import com.fd.campusid.data.local.preference.AppPreference
import com.fd.campusid.data.remote.api.service.UniversityApiService
import com.fd.campusid.data.repository.university.UniversityRepository
import com.fd.campusid.data.repository.university.UniversityRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUniversityRepository(
        universityApiService: UniversityApiService,
        universityDbService: IUniversityDbService,
        appPreference: AppPreference
    ): UniversityRepository {
        return UniversityRepositoryImpl(universityApiService, universityDbService, appPreference)
    }
}
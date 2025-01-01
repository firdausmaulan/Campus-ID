package com.fd.campusid.di

import com.fd.campusid.data.repository.university.UniversityRepository
import com.fd.campusid.ui.download.DownloadViewModel
import com.fd.campusid.ui.universities.UniversitiesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    @Singleton
    fun provideDownloadViewModel(universityRepository: UniversityRepository): DownloadViewModel {
        return DownloadViewModel(universityRepository)
    }

    @Provides
    @Singleton
    fun provideUniversitiesViewModel(universityRepository: UniversityRepository): UniversitiesViewModel {
        return UniversitiesViewModel(universityRepository)
    }
}
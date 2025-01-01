package com.fd.campusid.di

import android.content.Context
import androidx.room.Room
import com.fd.campusid.data.local.database.core.AppDatabase
import com.fd.campusid.data.local.database.service.IUniversityDbService
import com.fd.campusid.data.local.database.service.UniversityDbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUniversityDbService(db: AppDatabase): IUniversityDbService {
        return UniversityDbService(db)
    }
}
package com.fd.campusid.di

import android.content.Context
import com.fd.campusid.data.local.preference.AppPreference
import com.fd.campusid.data.local.preference.CorePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Provides
    @Singleton
    fun provideCorePreference(@ApplicationContext context: Context): CorePreference {
        return CorePreference(context)
    }

    @Provides
    @Singleton
    fun provideAppPreference(corePreference: CorePreference): AppPreference {
        return AppPreference(corePreference)
    }
}
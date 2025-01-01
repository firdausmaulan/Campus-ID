package com.fd.campusid.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fd.campusid.data.remote.api.core.AppHttpClient
import com.fd.campusid.data.remote.api.core.IAppHttpClient
import com.fd.campusid.data.remote.api.service.UniversityApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        @ApplicationContext context: Context
    ): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                preconfigured = OkHttpClient.Builder()
                    .addInterceptor(chuckerInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    // Configure to trust all certificates (be careful with this in production!)
                    .hostnameVerifier { _, _ -> true }
                    .build()
            }

            // Configure Ktor client
            // Don't throw on non-2xx responses
            expectSuccess = false

            // Handle different content types
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    @Provides
    @Singleton
    fun provideAppHttpClient(httpClient: HttpClient): IAppHttpClient {
        return AppHttpClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideUniversityApiService(appHttpClient: IAppHttpClient): UniversityApiService {
        return UniversityApiService(appHttpClient)
    }
}

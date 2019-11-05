package com.example.blogappmvi.di.auth

import com.example.blogappmvi.api.auth.OpenApiAuthService
import com.example.blogappmvi.persistence.AccountPropertiesDao
import com.example.blogappmvi.persistence.AuthTokenDao
import com.example.blogappmvi.repository.auth.AuthRepository
import com.example.blogappmvi.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule{

    @AuthScope
    @Provides
    fun provideFakeApiService(retrofit: Retrofit): OpenApiAuthService =
        retrofit.create(OpenApiAuthService::class.java)

    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: SessionManager,
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        openApiAuthService: OpenApiAuthService
    ): AuthRepository {
        return AuthRepository(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager
        )
    }

}
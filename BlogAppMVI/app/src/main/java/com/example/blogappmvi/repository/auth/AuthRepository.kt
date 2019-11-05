package com.example.blogappmvi.repository.auth

import com.example.blogappmvi.api.auth.OpenApiAuthService
import com.example.blogappmvi.persistence.AccountPropertiesDao
import com.example.blogappmvi.persistence.AuthTokenDao
import com.example.blogappmvi.session.SessionManager

class AuthRepository constructor(
    private val authTokenDao: AuthTokenDao,
    private val accountPropertiesDao: AccountPropertiesDao,
    private val openApiAuthService: OpenApiAuthService,
    sessionManager: SessionManager
) {


}
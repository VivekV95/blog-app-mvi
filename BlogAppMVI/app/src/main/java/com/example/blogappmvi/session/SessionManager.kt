package com.example.blogappmvi.session

import android.app.Application
import com.example.blogappmvi.persistence.AuthTokenDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor
    (val authTokenDao: AuthTokenDao, application: Application) {


}
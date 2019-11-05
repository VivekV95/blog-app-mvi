package com.example.blogappmvi.session

import android.app.Application
import com.example.blogappmvi.persistence.AuthTokenDao

class SessionManager constructor
    (val authTokenDao: AuthTokenDao, application: Application) {


}
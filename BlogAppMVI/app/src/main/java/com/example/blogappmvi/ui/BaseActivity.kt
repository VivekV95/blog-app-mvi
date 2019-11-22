package com.example.blogappmvi.ui

import com.example.blogappmvi.session.SessionManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity: DaggerAppCompatActivity() {

    val TAG = "AppDebug"

    @Inject
    lateinit var sessionManager: SessionManager


}
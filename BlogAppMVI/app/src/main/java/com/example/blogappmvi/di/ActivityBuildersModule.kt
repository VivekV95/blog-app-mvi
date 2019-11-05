package com.example.blogappmvi.di

import com.example.blogappmvi.di.auth.AuthFragmentBuildersModule
import com.example.blogappmvi.di.auth.AuthModule
import com.example.blogappmvi.di.auth.AuthScope
import com.example.blogappmvi.di.auth.AuthViewModelModule
import com.example.blogappmvi.ui.auth.AuthActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

}
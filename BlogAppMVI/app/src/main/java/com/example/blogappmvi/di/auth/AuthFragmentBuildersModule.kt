package com.example.blogappmvi.di.auth

import com.example.blogappmvi.ui.auth.ForgotPasswordFragment
import com.example.blogappmvi.ui.auth.LauncherFragment
import com.example.blogappmvi.ui.auth.LoginFragment
import com.example.blogappmvi.ui.auth.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeLauncherFragment(): LauncherFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    abstract fun contributeForgotPasswordFragment(): ForgotPasswordFragment

}
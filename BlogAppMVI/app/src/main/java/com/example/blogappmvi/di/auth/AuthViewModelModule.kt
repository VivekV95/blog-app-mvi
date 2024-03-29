package com.example.blogappmvi.di.auth

import androidx.lifecycle.ViewModel
import com.example.blogappmvi.di.ViewModelKey
import com.example.blogappmvi.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

}
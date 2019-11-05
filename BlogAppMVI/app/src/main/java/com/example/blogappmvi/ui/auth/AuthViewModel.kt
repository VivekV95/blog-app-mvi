package com.example.blogappmvi.ui.auth

import androidx.lifecycle.ViewModel
import com.example.blogappmvi.repository.auth.AuthRepository
import javax.inject.Inject

class AuthViewModel @Inject constructor
    (private val authRepository: AuthRepository) : ViewModel() {


}
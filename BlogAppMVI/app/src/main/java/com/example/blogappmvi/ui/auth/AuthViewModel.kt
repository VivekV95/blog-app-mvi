package com.example.blogappmvi.ui.auth

import androidx.lifecycle.LiveData
import com.example.blogappmvi.model.AuthToken
import com.example.blogappmvi.repository.auth.AuthRepository
import com.example.blogappmvi.ui.BaseViewModel
import com.example.blogappmvi.ui.DataState
import com.example.blogappmvi.ui.auth.state.AuthStateEvent
import com.example.blogappmvi.ui.auth.state.AuthStateEvent.*
import com.example.blogappmvi.ui.auth.state.AuthViewState
import com.example.blogappmvi.ui.auth.state.LoginFields
import com.example.blogappmvi.ui.auth.state.RegistrationFields
import com.example.blogappmvi.util.AbsentLiveData
import javax.inject.Inject

class AuthViewModel @Inject constructor
    (private val authRepository: AuthRepository) : BaseViewModel<AuthStateEvent, AuthViewState>() {

    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }

    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {
        when (stateEvent) {
            is LoginAttemptEvent -> {
                return authRepository.attemptLogin(
                    stateEvent.email,
                    stateEvent.password
                )
            }
            is RegisterAttemptEvent -> {
                return authRepository.attemptRegistration(
                    stateEvent.email,
                    stateEvent.username,
                    stateEvent.password,
                    stateEvent.confirm_password
                )
            }
            is CheckPreviousAuthEvent -> {
                return authRepository.checkPreviousAuthUser()
            }
        }
    }

    fun setRegistrationFields(registrationFields: RegistrationFields) {
        val update = getCurrentViewStateOrNew()
        if (update.registrationFields == registrationFields) {
            return
        }
        update.registrationFields = registrationFields
        _viewState.value = update
    }

    fun setLoginFields(loginFields: LoginFields) {
        val update = getCurrentViewStateOrNew()
        if (update.loginFields == loginFields) {
            return
        }
        update.loginFields = loginFields
        _viewState.value = update
    }

    fun setAuthToken(authToken: AuthToken) {
        val update = getCurrentViewStateOrNew()
        if (update.authToken == authToken) {
            return
        }
        update.authToken = authToken
        _viewState.value = update
    }

    fun cancelActiveJobs() {
        authRepository.cancelActiveJobs()
    }

    override fun onCleared() {
        cancelActiveJobs()
    }
}
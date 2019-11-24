package com.example.blogappmvi.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.blogappmvi.api.auth.OpenApiAuthService
import com.example.blogappmvi.model.AuthToken
import com.example.blogappmvi.model.network_responses.LoginResponse
import com.example.blogappmvi.model.network_responses.RegistrationResponse
import com.example.blogappmvi.persistence.AccountPropertiesDao
import com.example.blogappmvi.persistence.AuthTokenDao
import com.example.blogappmvi.repository.NetworkBoundResource
import com.example.blogappmvi.session.SessionManager
import com.example.blogappmvi.ui.Data
import com.example.blogappmvi.ui.DataState
import com.example.blogappmvi.ui.Response
import com.example.blogappmvi.ui.ResponseType
import com.example.blogappmvi.ui.auth.state.AuthViewState
import com.example.blogappmvi.ui.auth.state.LoginFields
import com.example.blogappmvi.ui.auth.state.RegistrationFields
import com.example.blogappmvi.util.*
import kotlinx.coroutines.Job

class AuthRepository constructor(
    private val authTokenDao: AuthTokenDao,
    private val accountPropertiesDao: AccountPropertiesDao,
    private val openApiAuthService: OpenApiAuthService,
    private val sessionManager: SessionManager
) {

    private var repositoryJob: Job? = null

    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthViewState>> {
        val loginFieldErrors = LoginFields(email, password).isValidForLogin()
        if (!loginFieldErrors.equals(LoginFields.LoginError.none())) {
            return returnErrorResponse(loginFieldErrors, ResponseType.Dialog())
        }

        return object: NetworkBoundResource<LoginResponse, AuthViewState>(
            sessionManager.isConnectedToTheInternet()
        ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<LoginResponse>) {
                if (response.body.response == GENERIC_AUTH_ERROR) {
                    return onErrorReturn(response.body.errorMessage, true, shouldUseToast = false)
                }
                onCompleteJob(
                    DataState.data(
                        data = AuthViewState(
                            authToken = AuthToken(response.body.pk, response.body.token)
                        )
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<LoginResponse>> {
                return openApiAuthService.login(email, password)
            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job
            }

        }.asLiveData()
    }

    fun attempRegistration(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<DataState<AuthViewState>> {

        val registrationFieldErrors =
            RegistrationFields(email, username, password, confirmPassword).isValidForRegistration()
        if (!registrationFieldErrors.equals(RegistrationFields.RegistrationError.none())) {
            return returnErrorResponse(registrationFieldErrors, ResponseType.Dialog())
        }

        return object: NetworkBoundResource<RegistrationResponse, AuthViewState>(
            sessionManager.isConnectedToTheInternet()
        ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<RegistrationResponse>) {
                if (response.body.response.equals(GENERIC_AUTH_ERROR)) {
                    return onErrorReturn(response.body.errorMessage, true, shouldUseToast = false)
                }

                onCompleteJob(
                    DataState.data(
                        data = AuthViewState(
                            authToken = AuthToken(response.body.pk, response.body.token)
                        )
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<RegistrationResponse>> {
                return openApiAuthService.register(email, username, password, confirmPassword)
            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job            }
        }.asLiveData()
    }

    private fun returnErrorResponse(errorMessage: String, responseType: ResponseType): LiveData<DataState<AuthViewState>> {
        return object: LiveData<DataState<AuthViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.error(
                    Response(
                        errorMessage,
                        responseType
                    )
                )
            }
        }
    }

    fun cancelActiveJobs() {
        repositoryJob?.cancel()
    }

}
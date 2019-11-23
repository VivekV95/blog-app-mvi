package com.example.blogappmvi.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.blogappmvi.api.auth.OpenApiAuthService
import com.example.blogappmvi.model.AuthToken
import com.example.blogappmvi.persistence.AccountPropertiesDao
import com.example.blogappmvi.persistence.AuthTokenDao
import com.example.blogappmvi.session.SessionManager
import com.example.blogappmvi.ui.DataState
import com.example.blogappmvi.ui.Response
import com.example.blogappmvi.ui.ResponseType
import com.example.blogappmvi.ui.auth.state.AuthViewState
import com.example.blogappmvi.util.ApiEmptyResponse
import com.example.blogappmvi.util.ApiErrorResponse
import com.example.blogappmvi.util.ApiSuccessResponse
import com.example.blogappmvi.util.ERROR_UNKNOWN

class AuthRepository constructor(
    private val authTokenDao: AuthTokenDao,
    private val accountPropertiesDao: AccountPropertiesDao,
    private val openApiAuthService: OpenApiAuthService,
    sessionManager: SessionManager
) {

    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthViewState>> {
        return openApiAuthService.login(email, password)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>() {
                    override fun onActive() {
                        super.onActive()

                        when (response) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = AuthViewState(
                                        authToken = AuthToken(
                                            response.body.pk,
                                            response.body.token
                                        )
                                    ),
                                    response = null
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                        }
                    }
                }
            }
    }

    fun attempRegistration(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<DataState<AuthViewState>> {
        return openApiAuthService.register(email, username, password, confirmPassword)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>() {
                    override fun onActive() {
                        super.onActive()

                        when (response) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = AuthViewState(
                                        authToken = AuthToken(
                                            response.body.pk,
                                            response.body.token
                                        )
                                    ),
                                    response = null
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                        }
                    }
                }
            }
    }
}
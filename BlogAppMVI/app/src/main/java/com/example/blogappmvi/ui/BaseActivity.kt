package com.example.blogappmvi.ui

import com.example.blogappmvi.session.SessionManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity: DaggerAppCompatActivity(), DataStateChangedListener {

    val TAG = "AppDebug"

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onDataStateChanged(dataState: DataState<*>?) {
        dataState?.let {
            GlobalScope.launch (Main) {
                displayProgressBar(it.loading.isLoading)

                it.error?.let { errorEvent ->
                    handleStateError(errorEvent)
                }

                it.data?.let {
                    it.response?.let { responseEvent ->
                        handleStateResponse(responseEvent)
                    }
                }
            }
        }
    }

    private fun handleStateError(errorEvent: Event<StateError>) {
        errorEvent.getContentIfNotHandled()?.let {
            when(it.response.responseType) {
                is ResponseType.Toast -> {
                    it.response.message?.let { message -> {
                        displayToast(message)
                    } }
                }
                is ResponseType.Dialog -> {
                    it.response.message?.let { message ->
                        displayErrorDialog(message)
                    }
                }
                is ResponseType.None -> {

                }
            }
        }
    }

    private fun handleStateResponse(event: Event<Response>) {
        event.getContentIfNotHandled()?.let {
            when(it.responseType) {
                is ResponseType.Toast -> {
                    it.message?.let { message -> {
                        displayToast(message)
                    } }
                }
                is ResponseType.Dialog -> {
                    it.message?.let { message ->
                        displaySuccessDialog(message)
                    }
                }
                is ResponseType.None -> {

                }
            }
        }
    }

    abstract fun displayProgressBar(bool: Boolean)
}
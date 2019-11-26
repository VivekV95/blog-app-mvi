package com.example.blogappmvi.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.blogappmvi.ui.DataStateChangedListener
import com.example.blogappmvi.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import java.lang.ClassCastException
import java.lang.Exception
import javax.inject.Inject

abstract class BaseAuthFragment: DaggerFragment() {

    val TAG = "AppDebug"

    lateinit var stateChangeListener: DataStateChangedListener

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, viewModelProviderFactory).get(AuthViewModel::class.java)
        }?: throw Exception("Plssssssss")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            stateChangeListener = context as DataStateChangedListener
        } catch (e: ClassCastException) {

        }
    }
}
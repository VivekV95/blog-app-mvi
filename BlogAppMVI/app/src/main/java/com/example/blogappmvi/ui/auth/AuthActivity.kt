package com.example.blogappmvi.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.blogappmvi.R
import com.example.blogappmvi.ui.BaseActivity
import com.example.blogappmvi.ui.ResponseType
import com.example.blogappmvi.ui.main.MainActivity
import com.example.blogappmvi.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        viewModel.cancelActiveJobs()
    }

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        findNavController(R.id.auth_nav_host_fragment).addOnDestinationChangedListener(this)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            onDataStateChanged(dataState)
            dataState.data?.let { data ->
                data.data?.let { event ->
                    event.getContentIfNotHandled()?.let {
                        it.authToken?.let { token ->
                            viewModel.setAuthToken(token)
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(this, Observer { authViewState ->
            authViewState.authToken?.let { authToken ->
                sessionManager.login(authToken)
            }
        })
        sessionManager.cachedToken.observe(this, Observer { authToken ->
            if (authToken != null && authToken.account_pk != -1 && authToken.token != null) {
                navMainActivity()
            }
        })
    }

    private fun navMainActivity() {
        Intent(this, MainActivity::class.java)
            .apply {
                startActivity(this)
            }
        finish()
    }

    override fun displayProgressBar(bool: Boolean) {
        if (bool) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }
}

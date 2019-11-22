package com.example.blogappmvi.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.blogappmvi.R
import com.example.blogappmvi.ui.BaseActivity
import com.example.blogappmvi.ui.main.MainActivity
import com.example.blogappmvi.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class AuthActivity : BaseActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        subscribeObservers()
    }

    fun subscribeObservers() {
        viewModel.viewState.observe(this, Observer { authViewState ->
            authViewState.authToken?.let {  authToken ->
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
}

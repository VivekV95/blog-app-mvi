package com.example.blogappmvi.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.Observer
import com.example.blogappmvi.R
import com.example.blogappmvi.ui.BaseActivity
import com.example.blogappmvi.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
    }

    fun subscribeObservers() {
        sessionManager.cachedToken.observe(this, Observer { authToken ->
            if (authToken == null || authToken.account_pk == -1 || authToken.token == null) {
            navAuthActivity()
        }
        })
    }

    private fun navAuthActivity() {
        Intent(this, AuthActivity::class.java)
            .apply {
                startActivity(this)
            }
        finish()
    }
}

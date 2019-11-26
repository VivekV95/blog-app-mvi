package com.example.blogappmvi.session

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.blogappmvi.model.AuthToken
import com.example.blogappmvi.persistence.AuthTokenDao
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor
    (private val authTokenDao: AuthTokenDao, private val application: Application) {

    private val _cachedToken = MutableLiveData<AuthToken>()

    val cachedToken: LiveData<AuthToken>
        get() = _cachedToken

    fun login(newValue: AuthToken) {
        setValue(newValue)
    }

    fun logout() {

        GlobalScope.launch(IO) {
            var errorMessage: String? = null
            try {
                cachedToken.value?.account_pk?.let {
                    authTokenDao.nullifyToken(it)
                }
            } catch (e: CancellationException) {
                errorMessage = e.message
            } catch (e: Exception) {
                errorMessage = errorMessage + "\n" + e.message
            } finally {
                errorMessage?.let {

                }
                setValue(null)
            }
        }
    }


    fun setValue(newValue: AuthToken?) {
        GlobalScope.launch(Main) {
            if (_cachedToken.value != newValue) {
                _cachedToken.value = newValue
            }
        }
    }

    fun isConnectedToTheInternet(): Boolean {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            if (Build.VERSION.SDK_INT < 23) {
                val networkInfo = connectivityManager.activeNetworkInfo
                if (networkInfo != null) {
                    return (networkInfo.isConnected &&
                            (networkInfo.type == ConnectivityManager.TYPE_WIFI
                                    || networkInfo.type == ConnectivityManager.TYPE_MOBILE))
                }
            } else {
                val network = connectivityManager.activeNetwork
                if (network != null) {
                    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                    networkCapabilities?.let {
                        return (networkCapabilities.hasTransport(
                            NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    }
                }
            }
        } catch (e: Exception) {

        }
        return false
    }

}
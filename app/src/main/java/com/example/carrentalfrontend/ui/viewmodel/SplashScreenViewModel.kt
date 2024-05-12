package com.example.carrentalfrontend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalfrontend.core.repositories.ProtoRepository
import com.example.carrentalfrontend.domain.model.data.LoggedUserData
import com.example.carrentalfrontend.util.Event
import com.example.carrentalfrontend.util.isEmpty
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val protoRepository: ProtoRepository) : ViewModel() {
    private val _splashScreenTimer = MutableLiveData<Event<LoggedUserData>>(Event.loading())
    val splashScreenTimer = _splashScreenTimer as LiveData<Event<LoggedUserData>>

    private suspend fun isUserLoggedIn(): LoggedUserData {
        val credentials = protoRepository.readUserCredentials()
        val isLogged = credentials?.isEmpty()?.not() ?: false
        val isAdmin = credentials?.isAdmin
        return if (isLogged) {
            LoggedUserData(isLogged, isAdmin!!)
        } else {
            LoggedUserData(isLogged, false)
        }
    }

    fun checkUserLoggedIn() {
        viewModelScope.launch {
            val delayJob = launch {
                delay(1500)
            }
            val readUserCredentialsJob = async {
                isUserLoggedIn()
            }
            delayJob.join()
            val isUserLogged = readUserCredentialsJob.await()
            _splashScreenTimer.postValue(Event.success(isUserLogged))
        }
    }
}

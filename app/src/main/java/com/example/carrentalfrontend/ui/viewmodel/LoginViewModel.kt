package com.example.carrentalfrontend.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalfrontend.core.repositories.ProtoRepository
import com.example.carrentalfrontend.core.repositories.UserRepository
import com.example.carrentalfrontend.domain.model.dto.LoginDto
import com.example.carrentalfrontend.domain.model.entity.User
import com.example.carrentalfrontend.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val userRepository: UserRepository,
    private val protoRepository: ProtoRepository
) : ViewModel() {

    private val _userCredentials = MutableLiveData<Event<User>>()
    val userCredentials = _userCredentials

    fun login(loginDto: LoginDto) {
        Log.e(TAG, "username: ${loginDto.username} password: ${loginDto.password}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _userCredentials.postValue(Event.loading())
                try {
                    val response = userRepository.login(loginDto = loginDto)
                    if (response.isSuccessful && response.body() != null) {
                        _userCredentials.postValue(Event.success(response.body()!!))
                    } else {
                        response.errorBody()?.string()?.let {
                            // TODO: Ensure to display the right error message for the Toast
                            _userCredentials.postValue(Event.failure(it))
                        }
                    }
                } catch (e: Exception) {
                    throw Exception(e.toString())
                }
            }
        }
    }

    fun updateValue(user: User) = viewModelScope.launch(Dispatchers.IO) {
        protoRepository.updateUserCredentials(user)
    }

    private companion object {
        const val TAG = "TOBA"
    }
}
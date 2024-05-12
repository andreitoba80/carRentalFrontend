package com.example.carrentalfrontend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalfrontend.core.repositories.UserRepository
import com.example.carrentalfrontend.domain.model.dto.RegisterUserDto
import com.example.carrentalfrontend.util.logDebugError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registerSuccessfully = MutableLiveData<Boolean>()
    val registerSuccessfully: LiveData<Boolean> = _registerSuccessfully

    fun register(registerUserDto: RegisterUserDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.register(registerUserDto = registerUserDto)
                if (response.isSuccessful && response.body() != null) {
                    _registerSuccessfully.postValue(true)
                    logDebugError("Response: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    _registerSuccessfully.postValue(false)
                    logDebugError("RegisterError: Error during registration: $errorBody")
                }
            } catch (e: Exception) {
                throw Exception(e.toString())
            }
        }
    }
}
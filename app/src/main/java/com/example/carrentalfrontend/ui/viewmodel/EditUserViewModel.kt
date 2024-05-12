package com.example.carrentalfrontend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalfrontend.core.repositories.UserRepository
import com.example.carrentalfrontend.domain.model.dto.UpdateUserDto
import com.example.carrentalfrontend.util.logDebugError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditUserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _editSuccess = MutableLiveData<Boolean>()
    val editSuccess: LiveData<Boolean> = _editSuccess

    fun updateUser(userId: Long, user: UpdateUserDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.updateUser(userId, user)
                if (response.isSuccessful && response.body() != null) {
                    _editSuccess.postValue(true)
                    logDebugError("Response: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()?.toString()
                    _editSuccess.postValue(false)
                    logDebugError("User Update Error: $errorBody")
                }
            } catch (e: Exception) {
                throw Exception(e.toString())
            }
        }
    }
}
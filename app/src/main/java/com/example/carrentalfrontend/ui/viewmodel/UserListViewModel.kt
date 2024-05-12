package com.example.carrentalfrontend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalfrontend.core.repositories.UserRepository
import com.example.carrentalfrontend.domain.model.entity.User
import com.example.carrentalfrontend.util.logDebugError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userList = MutableLiveData<ArrayList<User>>()
    val userList: LiveData<ArrayList<User>> = _userList

    private val _deleteSuccess = MutableLiveData<Boolean>()
    val deleteSuccess: LiveData<Boolean> = _deleteSuccess

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = userRepository.getAllUsers()
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    _userList.postValue(response.body())
                    logDebugError(response.body().toString())
                } else {
                    val errorBody = response.errorBody().toString()
                    logDebugError(errorBody)
                }
            } catch (e: Exception) {
                throw Exception(e.message)
            }
        }
    }

    fun deleteUser(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.deleteUser(userId)
                if (response.isSuccessful && response.body() != null) {
                    _deleteSuccess.postValue(true)
                    logDebugError("DeleteUser Response: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()
                    _deleteSuccess.postValue(false)
                    logDebugError("DeleteUser Error Response: $errorBody")
                }
            } catch (e: Exception) {
                throw Exception(e.toString())
            }
        }
    }
}
package com.example.carrentalfrontend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalfrontend.core.repositories.ProtoRepository
import kotlinx.coroutines.launch

class AdminScreenViewModel(private val protoRepository: ProtoRepository) : ViewModel() {
    private val _userFullName = MutableLiveData<String>()
    val userFullName = _userFullName as LiveData<String>

    private suspend fun getFullName(): String {
        return protoRepository.readUserCredentials()?.fullName ?: ""
    }

    fun getUserFullName() =
        viewModelScope.launch {
            _userFullName.postValue(getFullName())
        }
}
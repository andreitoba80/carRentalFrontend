package com.example.carrentalfrontend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalfrontend.core.repositories.CarRepository
import com.example.carrentalfrontend.core.repositories.ProtoRepository
import com.example.carrentalfrontend.domain.model.entity.Car
import com.example.carrentalfrontend.util.logDebugError
import kotlinx.coroutines.launch

class HomeFragmentViewModel(
    private val protoRepository: ProtoRepository,
    private val carRepository: CarRepository
) : ViewModel() {

    private val _carList = MutableLiveData<ArrayList<Car>>()
    val carList: LiveData<ArrayList<Car>> = _carList

    fun deleteUserCredentials() {
        viewModelScope.launch {
            protoRepository.deleteUserCredentials()
        }
    }

    fun fetchCars() {
        viewModelScope.launch {
            try {
                val response = carRepository.getAllCars()
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    _carList.postValue(response.body())
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
}
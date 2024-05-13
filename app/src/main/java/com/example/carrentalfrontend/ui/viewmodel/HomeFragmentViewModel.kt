package com.example.carrentalfrontend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalfrontend.core.repositories.CarBrandsRepository
import com.example.carrentalfrontend.core.repositories.CarRepository
import com.example.carrentalfrontend.core.repositories.ProtoRepository
import com.example.carrentalfrontend.domain.model.data.CarBrand
import com.example.carrentalfrontend.domain.model.entity.Car
import com.example.carrentalfrontend.util.logDebugError
import kotlinx.coroutines.launch

class HomeFragmentViewModel(
    private val protoRepository: ProtoRepository,
    private val carRepository: CarRepository,
    private val carBrandsRepository: CarBrandsRepository
) : ViewModel() {

    private val _carList = MutableLiveData<ArrayList<Car>>()
    val carList: LiveData<ArrayList<Car>> = _carList

    private val _carBrandList = MutableLiveData<ArrayList<CarBrand>>()
    val carBrandList: LiveData<ArrayList<CarBrand>> = _carBrandList

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

    fun fetchCarBrands() {
        viewModelScope.launch {
            try {
                val response = carBrandsRepository.getAllCarBrands()
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    logDebugError("ManageCarBrandsViewModel response.body = ${response.body()}")
                    _carBrandList.postValue(response.body())
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
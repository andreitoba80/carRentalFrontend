package com.example.carrentalfrontend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalfrontend.core.repositories.CarBrandsRepository
import com.example.carrentalfrontend.domain.model.data.CarBrands
import com.example.carrentalfrontend.util.logDebugError
import kotlinx.coroutines.launch

class ManageCarBrandsViewModel(
    private val carBrandsRepository: CarBrandsRepository
) : ViewModel() {

    private val _carBrandsList = MutableLiveData<ArrayList<CarBrands>>()
    val carBrandsList: LiveData<ArrayList<CarBrands>> = _carBrandsList

    fun fetchCarBrands() {
        viewModelScope.launch {
            try {
                val response = carBrandsRepository.getAllCarBrands()
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    _carBrandsList.postValue(response.body())
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
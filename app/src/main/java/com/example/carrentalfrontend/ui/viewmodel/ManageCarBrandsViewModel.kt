package com.example.carrentalfrontend.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalfrontend.core.repositories.CarBrandsRepository
import com.example.carrentalfrontend.domain.model.data.CarBrand
import com.example.carrentalfrontend.domain.model.dto.AddCarBrandDto
import com.example.carrentalfrontend.util.logDebugError
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class ManageCarBrandsViewModel(
    private val carBrandsRepository: CarBrandsRepository
) : ViewModel() {

    private val _carBrandList = MutableLiveData<ArrayList<CarBrand>>()
    val carBrandList: LiveData<ArrayList<CarBrand>> = _carBrandList

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

    fun getFileFromUri(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "upload_image.jpg")  // Temporary file
        val outputStream = FileOutputStream(file)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    fun addCarBrand(brandName: String, imageUri: Uri) {
        val addCarBrandDto = AddCarBrandDto(brandName)  // Assuming your DTO just needs a brand name

        viewModelScope.launch {
            try {
                val response = carBrandsRepository.addCarBrand(addCarBrandDto, imageUri)
                if (response.isSuccessful) {
                    logDebugError("SUCCESS")
                    // Handle the successful addition of a new car brand
                    // You can update LiveData here or handle UI interaction
                } else {
                    logDebugError("ERROR = ${response.errorBody()?.string().toString()}")
                    // Handle API request errors
                    // Could use a LiveData to propagate error messages back to the UI
                    val errorBody = response.errorBody()?.string() ?: "Unknown error occurred"
                    // Log error or inform the user via UI
                }
            } catch (e: Exception) {
                // Handle exceptions, possibly network errors or serialization issues
                // Could use a LiveData to propagate error messages back to the UI
                // Log error or inform the user via UI
            }
        }
    }
}
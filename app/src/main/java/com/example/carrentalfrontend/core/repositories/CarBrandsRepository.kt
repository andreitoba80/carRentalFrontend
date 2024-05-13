package com.example.carrentalfrontend.core.repositories

import android.net.Uri
import com.example.carrentalfrontend.domain.model.data.CarBrand
import com.example.carrentalfrontend.domain.model.dto.AddCarBrandDto
import retrofit2.Response

interface CarBrandsRepository {
    suspend fun getAllCarBrands(): Response<ArrayList<CarBrand>>

    suspend fun addCarBrand(addCarBrandDto: AddCarBrandDto, fileUri: Uri): Response<String>
}
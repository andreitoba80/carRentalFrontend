package com.example.carrentalfrontend.core.repositories

import com.example.carrentalfrontend.domain.model.data.CarBrands
import retrofit2.Response

interface CarBrandsRepository {
    suspend fun getAllCarBrands(): Response<ArrayList<CarBrands>>
}
package com.example.carrentalfrontend.core.repositories

import com.example.carrentalfrontend.domain.model.data.CarBrands
import retrofit2.Response

class CarBrandsRepositoryImpl(
    private val carBrandsApi: CarBrandsApi
) : CarBrandsRepository {
    override suspend fun getAllCarBrands(): Response<ArrayList<CarBrands>> {
        return carBrandsApi.getAllCarBrands()
    }
}
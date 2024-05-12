package com.example.carrentalfrontend.core.repositories

import com.example.carrentalfrontend.domain.model.data.CarBrands
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CarBrandsApi {
    @Headers("Content-Type: application/json")
    @GET("carBrands/getAllCarBrands")
    suspend fun getAllCarBrands(): Response<ArrayList<CarBrands>>
}
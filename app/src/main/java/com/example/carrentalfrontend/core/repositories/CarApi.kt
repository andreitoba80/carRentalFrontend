package com.example.carrentalfrontend.core.repositories

import com.example.carrentalfrontend.domain.model.entity.Car
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CarApi {
    @Headers("Content-Type: application/json")
    @GET("car/getAllCars")
    suspend fun getAllCars(): Response<ArrayList<Car>>
}
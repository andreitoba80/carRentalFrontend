package com.example.carrentalfrontend.core.repositories

import com.example.carrentalfrontend.domain.model.entity.Car
import retrofit2.Response

interface CarRepository {
    suspend fun getAllCars(): Response<ArrayList<Car>>
}
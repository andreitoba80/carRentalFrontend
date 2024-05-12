package com.example.carrentalfrontend.core.repositories

import com.example.carrentalfrontend.domain.model.entity.Car
import retrofit2.Response

class CarRepositoryImpl(private val carApi: CarApi) : CarRepository {
    override suspend fun getAllCars(): Response<ArrayList<Car>> {
        return carApi.getAllCars()
    }
}
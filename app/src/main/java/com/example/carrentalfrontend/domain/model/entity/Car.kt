package com.example.carrentalfrontend.domain.model.entity


data class Car(
    private val id: Long,
    private val model: String,
    private val year: Int,
    private val carSize: String, // Sedan, Suv, etc.
    private val manualTransmission: Boolean,
    private val fuelType: Int, // 0 -> Petrol, 1 -> Diesel, 2 -> Hybrid
    private val seatsCapacity: Int,
    private val pricePerDay: Float,
    private val rating: Float,
    private val status: Int, // -1 -> Maintenance, 0 -> Booked, 1 -> Available
    private val imageUrl: String,
)
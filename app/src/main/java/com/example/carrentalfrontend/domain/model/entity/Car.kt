package com.example.carrentalfrontend.domain.model.entity


data class Car(
    private val id: Long,
    private val manufacturer: String,
    private val model: String,
    private val year: Int,
    /**
     * Sedan, Suv, etc.
     */
    private val carSize: String,
    private val transmissionType: String,
    /**
     * 0 -> Petrol
     * 1 -> Diesel
     * 2 -> Hybrid
     */
    private val fuelType: Int,
    private val seatsCapacity: Int,
    private val pricePerDay: Float,
    private val rating: Float,
    /**
     * status field can have the following values:
     * -1 -> Maintenance
     * 0 -> Booked
     * 1 -> Available
     */
    private val status: Int,
    private val imageUrl: String
)
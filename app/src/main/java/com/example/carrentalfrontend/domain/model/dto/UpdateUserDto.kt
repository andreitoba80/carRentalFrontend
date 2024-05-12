package com.example.carrentalfrontend.domain.model.dto

data class UpdateUserDto(
    val username: String,
    val password: String,
    val email: String,
    val fullName: String,
    val isAdmin: Boolean
)
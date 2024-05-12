package com.example.carrentalfrontend.domain.model.dto

data class RegisterUserDto(
    val username: String,
    val password: String,
    val email: String,
    val fullName: String
)
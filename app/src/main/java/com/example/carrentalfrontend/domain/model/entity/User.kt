package com.example.carrentalfrontend.domain.model.entity

import java.io.Serializable

data class User(
    val id: Long,
    val username: String,
//    val password: String,
    val email: String,
    val fullName: String,
    val isAdmin: Boolean
) : Serializable
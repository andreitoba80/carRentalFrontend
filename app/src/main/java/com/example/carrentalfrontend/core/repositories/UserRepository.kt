package com.example.carrentalfrontend.core.repositories

import com.example.carrentalfrontend.domain.model.dto.LoginDto
import com.example.carrentalfrontend.domain.model.dto.RegisterUserDto
import com.example.carrentalfrontend.domain.model.dto.UpdateUserDto
import com.example.carrentalfrontend.domain.model.entity.User
import retrofit2.Response

interface UserRepository {
    suspend fun login(loginDto: LoginDto): Response<User?>

    suspend fun register(registerUserDto: RegisterUserDto): Response<String>

    suspend fun getAllUsers(): Response<ArrayList<User>>

    suspend fun updateUser(userId: Long, updateUserDto: UpdateUserDto): Response<String>

    suspend fun deleteUser(userId: Long): Response<String>
}
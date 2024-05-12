package com.example.carrentalfrontend.core.repositories

import com.example.carrentalfrontend.domain.model.dto.LoginDto
import com.example.carrentalfrontend.domain.model.dto.RegisterUserDto
import com.example.carrentalfrontend.domain.model.dto.UpdateUserDto
import com.example.carrentalfrontend.domain.model.entity.User
import retrofit2.Response

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {
    override suspend fun login(loginDto: LoginDto): Response<User?> {
        return userApi.login(loginDto = loginDto)
    }

    override suspend fun register(registerUserDto: RegisterUserDto): Response<String> {
        return userApi.register(registerUserDto = registerUserDto)
    }

    override suspend fun getAllUsers(): Response<ArrayList<User>> {
        return userApi.getAllUsers()
    }

    override suspend fun updateUser(userId: Long, updateUserDto: UpdateUserDto): Response<String> {
        return userApi.updateUser(userId, updateUserDto)
    }

    override suspend fun deleteUser(userId: Long): Response<String> {
        return userApi.deleteUser(userId)
    }
}
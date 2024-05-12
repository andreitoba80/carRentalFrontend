package com.example.carrentalfrontend.core.repositories

import com.example.carrentalfrontend.domain.model.dto.LoginDto
import com.example.carrentalfrontend.domain.model.dto.RegisterUserDto
import com.example.carrentalfrontend.domain.model.dto.UpdateUserDto
import com.example.carrentalfrontend.domain.model.entity.User
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @Headers("Content-Type: application/json")
    @POST("user/login")
    suspend fun login(
        @Body loginDto: LoginDto
    ): Response<User?>

    @Headers("Content-Type: application/json")
    @POST("user/createUser")
    suspend fun register(
        @Body registerUserDto: RegisterUserDto
    ): Response<String>

    @Headers("Content-Type: application/json")
    @GET("user/getAllUsers")
    suspend fun getAllUsers(): Response<ArrayList<User>>

    @Headers("Content-Type: application/json")
    @PUT("user/updateUser/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: Long,
        @Body updateUserDto: UpdateUserDto
    ): Response<String>

    @Headers("Content-Type: application/json")
    @DELETE("user/deleteUserById/{userId}")
    suspend fun deleteUser(
        @Path("userId") userId: Long
    ): Response<String>
}

package com.example.carrentalfrontend.core.repositories

import com.example.carrentalfrontend.domain.model.data.CarBrand
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CarBrandsApi {
    @Headers("Content-Type: application/json")
    @GET("carBrands/getAllCarBrands")
    suspend fun getAllCarBrands(): Response<ArrayList<CarBrand>>

    @Multipart
    @POST("carBrands/addCarBrand")
    suspend fun addCarBrand(
        @Part("brand") brand: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<String>
}
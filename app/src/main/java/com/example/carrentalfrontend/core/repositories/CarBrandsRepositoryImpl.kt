package com.example.carrentalfrontend.core.repositories

import android.content.Context
import android.net.Uri
import com.example.carrentalfrontend.domain.model.data.CarBrand
import com.example.carrentalfrontend.domain.model.dto.AddCarBrandDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class CarBrandsRepositoryImpl(
    private val context: Context,
    private val carBrandsApi: CarBrandsApi
) : CarBrandsRepository {

    override suspend fun getAllCarBrands(): Response<ArrayList<CarBrand>> {
        return carBrandsApi.getAllCarBrands()
    }

    override suspend fun addCarBrand(
        addCarBrandDto: AddCarBrandDto,
        fileUri: Uri
    ): Response<String> {
        // Prepare the text part of the request
        val brandRequestBody =
            addCarBrandDto.brand.toRequestBody("text/plain".toMediaTypeOrNull())

        // Convert Uri to File for the multipart request
        val imageFile = getFileFromUri(context, fileUri)
        val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageBody = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)

        // Make the network request
        return try {
            carBrandsApi.addCarBrand(brandRequestBody, imageBody)
        } catch (e: Exception) {
            // Ideally, handle the exception more gracefully and return an appropriate response
            throw Exception("Network error occurred: ${e.message}")
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw Exception("Cannot open input stream")
        val file = File(context.cacheDir, "temp_image.jpg") // Temporary file
        FileOutputStream(file).use { fileOutput ->
            inputStream.copyTo(fileOutput)
        }
        return file
    }

}
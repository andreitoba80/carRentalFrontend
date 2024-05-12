package com.example.carrentalfrontend.core.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import com.example.carrentalfronted.Person
import com.example.carrentalfrontend.domain.model.entity.User
import com.example.carrentalfrontend.serializer.MySerializer
import com.example.carrentalfrontend.util.toStringV2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import java.io.IOException

class ProtoRepository(context: Context) {
    private val dataStore: DataStore<Person> = context.createDataStore(
        "my_data",
        serializer = MySerializer()
    )

    suspend fun readUserCredentials(): Person? = dataStore.data.firstOrNull().also {
        Log.e("TOBA", "readUserCredentials: ${it?.toStringV2()}")
    }

    val readProto: Flow<Person> = dataStore.data
        .catch { e ->
            if (e is IOException) {
                Log.d("Error", e.message.toString())
                emit(Person.getDefaultInstance())
            } else {
                throw e
            }
        }


    suspend fun deleteUserCredentials() = dataStore.updateData {
        it.toBuilder()
            .clearId()
            .clearEmail()
            .clearUsername()
            .clearFullName()
            .clearIsAdmin()
            .build()
//        Log.e("TOBA", "deleteUserCredentials = ${it.toStringV2()}")
//        it
    }.also {
        Log.e("TOBA", "deleteUserCredentials = ${it.toStringV2()}")
    }

    suspend fun updateUserCredentials(user: User) {
        dataStore.updateData { preference ->
            preference.toBuilder()
                .setId(user.id)
                .setUsername(user.username)
                .setEmail(user.email)
                .setFullName(user.fullName)
                .setIsAdmin(user.isAdmin)
                .build()
        }
    }
}
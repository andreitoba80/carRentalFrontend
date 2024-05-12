package com.example.carrentalfrontend.util

import android.util.Log
import com.example.carrentalfronted.Person

fun Person.isEmpty(): Boolean {
    return username.isBlank() && email.isBlank() && fullName.isBlank()
}

fun Person.toStringV2(): String {
    return "Person [id = $id, username = $username, email = $email, fullName = $fullName, isAdmin = $isAdmin]"
}

fun Any.logDebugError(message: String) {
    Log.e("TOBA ${this::class.java.simpleName}", message)
}
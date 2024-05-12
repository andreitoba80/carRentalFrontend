package com.example.carrentalfrontend.util

//sealed class Event<T> {
//
//    data object Loading : Event<Unit>()
//    data class Success<T>(var data: T) : Event<T>()
//    data class Failure(val errorMessage: String) : Event<String>()
//
//    companion object {
//        fun loading(): Event<Unit> = Loading
//        fun <T> success(data: T): Event<T> = Success(data)
//        fun failure(errorMessage: String): Event<String> = Failure(errorMessage)
//    }
//}

sealed class Event<T> {

    class Loading<T> : Event<T>()
    data class Success<T>(var data: T) : Event<T>()
    data class Failure<T>(val errorMessage: String) : Event<T>()

    companion object {
        fun <T> loading(): Event<T> = Loading()
        fun <T> success(data: T): Event<T> = Success(data)
        fun <T> failure(errorMessage: String): Event<T> = Failure(errorMessage)
    }
}

fun <T> Event<T>.handle(
    onSuccess: (T) -> Unit,
    onFailure: (String) -> Unit
) {
    when (this) {
        is Event.Success -> {
            onSuccess(this.data)
        }

        is Event.Loading -> {
            // TODO: Show a loading indicator
        }

        is Event.Failure -> {
            onFailure(this.errorMessage)
        }
    }
}
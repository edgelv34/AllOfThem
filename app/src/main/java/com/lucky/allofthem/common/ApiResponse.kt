package com.lucky.allofthem.common

import okio.IOException
import retrofit2.HttpException

sealed interface ApiResponse<out R> {
    data class Success<out T>(val data: T): ApiResponse<T>
    data object NetworkError : ApiResponse<Nothing>
    data object Unauthorized : ApiResponse<Nothing>
    data class Failure(val message: String) : ApiResponse<Nothing>
}


suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): ApiResponse<T> {
    return try {
        ApiResponse.Success(block())
    } catch (e: HttpException) {
        ApiResponse.Unauthorized
    } catch (e: IOException) {
        ApiResponse.NetworkError
    } catch (e: Exception) {
        ApiResponse.Failure(message = "Unexpected error: ${e.message}")
    }
}



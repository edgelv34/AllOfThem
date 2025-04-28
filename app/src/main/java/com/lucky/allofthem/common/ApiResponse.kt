package com.lucky.allofthem.common

import okio.IOException
import retrofit2.HttpException

sealed interface ApiResponse<out R> {
    data class Success<out T>(val data: T): ApiResponse<T>
    data class Failure(val message: String) : ApiResponse<Nothing>
}


suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): ApiResponse<T> {
    return try {
        ApiResponse.Success(block())
    } catch (e: HttpException) {
        ApiResponse.Failure(message = "Http 에러 : ${e.message}")
    } catch (e: IOException) {
        ApiResponse.Failure(message = "통신 에러 : ${e.localizedMessage ?: e.message}")
    } catch (e: Exception) {
        ApiResponse.Failure(message = "${e.localizedMessage ?: e.message}")
    }
}



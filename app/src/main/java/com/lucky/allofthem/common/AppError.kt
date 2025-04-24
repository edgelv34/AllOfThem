package com.lucky.allofthem.common

sealed class AppError {
    object NetworkError : AppError()
    object Unauthorized : AppError()
    data class Unknown(val message: String) : AppError()
}

package com.app.currency_converter.domain.model

sealed class Result {
    object Success : Result()
    class Error(val message: String?) : Result()
}

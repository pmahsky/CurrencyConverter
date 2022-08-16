package com.app.currency_converter.model

abstract class BaseResponse(
    val description: String,
    val error: Boolean,
    val message: String,
    val status: Int
)
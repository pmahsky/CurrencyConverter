package com.app.currency_converter.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager
import com.app.currency_converter.data.database.model.CurrencyEntity
import java.math.BigDecimal
import java.math.RoundingMode

object Utils {

    enum class Order(val position: Int) {
        INVALID(-1),
        FIRST(0),
        SECOND(1),
    }

    fun Activity.hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    fun BigDecimal.roundToFourDecimalPlaces(): BigDecimal = setScale(4, RoundingMode.HALF_DOWN)

    fun Long.toMillis() = this * 1_000L
}
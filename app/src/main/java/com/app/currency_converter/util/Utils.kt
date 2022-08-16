package com.app.currency_converter.util

import android.content.Context
import android.net.ConnectivityManager
import com.app.currency_converter.data.database.model.CurrencyEntity
import java.math.BigDecimal
import java.math.RoundingMode

object Utils {

    enum class Order(val position: Int) {
        INVALID(-1),
        FIRST(0),
        SECOND(1),
        THIRD(2),
        FOURTH(3)
    }

    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }

    fun List<*>.hasOnlyOneElement() = size == 1

    fun List<*>.isNotLastElement(position: Int): Boolean {
        if (position < 0 || position >= size) {
            throw IllegalArgumentException("Position: $position is out of bound.")
        }
        return size > position + 1
    }

    fun <E> List<E>.elementBefore(position: Int): E {
        if (position <= 0 || position >= size) {
            throw IllegalArgumentException("Position: $position is invalid.")
        }
        return this[position - 1]
    }

    fun <E> List<E>.elementAfter(position: Int): E {
        if (position < 0 || position >= size - 1) {
            throw IllegalArgumentException("Position: $position is invalid.")
        }
        return this[position + 1]
    }

    fun List<CurrencyEntity>.deepEquals(other: List<CurrencyEntity>): Boolean {
        if (this.size != other.size) {
            return false
        }
        val n = this.size
        for (i in 0 until n) {
            val currencyA = this[i]
            val currencyB = other[i]
            val areCurrenciesEqual = currencyA.deepEquals(currencyB)
            if (!areCurrenciesEqual) {
                return false
            }
        }
        return true
    }

    fun BigDecimal.roundToFourDecimalPlaces(): BigDecimal = setScale(4, RoundingMode.HALF_DOWN)

    fun Int.isValid() = this != Order.INVALID.position

    fun Long.toSeconds() = this / 1_000L

    fun Long.toMillis() = this * 1_000L

    val String.Companion.EMPTY get() = ""
}
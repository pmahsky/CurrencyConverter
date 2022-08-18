package com.app.currency_converter.data

import android.content.Context
import com.app.currency_converter.util.Utils.toMillis

class AppPreference(context: Context) {

    private val sharedPrefs = context.getSharedPreferences("${context.packageName}.properties",
            Context.MODE_PRIVATE)
    private val editor = sharedPrefs.edit()

    var timestampInSeconds: Long
        get() = (sharedPrefs.getLong(TIMESTAMP, NO_DATA))
        set(value) = editor.putLong(TIMESTAMP, value).apply()

    val isDataEmpty
        get() = timeSinceLastUpdateInMillis == NO_DATA

    val shouldRefreshData
        get() = timeSinceLastUpdateInMillis > THIRTY_MINUTES_IN_MILLIS

     private val timeSinceLastUpdateInMillis: Long
        get() {
            return if (timestampInSeconds != NO_DATA) {
                System.currentTimeMillis() - timestampInSeconds.toMillis()
            } else {
                NO_DATA
            }
        }

    companion object {
        const val TIMESTAMP = "timestamp"
        const val THIRTY_MINUTES_IN_MILLIS = 18_00_000L
        const val NO_DATA = 0L
    }
}
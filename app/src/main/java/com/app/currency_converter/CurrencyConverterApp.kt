package com.app.currency_converter

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class CurrencyConverterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        "[L:%s] [M:%s] [C:%s]",
                        element.lineNumber,
                        element.methodName,
                        super.createStackElementTag(element)
                    )
                }
            })
        }
    }


}
package com.pike.checkconnectionwithdnsdemo

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
        ConnectionCheckUtil.initRxJavaDisposeErrorHandler()
    }
    private fun initLogger() {
        Logger.clearLogAdapters()
        var methodCount = 3

        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(methodCount)
            .methodOffset(0)
            .tag("ConnectionCheckUtilDemo")
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
}
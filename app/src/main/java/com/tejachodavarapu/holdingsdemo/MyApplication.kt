package com.tejachodavarapu.holdingsdemo

import android.app.Application
import com.tejachodavarapu.holdingsdemo.worker.WorkManagerScheduler
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        WorkManagerScheduler.scheduleHoldingsSync(applicationContext)
    }
}
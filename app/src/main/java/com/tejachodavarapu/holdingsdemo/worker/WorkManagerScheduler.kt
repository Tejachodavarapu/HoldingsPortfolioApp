package com.tejachodavarapu.holdingsdemo.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object WorkManagerScheduler {

    private const val HOLDINGS_WORK_TAG = "holdings_sync_worker_tag"

    fun scheduleHoldingsSync(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<HoldingsSyncWorker>(
            15, TimeUnit.MINUTES // ⏱ change as needed (min 15 min for periodic)
        )
            .setConstraints(constraints)
            .addTag(HOLDINGS_WORK_TAG)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            HOLDINGS_WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
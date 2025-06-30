package com.tejachodavarapu.holdingsdemo.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tejachodavarapu.holdingsdemo.data.repository.HoldingsRepository
import com.tejachodavarapu.holdingsdemo.utils.NetworkUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber


@HiltWorker
class HoldingsSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: HoldingsRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            if (NetworkUtils.isNetworkAvailable(applicationContext)) {
                repository.syncHoldingsFromApi()
                Timber.d("Holdings synced successfully")
                Result.success()
            } else {
                Timber.d("No internet. Skipping holdings sync.")
                Result.retry()
            }
        } catch (e: Exception) {
            Timber.e(e, "Error syncing holdings")
            Result.failure()
        }
    }
}
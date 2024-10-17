package com.example.publicplayground.feature.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class SimpleCoroutineWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        println("Hello, World before!")
        delay(1000)
        Log.d("SimpleCoroutineWorker", "Hello, World!")
        println("Hello, World after!")
        return Result.success()
    }
}

package com.example.publicplayground.feature.workmanager

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class ExampleUnitTest {
    @Test
    fun simpleWorkerTest() = runTest {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(appContext, config)

        val request = OneTimeWorkRequestBuilder<SimpleCoroutineWorker>()
            .build()
        val workManager = WorkManager.getInstance(appContext)
        testScheduler.advanceTimeBy(2000)
        val result = workManager.enqueue(request).result.get()
        println("result: $result")
    }
}

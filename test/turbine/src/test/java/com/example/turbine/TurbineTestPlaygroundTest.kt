package com.example.turbine

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TurbineTestPlaygroundTest {
    @Test
    fun useAwaitItem() = runTest {
        flow {
            emit(1)
            emit(2)
        }.distinctUntilChanged().test {
            assertThat(awaitItem()).isEqualTo(1)
            assertThat(awaitItem()).isEqualTo(2)
            awaitComplete()
        }
    }

    @Test
    fun useExpectMostRecentItem() = runTest {
        flow {
            emit(1)
            emit(2)
        }.distinctUntilChanged().test {
            assertThat(expectMostRecentItem()).isEqualTo(2)
            // no need awaitComplete()
        }
    }

    @Test
    fun advanceByUseDelay() = runTest {
        flow {
            emit(1)
            emit(2)
            delay(10000)
            emit(3)
        }.distinctUntilChanged().test {
            assertThat(expectMostRecentItem()).isEqualTo(2)
            delay(20000)
            // no need runCurrent()
            assertThat(expectMostRecentItem()).isEqualTo(3)
        }
    }

    @Test
    fun advanceByUseAwait() = runTest {
        flow {
            emit(1)
            emit(2)
            delay(10000)
            emit(3)
        }.distinctUntilChanged().test {
            assertThat(expectMostRecentItem()).isEqualTo(2)
            assertThat(awaitItem()).isEqualTo(3)
            awaitComplete()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun advanceByUseAdvanceUtilIdle() = runTest {
        flow {
            emit(1)
            emit(2)
            delay(10000)
            emit(3)
            delay(10000)
            emit(4)
        }.distinctUntilChanged().test {
            assertThat(expectMostRecentItem()).isEqualTo(2)
            advanceUntilIdle()
            assertThat(awaitItem()).isEqualTo(3) // receive 3
            assertThat(expectMostRecentItem()).isEqualTo(4)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delayLongTime() = runTest {
        flow {
            emit(1)
            delay(1000)
            emit(2)
            delay(100000000)
        }.distinctUntilChanged().test {
            assertThat(expectMostRecentItem()).isEqualTo(1)
            advanceUntilIdle()
            assertThat(expectMostRecentItem()).isEqualTo(2)
        }
    }
}

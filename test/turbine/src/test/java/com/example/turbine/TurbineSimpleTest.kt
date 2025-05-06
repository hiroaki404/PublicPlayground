package com.example.turbine

import app.cash.turbine.Turbine
import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TurbineSampleTest {
    @Test
    fun flowTest() = runTest {
        val simpleFlow = flow {
            emit(1)
            emit(2)
        }

        simpleFlow.first() shouldBe 1
        simpleFlow.drop(1).first() shouldBe 2
    }

    @Test
    fun turbineTest() = runTest {
        val simpleFlow = flow {
            emit(1)
            emit(2)
        }

        simpleFlow.test {
            awaitItem() shouldBe 1
            awaitItem() shouldBe 2
            awaitComplete()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun sharedFlowTest() = runTest {
        val sharedFlow = MutableSharedFlow<Int>()
        val values = mutableListOf<Int>()

        // NG: because endless flow
//        flow.toList(list)

        // OK
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sharedFlow.toList(values)
        }

        sharedFlow.emit(1)
        values[0] shouldBe 1
        sharedFlow.emit(2)
        values[1] shouldBe 2
    }

    @Test
    fun sharedFlowWithTurbine() = runTest {
        val sharedFlow = MutableSharedFlow<Int>()

        sharedFlow.test {
            sharedFlow.emit(1)
            awaitItem() shouldBe 1
            sharedFlow.emit(2)
            awaitItem() shouldBe 2
        }
    }

    interface Logger {
        fun log(message: String)
    }

    @Test
    fun useStandaloneTurbines() = runTest {
        class FakeLogger : Logger {
            val message = Turbine<String>()

            override fun log(message: String) {
                this.message.add(message)
            }
        }

        class GreetingRepository(val logger: Logger) {
            private val _greeting = "hello"
            fun greeting() = flow {
                val greeting = _greeting
                logger.log(greeting)
                emit(greeting)
            }
        }

        val fakeLogger = FakeLogger()
        val greetingRepository = GreetingRepository(fakeLogger)
        greetingRepository.greeting().test {
            awaitItem() shouldBe "hello"
            fakeLogger.message.awaitItem() shouldBe "hello" // 検証
            awaitComplete()
        }
    }
}

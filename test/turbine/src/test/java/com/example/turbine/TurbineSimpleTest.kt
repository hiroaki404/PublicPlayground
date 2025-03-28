package com.example.turbine

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
}

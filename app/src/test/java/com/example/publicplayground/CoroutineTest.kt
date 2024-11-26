package com.example.publicplayground

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CoroutineTest {
    @Test
    fun verify_supervisor_job() = runTest {
//        CoroutineScope(this.coroutineContext + SupervisorJob()).launch {
//            CoroutineScope(this.coroutineContext + SupervisorJob()).launch {
//                delay(1000)
//                println("coroutine1")
//                throw Exception("test")
//            }
//            CoroutineScope(this.coroutineContext).launch {
//                delay(2000)
//                println("coroutine2")
//                throw Exception("test")
//            }
//            delay(3000)
//            println("coroutine3")
//        }

        // log
        // coroutine1
        // coroutine2
        // throw Exception("test")
    }

    @Test
    fun coroutine_test() = runTest {
        launch {
            delay(1000)
            println("coroutine")
        }
        println("test")
        testScheduler.advanceUntilIdle()
        launch {
            delay(2000)
            println("coroutine2")
        }
        println("test finished")
        // log
        // test
        // coroutine
        // test finished
        // coroutine2
    }
}

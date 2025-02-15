package com.example.publicplayground.guardExpressionKotlin

enum class ErrorCode {
    NOT_FOUND,
    UNKNOWN
}

sealed interface Result {
    data object Success : Result
    data class Error(val code: ErrorCode) : Result
}

fun main() {
    val random = Math.random()
    val result: Result = when (random) {
        in 0.0..<0.5 -> Result.Success
        in 0.5..<0.75 -> Result.Error(ErrorCode.NOT_FOUND)
        else -> Result.Error(ErrorCode.UNKNOWN)
    }

    when (result) {
        is Result.Success -> println("Success")
        is Result.Error -> {
            when (result.code) {
                ErrorCode.NOT_FOUND -> println("Error: NOT_FOUND")
                ErrorCode.UNKNOWN -> println("Error: UNKNOWN")
            }
        }
    }

    when (result) {
        is Result.Success -> println("Success")
        is Result.Error if (result.code == ErrorCode.NOT_FOUND) -> println("Error: NOT_FOUND")
        is Result.Error -> println("Error: UNKNOWN")
    }

    when (result) {
        is Result.Success -> println("Success")
        else if result.isKnown() -> println("Error: NOT_FOUND")
        else -> println("Error: UNKNOWN")
    }
}

fun Result.isKnown(): Boolean = when (this) {
    is Result.Success -> true
    is Result.Error -> this.code != ErrorCode.UNKNOWN
}

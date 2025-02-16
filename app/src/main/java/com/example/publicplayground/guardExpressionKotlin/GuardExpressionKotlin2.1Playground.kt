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
    println("----sample1----")
    sample1(result = Result.Error(ErrorCode.NOT_FOUND))
    println("----sample2----")
    sample2(status = Status.Success("Success"))
}

fun Result.isKnown(): Boolean = when (this) {
    Result.Success -> true
    is Result.Error -> this.code != ErrorCode.UNKNOWN
}

fun sample1(result: Result = Result.Success) {
    val result: Result = result

    when (result) {
        Result.Success -> println("Success")
        is Result.Error -> {
            when (result.code) {
                ErrorCode.NOT_FOUND -> println("Error: NOT_FOUND")
                ErrorCode.UNKNOWN -> println("Error: UNKNOWN")
            }
        }
    }

    when (result) {
        Result.Success -> println("Success")
        is Result.Error if (result.code == ErrorCode.NOT_FOUND) -> println("Error: NOT_FOUND")
        is Result.Error -> println("Error: UNKNOWN")
    }

    // error
//    when (result) {
//        Result.Success -> println("Success")
//        is Result.Error if (result.code == ErrorCode.NOT_FOUND) -> println("Error: NOT_FOUND")
//        is Result.Error if (result.code == ErrorCode.UNKNOWN) -> println("Error: UNKNOWN")
//    }

    when (result) {
        Result.Success -> println("Success")
        is Result.Error if Math.random() > 0.5 -> println("Error but happy")
        else -> println("Error and unhappy")
    }

    when (result) {
        Result.Success -> println("Success")
        else if result.isKnown() -> println("Error: KNOWN")
        else -> println("Error: UNKNOWN")
    }
}

sealed interface Status {
    data object Loading : Status
    data class Success(val message: String) : Status
    data class Error(val code: ErrorCode) : Status
}

fun Status.hasNoMessage(): Boolean = when (this) {
    is Status.Success -> this.message.isEmpty()
    else -> true
}

fun sample2(status: Status = Status.Success("Success")) {
    val status = status

    when (status) {
        Status.Loading -> println("Loading")
        else if status.hasNoMessage() -> println("Error: NO_MESSAGE")
        else -> println("Has message")
    }

    when (status) {
        Status.Loading -> println("Loading")
        is Status.Success if !status.hasNoMessage() -> println("Success")
        else -> println("Error: NO_MESSAGE")
    }

    when (status) {
        Status.Loading -> println("Loading")
        is Status.Success if Math.random() > 0.5 -> println("happy")
        else if Math.random() < 0.5 -> println("unhappy")
        else -> println("Error: UNKNOWN")
    }
}

fun sample3() {
    val errorCode: ErrorCode = ErrorCode.NOT_FOUND
    when (errorCode) {
        ErrorCode.NOT_FOUND -> println("Error: NOT_FOUND")
        else -> println("Error: UNKNOWN")
    }

    val status: Result = Result.Error(ErrorCode.NOT_FOUND)
    when (status) {
        Result.Success -> println("Success")
        is Result.Error -> println("Error: ${status.code}")
        else -> println("Success")
    }
}

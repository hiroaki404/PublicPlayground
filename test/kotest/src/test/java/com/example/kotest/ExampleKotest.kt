package com.example.kotest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.startWith

class MyTests: StringSpec({
    "length should return size of string" {
        "hello".length shouldBe 5
    }

    // Failure case
    "length should return size of string failure case" {
        1 shouldBe 2
    }

    "length should return size of string other description" {
        "hello".length.shouldBe(5)
    }

    "startsWith should test for a prefix" {
        "world" should startWith("wor")
    }

    // Failure case
    "compare two objects has same fields" {
        val car1 = SampleCar("Roadster", "Mazda")
        val car2 = SampleCar("Roadster", "Mazda")
        car1 shouldBe car2
    }

    "compare two objects has same fields with fields" {
        val car1 = SampleCar("Roadster", "Mazda")
        val car2 = SampleCar("Roadster", "Mazda")
        car1 shouldBeEqualToComparingFields car2
    }

    // Failure case
    "compare two objects has different fields" {
        val car1 = SampleCar("Roadster", "Mazda")
        val car2 = SampleCar("Lexus", "Toyota")
        car1 shouldBe car2
    }

    // Failure case
    "compare two objects has different fields with fields" {
        val car1 = SampleCar("Roadster", "Mazda")
        val car2 = SampleCar("Lexus", "Toyota")
        car1 shouldBeEqualToComparingFields  car2
    }
})

class SampleCar(
    val model: String,
    val manufacture: String
)

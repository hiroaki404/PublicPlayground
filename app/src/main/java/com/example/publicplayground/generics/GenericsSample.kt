package com.example.publicplayground.generics

class Box<T>(private val t: T) {
    val boxMessage: String = "give you $t"
    fun getValue(): T = t

    // これでも同じ
    fun <T> getValue2() = t
}

fun giveYouBox() {
    val stringBox: Box<String> = Box("Switch2")
    var intBox: Box<Int> = Box(100000)
    // NG
//    intBox = stringBox

    println("value is ${stringBox.getValue()}, and ${stringBox.boxMessage}")
    println("value is ${intBox.getValue()}, and ${intBox.boxMessage}")
}

class Square<T : Number>(private val number: T) {
    fun getArea(): Double = number.toDouble() * number.toDouble()
    fun getValue(): T = number
}

fun getSquare() {
    val square: Square<Int> = Square(10)
    println("value is ${square.getValue()}, Square area is ${square.getArea()}")

    val doubleSquare: Square<Double> = Square(2.5)
    println("value is ${doubleSquare.getValue()}, Square area is ${doubleSquare.getArea()}")
}

fun useList() {
    val list: List<Number> = listOf<Int>(1, 2)
    // compile error
//    val mutableList: MutableList<Number> = mutableListOf<Int>(1, 2)
}

fun <T> String.logConnectedMessage(value: T): T {
    print("$this $value")
    return value
}

fun main() {
    giveYouBox()
    getSquare()
    "this is".logConnectedMessage(1)
}

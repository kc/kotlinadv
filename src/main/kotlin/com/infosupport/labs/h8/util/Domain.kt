package com.infosupport.labs.h8

data class Person(val name: String, val age: Int)

fun List<Int>.peekNotInlined(action: (Int) -> Unit): List<Int> {
    forEach(action)
    return this
}

inline fun List<Int>.peekInlined(action: (Int) -> Unit): List<Int> {
    forEach(action)
    return this
}

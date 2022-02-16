package com.infosupport.labs.h8

// Not inlined

fun notInlinedFilter(list: List<Int>, predicate: (Int) -> Boolean): List<Int> {
    return list.filter(predicate)
}

fun notInlinedTest() {
    val list = listOf(1, 2, 3)
    val newList = notInlinedFilter(list) { it < 2 } // Call site, where the lambda parameter body is defined
    println(newList)
}

// Inlined

inline fun inlinedFilter(list: List<Int>, predicate: (Int) -> Boolean): List<Int> {
    // Inlined function body start
    return list.filter(predicate)
    // Inlined function body end
}

fun inlinedTest() {
    val list = listOf(1, 2, 3)
    val newList = inlinedFilter(list) { it < 2 } // Call site, where the lambda parameter body is defined
    println(newList)
}

// This time, the lambda parameter body is not defined in the call-site, but is passed as a parameter.

fun filterLessThanTwo(input: Int) = input < 2

fun inlinedTestPassingLambdaInstance() {
    val predicate = ::filterLessThanTwo
    val list = listOf(1, 2, 3)
    val newList = inlinedFilter(list, predicate) // lambda parameter body as parameter
    println(newList)
}

fun main() {
    notInlinedTest()
    inlinedTest()
    inlinedTestPassingLambdaInstance()
}

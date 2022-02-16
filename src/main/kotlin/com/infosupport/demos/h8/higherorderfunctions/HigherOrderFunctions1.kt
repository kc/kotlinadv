package com.infosupport.demos.h8.higherorderfunctions

// Declaring higher-order functions

fun main() {
    callThem()
    callTwoAndThree()
    callInDifferentWaysUsingDefaults()
    callNullableLambda(action)
    callNullableLambda(null)

    printAverageDuration()
    printAverageDurationForOs()
    printAverageDurationForPredicate()
}

// Function types ----------------------------------------------
val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y } // syntax
val action: () -> Unit = { println(42) }

// types inferred as much as possible
val sum2 = { x: Int, y: Int -> x + y }
val action2 = { println(42) }

fun callThem() {
    sum(1, 2)
    action()
}

// nullable
var canReceiveNull: (Int?, Int?) -> Int = { a, b -> a?.plus(b ?: 0) ?: 0 }
var canReturnNull: (Int, Int) -> Int? = { _, _ -> null } // note the underscores
var canBeNull: ((Int, Int) -> Int)? = null

// Calling functions passed as arguments -------------------------

fun twoAndThree(operation: (Int, Int) -> Int) {
    val result = operation(2, 3)
    println("The result is $result")
}

fun callTwoAndThree() {
    twoAndThree({ a, b -> a + b })
    twoAndThree { a, b -> a * b } // no parentheses
    twoAndThree(sum)
}

// Default and null values for parameters with function types --------

fun callInDifferentWaysUsingDefaults() {
    val letters = listOf("Alpha", "Beta")

    println(letters.joinToString(separator = "! ", postfix = "! ", transform = { it.uppercase() }))
    println(letters.joinToString { it.lowercase() }) // only supply the last parameter, which is a lambda, others are default
    println(letters.joinToString()) // all the defaults are used
}

fun callNullableLambda(callback: (() -> Unit)?) {
    // Elvis doesn't work with lambdas:
    // callback?.()

    // Instead:
    if (callback != null) {
        callback()
    }

    // alternatively:
    callback?.invoke() // function type is an implementation of an interface with an invoke method
}

// Removing duplication with a higher-order function ---------------------------
// We want to search a log for average duration of calls.

// Attempt 1: filter is hard coded, not DRY
val averageDurationWindows = logList.filter { it.os == OS.WINDOWS }.map(SiteVisit::duration).average()
val averageDurationMac = logList.filter { it.os == OS.MAC }.map(SiteVisit::duration).average()
val averageDurationMobile = logList.filter { it.os in setOf(OS.IOS, OS.ANDROID) }.map(SiteVisit::duration).average()

fun printAverageDuration() {
    println(averageDurationWindows)
    println(averageDurationMac)
    println(averageDurationMobile)
}

// Attempt 2: parameterize the OS with a normal extension function, DRY
fun List<SiteVisit>.averageDurationFor(os: OS) = filter { it.os == os }.map(SiteVisit::duration).average()

fun printAverageDurationForOs() {
    println(logList.averageDurationFor(OS.WINDOWS))
    println(logList.averageDurationFor(OS.MAC))
}

// Attempt 3: search on combinations of conditions with higher order extension functions, DRY for all situations
fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) = filter(predicate).map(SiteVisit::duration).average()

fun printAverageDurationForPredicate() {
    println(logList.averageDurationFor { it.os == OS.WINDOWS })
    println(logList.averageDurationFor { it.os == OS.MAC })
    println(logList.averageDurationFor { it.os in setOf(OS.ANDROID, OS.IOS) })
    println(logList.averageDurationFor { it.os == OS.IOS && it.path == "/signup" })
}




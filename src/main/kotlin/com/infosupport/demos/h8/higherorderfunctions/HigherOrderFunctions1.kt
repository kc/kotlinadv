package com.infosupport.demos.h8.higherorderfunctions

// Declaring higher-order functions

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
var canHaveNull: (Int?, Int?) -> Int = { a, b -> a?.plus(b ?: 0) ?: 0 }
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

    println(letters.joinToString(separator = "! ", postfix = "! ", transform = { it.toUpperCase() }))
    println(letters.joinToString { it.toLowerCase() }) // only supply the last parameter, which is a lambda, others are default
    println(letters.joinToString()) // all the defaults are used
}

fun callNullableLambda(callback: (() -> Unit)?) {
    // Elvis doesn't work with lambdas:
    if (callback != null) {
        callback()
    }

    // alternatively:
    callback?.invoke() // function type is an implementation of an interface with an invoke method
}

// Removing duplication with a higher-order function ---------------------------
// We want to search a log for average duration of calls.

// OS is hard coded...
val averageWindowsDuration =
    log
        .filter { it.os == OS.WINDOWS }
        .map(SiteVisit::duration)
        .average()

fun printAverageWindowsDuration() {
    println(averageWindowsDuration)
}

// parameterize the OS with a regular function, DRY
fun List<SiteVisit>.averageDurationFor(os: OS) = filter { it.os == os }.map(SiteVisit::duration).average()

fun printAverageDurationForOs() {
    println(log.averageDurationFor(OS.WINDOWS))
    println(log.averageDurationFor(OS.MAC))
}

// search for more than one os (all mobiles), not DRY compared to previous
val averageMobileDuration = log
    .filter { it.os in setOf(OS.IOS, OS.ANDROID) }
    .map(SiteVisit::duration)
    .average()

fun printAverageMobileDuration() {
    println(averageMobileDuration)
}

// search on combinations of conditions with higher order functions, DRY for all situations
fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
    filter(predicate).map(SiteVisit::duration).average()

fun printAverageDurationForPredicate() {
    println(log.averageDurationFor { it.os == OS.WINDOWS })
    println(log.averageDurationFor { it.os == OS.MAC })
    println(log.averageDurationFor { it.os in setOf(OS.ANDROID, OS.IOS) })
    println(log.averageDurationFor { it.os == OS.IOS && it.path == "/signup" })
}

// -----------

fun main() {
    callThem()
    callTwoAndThree()
    callInDifferentWaysUsingDefaults()
    callNullableLambda(action)
    callNullableLambda(null)

    printAverageWindowsDuration()
    printAverageDurationForOs()
    printAverageMobileDuration()
    printAverageDurationForPredicate()
}

package com.infosupport.demos.h12.async

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Coroutines
// flows

// A suspending function asynchronously returns a single value, but how
// can we return multiple asynchronously computed values? This is where Kotlin Flows come in.

// Multiple values can be represented in Kotlin using collections:
fun simple(): List<Int> = listOf(1, 2, 3)

// If we are computing the numbers with some CPU-consuming blocking code (each computation taking 100ms), then we can represent the numbers using a Sequence:
fun sequence(): Sequence<Int> = sequence { // sequence builder
    for (i in 1..3) {
        Thread.sleep(1000) // pretend we are computing it
        yield(i) // yield next value
    }
}

// However, this computation blocks the main thread that is running the code.
// When these values are computed by asynchronous code we can mark the simple function with a suspend modifier, so that it can perform its work without blocking and return the result as a list:
suspend fun suspendable(): List<Int> {
    delay(3000) // pretend we are doing something asynchronous here
    return listOf(1, 2, 3)
}

// Using the List<Int> result type, means we can only return all the values at once.
// To represent the stream of values that are being asynchronously computed, we can use a
// Flow<Int> type just like we would use the Sequence<Int> type for synchronously computed values:
fun flowww(): Flow<Int> = // no longer marked with suspend modifier
    flow {              // flow builder
        for (i in 1..3) {
            delay(1000) // pretend we are doing something useful here
            emit(i)     // emit next value
        }
    }

fun main() {
    printheader(::simple)
    simple().forEach { log("simple = $it") }
    log("Am I alive?") // will only be printed after simple is done.

    printheader(::sequence)
    sequence().forEach { log("sequence = $it") }
    log("Am I alive?") // will only be printed after simpleSequence is done.

    printheader(::suspendable)
    runBlocking {
        launch { heartbeat() } // check if the main thread is alive
        suspendable().forEach { log("suspendable = $it") } // results will become available all at once...
    }

    printheader(::flowww)
    runBlocking {
        launch { heartbeat() }  // to check if the main thread is alive
        flowww().collect { log("flow = $it") } // results will become available one by one
    }

    // More: https://kotlinlang.org/docs/flow.html
}

private suspend fun heartbeat() {
    for (k in 1..3) {
        log("I'm alive $k")
        delay(1000)
    }
}


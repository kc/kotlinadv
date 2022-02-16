package com.infosupport.demos.h12.async

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

// Coroutines
// async await many

fun launchManyCoroutinesAndAwaitResult() {
    val deferred = (1..1_000_000).map { n ->
        GlobalScope.async {
            doWork(n) // doWork is, again, declared as suspend(able)
        }
    }

    // await not allowed outside coroutine scope
    // val sum = deferred.sumOf { it.await().toLong() }

    runBlocking {
        val sum = deferred.sumOf { it.await().toLong() }
        println("Sum: $sum")
    }

    // Q: How long does this take?
    // A: run it. It doesn't take 1_000_000 seconds, so coroutines run concurrently.

    // See resources/../
}

private suspend fun doWork(n: Int): Int {
    delay(1000)
    return n
}

@ExperimentalTime
fun main() {
    println(measureTime { launchManyCoroutinesAndAwaitResult() })
}

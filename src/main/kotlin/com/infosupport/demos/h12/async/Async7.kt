package com.infosupport.demos.h12.async

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

// Coroutines
// Shared mutable state and concurrency
// See https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html

// Let us launch a hundred coroutines all doing the same action thousand times.
// We'll also measure their completion time for further comparisons:
suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

// This is not thread safe:
var counter = 0
private suspend fun notThreadSafe() {
    printheader(::notThreadSafe)
    withContext(Dispatchers.Default) {
        massiveRun {
            counter++
        }
    }
    println("Counter = $counter")
}

// Volatiles are of no help
@Volatile var counterVolatile = 0
private suspend fun notThreadSafeVolatile() {
    printheader(::notThreadSafeVolatile)
    withContext(Dispatchers.Default) {
        massiveRun {
            counterVolatile++
        }
    }
    println("Counter = $counterVolatile")
}

// Thread-safe data structures
val counterAtomic = AtomicInteger()
private suspend fun threadSafeAtomic() {
    printheader(::threadSafeAtomic)
    withContext(Dispatchers.Default) {
        massiveRun {
            counterAtomic.incrementAndGet()
        }
    }
    println("Counter = $counterAtomic")
}

// Thread confinement fine-grained
val counterContext = newSingleThreadContext("CounterContext")
private suspend fun threadSafeConfinedFineGrained() {
    printheader(::threadSafeConfinedFineGrained)
    counter = 0
    withContext(Dispatchers.Default) {
        massiveRun {
            // confine each increment to a single-threaded context
            withContext(counterContext) {
                counter++
            }
        }
    }
    println("Counter = $counter")
}

// Thread confinement coarse-grained
private suspend fun threadSafeConfinedCoarseGrained() {
    printheader(::threadSafeConfinedCoarseGrained)
    counter = 0
    // confine everything to a single-threaded context
    withContext(counterContext) {
        massiveRun {
            counter++
        }
    }
    println("Counter = $counter")
}

// Mutual exclusion
val mutex = Mutex()
private suspend fun threadSafeMutex() {
    printheader(::threadSafeMutex)
    counter = 0
    withContext(Dispatchers.Default) {
        massiveRun {
            // protect each increment with lock
            mutex.withLock {
                counter++
            }
        }
    }
    println("Counter = $counter")

    // The locking in this example is fine-grained, so it pays the price.
    // However, it is a good choice for some situations where you
    // absolutely must modify some shared state periodically, but
    // there is no natural thread that this state is confined to.
}

// Actors ------------------------
// See https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html#actors

// Message types for counterActor
sealed class CounterMsg
object IncCounter : CounterMsg() // one-way message to increment counter
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg() // a request with reply

// This function launches a new counter actor
fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter = 0 // actor state
    for (msg in channel) { // iterate over incoming messages
        when (msg) {
            is IncCounter -> counter++
            is GetCounter -> msg.response.complete(counter)
        }
    }
}

private suspend fun threadSafeActor(scope: CoroutineScope) {
    printheader(::threadSafeActor)

    // create the actor
    val counterActor = scope.counterActor()

    withContext(Dispatchers.Default) {
        massiveRun {
            counterActor.send(IncCounter) // send msg to actor
        }
    }

    // send a message to get a counter value from an actor
    val response = CompletableDeferred<Int>()
    counterActor.send(GetCounter(response))
    println("Counter = ${response.await()}")

    counterActor.close() // shutdown the actor
}

fun main() {
    runBlocking {
        notThreadSafe()
        notThreadSafeVolatile()

        threadSafeAtomic()                  // performs OK
        threadSafeConfinedFineGrained()     // sloooooooooooow........
        threadSafeConfinedCoarseGrained()   // better
        threadSafeMutex()                   // slow
        threadSafeActor(this)               // slow
    }
}

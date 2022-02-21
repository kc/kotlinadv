package com.infosupport.demos.h12.async

import com.infosupport.demos.h8.higherorderfunctions.people
import kotlinx.coroutines.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

// Coroutines
// suspend and resume, async await

// Let's look at the definition of delay:
// public suspend fun delay(timeMillis: Long)

// It's marked with keyword suspend, meaning this function can (only)
// be called from a coroutine.

// If we call two suspend functions one after the other _in the same coroutine_,
// they will influence each other, like in this example:

fun networkCallsSequential() {
    printheader(::networkCallsSequential)
    runBlocking {
        launch {
            val networkCallAnswer = doNetworkCall() // TODO check the signature of this function
            val networkCallAnswer2 = doNetworkCall2()
            println("$networkCallAnswer, $networkCallAnswer2")
        }
    }
}

// Cancel a job with cancel()
fun networkCallsSequentialWithCancel() {
    printheader(::networkCallsSequentialWithCancel)
    runBlocking {
        val job = launch {
            val networkCallAnswer = doNetworkCall()
            val networkCallAnswer2 = doNetworkCall2()
            println("result: $networkCallAnswer, $networkCallAnswer2")
        }
        delay(100)
        job.cancel()
        println("job done")
    }
}

// Cancel a job with withTimeout
fun networkCallsSequentialWithTimeout() {
    printheader(::networkCallsSequentialWithTimeout)
    try {
        runBlocking {
            withTimeout(150) {
                val networkCallAnswer = doNetworkCall()
                val networkCallAnswer2 = doNetworkCall2()
                println("result: $networkCallAnswer, $networkCallAnswer2")
            }
            println("job done")
        }
    } catch (e: TimeoutCancellationException) {
        System.err.println("Timeout! ${e.message}")
    }
}

fun networkCallsDispatched() {
    printheader(::networkCallsDispatched)
    runBlocking { // let's call this thread main
        // You can dispatch to different thread, see resources/../suspend_dispatched.png
        // TODO comment out dispatcher, and see resources/../suspend_main.png
        launch(Dispatchers.Default) {
            // let's call this thread T1
            val msg = doNetworkCall() // will suspend this task on T1
            log(msg)
        }

        log("running...")
        delay(50)                     // will suspend this task on main
        log("done")
    }
}

fun networkCallsConcurrentlyWithJoins() {
    printheader(::networkCallsConcurrentlyWithJoins)
    runBlocking {
        val job1 = this.launch {
            log(doNetworkCall())
        }

        val job2 = launch {
            log(doNetworkCall2())
        }

        // wait until all jobs are done...
        job1.join()
        job2.join()
    }
}

// Another way of starting a coroutine is async {}.
// Unlike many other languages with similar capabilities, async and await are not keywords in Kotlin
// and are not even part of its standard library. Moreover, Kotlin's concept of suspending function
// provides a safer and less error-prone abstraction for asynchronous operations than futures and promises.

fun networkCallsConcurrentlyWithAsync() {
    printheader(::networkCallsConcurrentlyWithAsync)
    runBlocking {
        // async is like launch, but returns an instance of Deferred<T>
        val answer1 = async {                 // like Promise/CompletableFuture/...:
            val doc = getDocument(1)          // .. and then
            val response = doNetworkCall(doc) // .. and then
            response
        }

        val answer2 = async {
            val doc = getDocument(2)           // .. and then
            val response = doNetworkCall2(doc) // .. and then
            response
        }

        log(answer1.await()) // Deferred has an await() function that returns the result of the coroutine.
        log(answer2.await())
        println("done")
    }
}

// check the keyword suspend
suspend fun getDocument(id: Int): String {
    delay(10L) // suspend this coroutine, giving way to other coroutines on the thread
    return "{id: $id,  name: '${people[id].name}'}"
}

suspend fun doNetworkCall(json: String = "{}"): String {
    printheader(::doNetworkCall)
    delay(200L)
    return "the answer to $json is: 42"
}

suspend fun doNetworkCall2(json: String = "{}"): String {
    printheader(::doNetworkCall2)
    delay(100L)
    return "the answer to $json is: 1337"
}

@ExperimentalTime // needed for built in function measureTime
fun main() {
    println(measureTime { networkCallsSequential() })
    // println(measureTime { networkCallsSequentialWithCancel() })
    // println(measureTime { networkCallsSequentialWithTimeout() })
    // println(measureTime { networkCallsDispatched() })
    // println(measureTime { networkCallsConcurrentlyWithJoins() })
    // println(measureTime { networkCallsConcurrentlyWithAsync() })
}

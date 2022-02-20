package com.infosupport.demos.h12.async

import kotlinx.coroutines.*
import kotlin.concurrent.thread

// Coroutines
// Basics: launch, delay vs sleep, thread, runblocking, join, coroutinescope.

// The next part is from:
// https://kotlinlang.org/docs/coroutines-guide.html#table-of-contents and
// https://medium.com/swlh/coroutines-pilove-notes-cb83654a88d4

// In order to use coroutines as well as follow the examples in this guide, you need to add a dependency
// on the kotlinx-coroutines-core module as explained in the project README,
// see https://github.com/Kotlin/kotlinx.coroutines/blob/master/README.md#using-in-your-projects.
// It contains a number of high-level coroutine-enabled primitives that this guide covers,
// including launch, async and others.

fun firstCoroutine() {
    // Every coroutine needs to start in a scope, and GlobalScope is
    // a scope that will live as long as our app lives.

    GlobalScope.launch {    // launch a new coroutine in background and continue
        delay(1000L)        // NON-BLOCKING; _suspends_ coroutine and thread can continue with other work
        log("World!")       // print after delay, NON blocking
    }
    log("Hello,")           // main thread continues while coroutine is delayed
    Thread.sleep(2000L)     // BLOCKING, _blocks_ thread, keep GlobalScope alive (what happens if we lower this value?)
    println("Done")
}

// We can do the same with threads:

fun sameWithThread() {
    thread {
        // delay(1000L) // not allowed in thread

        /*
            Suspend function 'delay' should be called only from a coroutine or another suspend function
            That is because delay is a special suspending function that does not block a thread,
            but suspends the coroutine, and it can be only used from a coroutine.

            In other words:
            Opposed to Thread.sleep(), delay() will not stop the whole thread that it belongs to,
            the same way that if one worker on construction site stops working the other workers
            will continue, but if we want for the whole construction site to stop working we would use Thread.sleep()
        */

        // instead use:
        Thread.sleep(1000L) // BLOCKING (consumes resources for 1 second for nothing...)
        log("World!")
    }
    log("Hello,")
    Thread.sleep(2000L)     // BLOCKING
    println("Done")
}

fun sameWithRunBlocking() {
    GlobalScope.launch {
        delay(1000L)
        log("World!")
    }
    log("Hello,")
    runBlocking {
        delay(2000L)    // now we can run a non-blocking suspended coroutine in a runBlocking{..}
    }
    println("Done")
}

fun sameButMoreIdiomatic() {
    runBlocking {            // start main coroutine
        GlobalScope.launch { // launch a new coroutine in background and continue
            delay(1000L)
            log("World!")
        }
        log("Hello,")       // main coroutine continues here immediately
        delay(2000L)
    } // blocks until all coroutines finish
    println("Done")
}

fun sameButWithJoin() {
    runBlocking {
        // A coroutine runs in a background job.
        // Conceptually, a job is a cancellable thing with a life-cycle that culminates in its completion.
        // See https://www.raywenderlich.com/books/kotlin-coroutines-by-tutorials/v2.0/chapters/3-getting-started-with-coroutines#toc-chapter-007-anchor-004
        val job = GlobalScope.launch {
            delay(1000L)
            log("World!")
        }
        println("Hello,")
        job.join() // no delay anymore, wait here for job to finish
    }
    println("Done") // immediately when job is done
}

fun sameButWithNestedCoroutineScope() {
    // outer coroutine
    runBlocking {
        // inner coroutine
        val job = this.launch { // NOTE: this.launch
            delay(1000L)
            log("World!")
        }
        println("Hello,")
        // NOTE: job.join() not needed, because an outer coroutine (runBlocking) does
        // not complete until all the coroutines launched in its scope (job) complete.
    }
    println("Done") // immediately when job and so runblocking are done
}

fun newCoroutineScope() {
    runBlocking {
        println("Begin")

        coroutineScope { // this: CoroutineScope
            // Concurrently executes both sections
            launch {
                delay(200L)
                println("World 2")
            }
            launch {
                delay(100L)
                println("World 1")
            }
            println("Hello")
            // completes only after both sections are complete
        }
        println("Done")
    }
    log("runBlocking is over")
}

fun main() {
    // firstCoroutine()
    // sameWithThread()
    // sameWithRunBlocking()
    // sameButMoreIdiomatic()
    // sameButWithJoin()
    // sameButWithNestedCoroutineScope()
    // newCoroutineScope()
}

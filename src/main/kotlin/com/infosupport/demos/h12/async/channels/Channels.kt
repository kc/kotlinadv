package com.infosupport.demos.h12.async.channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val channel = Channel<String>()

    // coroutine A sends two messages on the channel:
    launch {
        channel.send("A1")
        channel.send("A2")
        log("A done")
    }

    // coroutine B sends one messages on the channel:
    launch {
        channel.send("B1")
        log("B done")
    }

    // coroutine C receives the messages
    launch {
        repeat(3) {
            val x = channel.receive()
            log("C receives" + x)
        }
    }
}

fun log(message: Any?) {
    println("[${Thread.currentThread().name}] $message")
}

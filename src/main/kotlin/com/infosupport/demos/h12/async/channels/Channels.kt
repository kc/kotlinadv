package com.infosupport.demos.h12.async.channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {

        val channel = Channel<Int>() // like a stream or an Observable

        launch {
            for (i in 1..20) {
                println("sending $i")
                channel.send(i) // can suspend
            }
            channel.close() // to terminate the receiver
        }

        coroutineScope {
            launch {
                receive(channel, "A")
            }

            launch {
                receive(channel, "B")
            }
        }
    }
}

private suspend fun receive(channel: Channel<Int>, id: String) {
    for (i in channel) { // can suspend
        println("$id processed $i")
    }
    println("channel closed for $id")
}

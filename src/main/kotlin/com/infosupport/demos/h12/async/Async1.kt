package com.infosupport.demos.h12.async

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

// Coroutines
// Coroutines vs threads and other async mechanisms

// See slides.
/*
    From: https://medium.com/swlh/coroutines-pilove-notes-cb83654a88d4

    See:
      https://miro.medium.com/max/3000/1*yM9ZpkM8eFZP9QhPRuMKTQ.png
      https://miro.medium.com/max/3000/1*3YcYk00Y88SktNAtRVBoXQ.png
*/

// That being said, let's test how light coroutines are vs threads:

fun testHowLightCoroutinesAre() {
    val c = measure(10_000, ::coroutine)
    val t = measure(10_000, ::thread)
    println("These threads are ${(t / c).round(2)} times slower than coroutines.")
}

private fun coroutine() {
    GlobalScope.launch { delay(10) }
}

private fun thread() {
    thread { Thread.sleep(10) }
}

fun main() {
    testHowLightCoroutinesAre()
}


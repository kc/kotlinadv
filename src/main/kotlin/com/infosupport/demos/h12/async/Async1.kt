package com.infosupport.demos.h12.async

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

// Coroutines
// Coroutines vs threads and other async mechanisms

// show small intro: https://youtu.be/ShNhJ3wMpvQ?t=180
// See screenshots (from this talk: https://youtu.be/wBpKAv4i8Ug):
// - img/threads_and_cpu.png
// - img/coroutines_and_threads.png

/*
    Kotlin Coroutines are like lightweight threads.
         - They are lightweight because creating coroutines doesn’t allocate new threads.
         - Instead, they use predefined thread (pools) and smart scheduling.

    Additionally, coroutines can be suspended and resumed mid-execution.

    Also, you can easily change the context of the coroutine.
     - What that means is that if we think about threads as construction sites
       and coroutines as workers on the construction site, changing context means
       shifting a worker from one site to an another.

    From: https://medium.com/swlh/coroutines-pilove-notes-cb83654a88d4

    See:
      https://miro.medium.com/max/3000/1*yM9ZpkM8eFZP9QhPRuMKTQ.png
      https://miro.medium.com/max/3000/1*3YcYk00Y88SktNAtRVBoXQ.png
*/

/*
    Coroutines are very similar to threads.

    However, coroutines are cooperatively multitasked,
    whereas threads are typically preemptively multitasked.

    This means that coroutines provide concurrency but not parallelism.

    From: https://en.wikipedia.org/wiki/Coroutine#Comparison_with_threads
*/

/*
    Drawbacks Threads:
        - Expensive.
             require context switches which are costly.
        - Not infinite.
             The number of threads that can be launched is limited by the underlying operating system.
             In server-side applications, this could cause a major bottleneck.
        - Not always available.
             Some platforms, such as JavaScript do not even support threads
        - Not easy.
             Debugging threads, avoiding race conditions are common problems we suffer in multi-threaded programming.
*/

/*
    Other async mechanisms and their properties:

    * Callbacks
        - Difficulty of nested callbacks
        - Error handling is complicated
    * Futures, promises, and others
        - Different programming model
        - Different APIs
        - Specific return type
        - Error handling can be complicated
    * Reactive extensions (Rx)
        - Different programming model
            - Observer pattern
            - Streams
        - Consistent API experience
        - Nicer approach to error handling

    From: https://kotlinlang.org/docs/async-programming.html
 */

/*
    Coroutine internals:
    https://doordash.engineering/2021/11/09/the-beginners-guide-to-kotlin-coroutine-internals/
*/


// Now, suppose we want to fetch a lot of data from github (repos and all contributors within those)
// and display this data within a UI without blocking it, what are the options? See resources/../comparison.

// That being said, let's test how light coroutines are vs threads:

fun testHowLightCoroutinesAre() {
    val c = measure(10_000, ::coroutine)
    val t = measure(10_000, ::thread)
    println("These threads are ${(t / c).round(2)} times slower than coroutines.")
}

private fun coroutine() {
    GlobalScope.launch { /*noop*/ }
}

private fun thread() {
    thread { /*noop*/ }
}

fun main() {
    testHowLightCoroutinesAre()
}


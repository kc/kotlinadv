package com.infosupport.demos.h12.async

import com.infosupport.demos.h8.higherorderfunctions.Person
import com.infosupport.demos.h8.higherorderfunctions.people
import kotlinx.coroutines.*

// Coroutines
// async await use case

fun main() {
    println("main start")
    getNamesAsync(people)
    println("main end")
}

fun getNamesAsync(persons: List<Person>) =
    runBlocking {
        persons
            .map { getNameAsync(it) }
            .forEach { println(it.await()) }
    }

suspend fun getNameAsync(p: Person): Deferred<String> {
    println("getNameAsync for $p")
    val async = GlobalScope.async {
        val ms = rand(4000)
        delay(ms)
        log(p, ms)
        p.name
    }
    println("getNameAsync request fired for $p; waiting for response....")
    return async
}

private fun log(p: Person, ms: Long) = println("getting ${p.name} took $ms ms.")

private fun rand(max: Long) = (Math.random() * max).toLong()

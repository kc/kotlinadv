package com.infosupport.demos.h12.async

import com.infosupport.demos.h8.higherorderfunctions.Person
import com.infosupport.demos.h8.higherorderfunctions.people
import kotlinx.coroutines.*

// Coroutines
// async await use case

fun main() = runBlocking {
    println("main start")
    printNamesAsync(people)
    println("main end")
}

suspend fun printNamesAsync(persons: List<Person>) =
    persons
        .map { getNameFromDbAsync(it) }
        .forEach { println("Got person's name: ${it.await()}") }

suspend fun getNameFromDbAsync(p: Person): Deferred<String> = coroutineScope {
    println("getNameAsync for $p")
    val deferredName = async {
        val ms = rand(4000)
        delay(ms)
        log(p, ms)
        p.name
    }
    println("getNameFromDbAsync request fired for $p; waiting for response....")
    deferredName
}

private fun log(p: Person, ms: Long) = println("getting ${p.name} took $ms ms.")


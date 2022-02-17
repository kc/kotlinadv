package com.infosupport.demos.h8.higherorderfunctions

import java.io.BufferedReader
import java.io.StringReader
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

// Inlining

// 1. Concept and benefits
fun filterPeople() {

    // Q: Which one performs better?
    imperativeStyle()
    functionalStyle()

    // A: In Java, lambdas are slower because anonymous class is generated,
    //    for captured variables new objects are created, calls are made to
    //    these classes and objects, which introduces runtime overhead.

    //    In Kotlin, filter is declared inline which means that the bytecode of the lambda
    //    is literally inlined (by the compiler) where it is used, resulting in roughly the same
    //    bytecode as the direct implementation.
}

private fun imperativeStyle() {
    // direct implementation
    val result = mutableListOf<Person>()
    for (person in people) {
        if (person.age < 30) result.add(person)
    }
    println(result)
}

private fun functionalStyle() {
    // indirectly through a lambda
    println(people.filter { it.age < 30 })
}

// 2. Defining and using an inline function -------------------------
inline fun <T> synchronized(lock: Lock, action: () -> T): T {
    lock.lock()
    try {
        return action() // execute lambda
    } finally {
        lock.unlock()
    }
}

fun usingSynchronized(lock: Lock) {
    // Looks just like Java
    println("Before sync")
    synchronized(lock) {    // lambda is known at compile time, can be inlined
        println("Action")
    }
    println("After sync")
}

//    this translates to this bytecode:
fun pseudoByteCodeOfUsingSynchronized(lock: Lock) {
    println("Before sync")

    // synchronized is inlined here:
    lock.lock()             // inlined code of synchronized(...)
    try {
        println("Action")   // lambda body is also inlined
    } finally {
        lock.unlock()
    }

    println("After sync")
}

// 3. Inlining when lambda is passed as parameter (here: body) ---------------------------
class LockOwner(val lock: Lock) {
    fun runUnderLock(body: () -> Unit) { // higher order function
        synchronized(lock, body)         // lambda is NOT known at compile time, can NOT be inlined!
    }
}

// ... which translates to this bytecode:
class LockOwnerByteCode(val lock: Lock) {
    fun runUnderLock(body: () -> Unit) {
        // synchronized is inlined here:
        lock.lock()             // inlined code of synchronized(...)
        try {
            body()              // ... but lambda can't get inlined!!
        } finally {
            lock.unlock()
        }
    }
}

//    therefore, this code can't completely be inlined:
fun inliningCallsInASequence() =
    people.asSequence()
        .filter { it.age > 10 } // Sequence.filter is NOT declared as inline; the lambda is passed
        .toList()               // to a new object FilteringSequence(..); can't be inlined in Sequence.filter.

//    this code can be inlined:
fun inliningCallsInCollectionAPI(): List<Person> {
    return people // no sequence
        .filter { it.age > 10 } // Iterable.filter is declared inline, because it executes predicate (almost) itself.
        .toList()
    // But, this code creates an intermediate collection for each
    // intermediate operation. So, for large collections, this becomes
    // a concern.
}

//     Conclusion:
//     Q: When to use sequence, when not?
//     A: With large collections (no inlining, but no large intermediate collections).
//        Not with small collections, so you can benefit from inlining.
//        and the intermediate collections are small, so no problem.

// 4. Inlining with resources -------------------------------------------------
var sharedResource = 9

fun synchronizedInKotlin() {
    val lock = ReentrantLock()
    lock.withLock { // declared inline
        sharedResource++
    }
}

fun tryWithResourcesInKotlin(): String? {
    BufferedReader(StringReader("ABC\nDEF")).use { // declared inline
        return it.readLine()
    }
}

// 5. Storing Inlined Parameter for future use is not allowed:
inline fun inlinedFilterStoresInlinedParameter(list: List<Int>, predicate: (Int) -> Boolean): List<Int> {
    // val tryToStoreInlinedParameter = predicate // not allowed!
    return list.filter(predicate)
}

// 6. Conclusion ------------------------------------
//
// Q: When to inline, when not?
// A: Do:
//      - when functions that take lambdas as arguments
//      - to reduce the amount of overhead of class and object creation
//      - JVM already does inlining, but not always
//      - to be able to use special features, such as non-local returns
//    Don't:
//      - to prevent large byte code size, especially with large inline functions
//      - to get a clearer stacktrace

fun main() {
    filterPeople()

    usingSynchronized(lock)
    LockOwner(lock).runUnderLock(action)

    inliningCallsInASequence().forEach { println(it) }
    inliningCallsInCollectionAPI().forEach { println(it) }

    synchronizedInKotlin()
    tryWithResourcesInKotlin()
}

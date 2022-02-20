package com.infosupport.demos.h11

// Scope functions

// The Kotlin standard library contains several functions whose sole purpose
// is to execute a block of code within the context of an object.

// In this scope, you can access the object without its name. Such functions are called scope functions.
// There are five of them: let, run, with, apply, and also.
// They differ in scope object and return value: https://kotlinlang.org/docs/scope-functions.html#function-selection

val pairs = mutableMapOf(1 to "one")

fun main() {

    // Some examples ...

    val pairsToo: MutableMap<Int, String> =
        pairs
            .apply {
                this[2] = "two"
                replace(2, "twee") // returns this
            }

    val with: Unit =
        with(pairs) {
            this[3] = "three"
            replace(3, "drie")
        }

    val let: String? = pairs.let {
        it[4] = "four"
        it.replace(4, "vier")
    }

    // Apply as Person builder:
    val bram = Person().apply {
        name = "Bram"
        age = 42 // returns this
    }

    bram.run {
        name = "Abraham Janssens"
        firstname()
    }.also {
        println(it ?: "No firstname...")
    }

    // without run
    bram.name = "Abraham Janssens"
    val firstname = bram.firstname()
    println(firstname ?: "No firstname...")

    bram.let {
        it.age?.plus(10)
        it.copy()
    }.also {
        println("The copied person is: ")
        println(it)
    }

    // Q: What's the signature of these functions?
    // Q: How can we create these ourselves? See next...
}





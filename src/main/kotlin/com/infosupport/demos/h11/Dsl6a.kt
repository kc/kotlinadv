package com.infosupport.demos.h11

// Kotlin DSLs in practice

// 1. Chaining infix
// How can we implement this:
// assert { "kotlin" should start with "kot" } --> OK
// assert { "bram" should start with "abc" }   --> NOK

// Some infix calls are made here, so let's first convert it to method calls:
// "kotlin".should(start).with("kot")

// Apparently, should and with are the methods, which should become infix functions.

// Starting from the left:
// infix fun String.should(x: ??1??): ??2??

// Q: What are ??1?? and ??2??
/* A: 1 is just a word for the DSL's grammar; let's take an object for that, i.e. an empty, static, global val.
      2 is for chaining, also grammar. On the object, we define `with`
*/

// Solution:

object start

infix fun String.should(dummy: start): StartWrapper = StartWrapper(this)

class StartWrapper(val value: String) {
    infix fun with(prefix: String) {
        if (!value.startsWith(prefix))
            throw AssertionError(""""$value" does not start with "$prefix"""")
    }
}

fun main() {
    assert { "kotlin" should start with "kot" }
    assert { "bram" should start with "abc" }
}

fun assert(assertion: () -> Unit) {
    try {
        assertion()
        println("OK")
    } catch (e: AssertionError) {
        System.err.println("NOK ${e.message}")
    }
}


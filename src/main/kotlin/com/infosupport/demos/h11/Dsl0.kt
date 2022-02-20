package com.infosupport.demos.h11

import java.util.*

// Building structured APIs: lambdas with receivers in DSLs
// Lambdas with receivers and extension function types

fun main() {
    // Suppose we want to create a function to build a string.

    // Lambda with receiver:
    println(
        // buildString gets a lambda WITH a StringBuilder
        buildString { sb ->
            sb.append("snessnaj marb")
            sb.reverse()
            sb.capEachWord()
        }
    )

    println(
        // same, but now using it
        buildString {
            it.append("retsek ellej naj")
            it.reverse()
            it.capEachWord()
        }
    )

    println(
        // buildStringDSL gets a lambda ON a StringBuilder
        buildStringDSL {
            // we're in the scope of StringBuilder, so we can access members of StringBuilder directly here
            append("ytinummoc avaj")
            reverse()
            capEachWord()
        }
    )

    //  Create your own "language elements"
    //  I created my own forEach:
    forEach(persons) {
        // we're in the scope of Person, so we can access members of Person directly here
        birthday()

        // if person has separate firstname, print it
        firstname()?.let { println("Person firstname is $it") }

        println("Person is: $this")
    }

    forEach((-2..3).toList()) {
        // we're in the scope of Int, so we can access members of Int directly here
        val posneg = if (this.compareTo(0) == 1) "positive" else "negative"
        println("Number $this is $posneg.")
    }

}

fun buildString(body: (StringBuilder) -> Unit): String {
    val sb = StringBuilder()
    body(sb) // call lambda WITH sb as param
    return sb.toString()
}

fun buildStringDSL(body: StringBuilder.() -> Unit): String {
    val sb = StringBuilder()
    body(sb) // call lambda WITH sb as param
    return sb.toString()
}

private fun StringBuilder.capEachWord() {
    val sj = StringJoiner(" ")

    val wordsCapped = split(" ").map { it.first().uppercase() + it.substring(1 until it.length) }
    wordsCapped.forEach { sj.add(it) }

    clear()
    append(sj.toString())
}

fun <T> forEach(items: List<T>, body: T.() -> Unit) {
    for (item in items) {
        body(item)
    }
}

data class Person(var name: String? = "", var age: Int? = null, private val i: Int = 0)

fun Person.birthday(): Int? = age?.plus(1)
fun Person.firstname() = name?.substringBefore(" ")

val persons = listOf(
    Person("Bram Janssens", 28),
    Person("Alice", 29)
)

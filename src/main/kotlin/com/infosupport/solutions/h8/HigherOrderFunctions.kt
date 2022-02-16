package com.infosupport.solutions.h8

import com.infosupport.labs.h8.Person
import com.infosupport.labs.h8.peekInlined
import com.infosupport.labs.h8.peekNotInlined

// 1.
// a. b.
fun String.filter(predicate: (Char) -> Boolean = { it != ' ' }): String {
    val sb = StringBuilder()
    for (c in this) {
        if (predicate(c)) sb.append(c)
    }
    return sb.toString()
}

// c.
fun List<String>.avgLengthFor(predicate: (String) -> Boolean) = avgLengthFor({ s: String -> s.length }, predicate)

// d.
fun <T> List<T>.avgLengthFor(
    counter: ((T) -> Int) = { it.toString().length },
    predicate: ((T) -> Boolean)? = null
): Double {
    var length = 0
    var count = 0

    fun process(s: T) {
        length += counter(s)
        count++
    }

    this.filter { s -> predicate?.invoke(s) ?: true }.forEach(::process)

    // imperative style:
    // for (s in this) {
    //     if (predicate == null) process(s)
    //     else if (predicate(s)) process(s)
    // }

    return (length.toDouble() / count)
}

fun main() {
    testFilter()
    testC()
    testD()

    returnFromNonInlined()
    returnFromInlined()
}

fun testFilter() {
    println("ab1c".filter { it in 'a'..'z' })
    println("123bc56".filter { it in '0'..'9' })
    println("ab c".filter())
}

fun testC() {
    println(listOf("123", "abc", "de", "f").avgLengthFor { it.all { c -> c in 'a'..'z' } } == 2.0)
    println(listOf("123", "abc", "hans@email.de", "pierre@croissant.fr").avgLengthFor { it.contains('@') } == 16.0)
    println(listOf(123, 45678, 9, 0).avgLengthFor({ it.toString().length }, { it > 100 }) == 4.0)
}

fun testD() {
    println(listOf(123, 45678, 9, 0).avgLengthFor() == 2.5) // (average length of all these digits)
    println(listOf(123, 45678, 9, 0).avgLengthFor(predicate = { it > 100 }) == 4.0) //(idem, but for digits greater than 100)
    println(listOf(123, 45678, 9, 0).avgLengthFor({ it.toString().length }) { it > 100 } == 4.0) // (idem)
    println(listOf(123, 45678, 9, 0).avgLengthFor({ it }, { it > 100 }) == 22900.5) // (average value of all digits greater than 100)
    println(listOf(Person("Bram", 41), Person("Pieter", 10)).avgLengthFor({ it.name.toString().length }) == 5.0)
    println(listOf(Person("Bram", 41), Person("Pieter", 10)).avgLengthFor(Person::age) { it.age > 40 } == 41.0)
}

// 2.
// See https://medium.com/tompee/idiomatic-kotlin-inline-functions-e39b2f90a291

// 3.
fun returnFromNonInlined() {
    val list = listOf(1, 2, 3)
    val newList = list.peekNotInlined {
        println("peeking $it")
        return@peekNotInlined
        // return // not allowed
    }

    println(newList)
}

fun returnFromInlined() {
    val list = listOf(1, 2, 3)
    val newList = list.peekInlined {
        println("peeking $it")
        return@peekInlined
        // return // allowed, but code below isn't executed

    }
    println(newList)
}

// 4.
// See Atomic Kotlin

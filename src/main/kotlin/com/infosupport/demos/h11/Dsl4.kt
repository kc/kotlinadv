package com.infosupport.demos.h11

import com.infosupport.labs.h8.Person

// More flexible block nesting with the “invoke” convention
// The “invoke” convention: objects callable as functions

// First, we define some `operator fun invoke` on a class
class Greeter(val greeting: String) {
    operator fun invoke(name: String) {
        println("$greeting, $name!")
    }

    operator fun invoke(number: Int) {
        println("$greeting, $number!")
    }

    operator fun invoke(name: String, number: Int) {
        println("$greeting, $name, you're $number!")
    }
}

// Next, we can invoke a variable, with a string and/or int
fun invokeDemo() {
    val bavarianGreeter = Greeter("Servus")

    bavarianGreeter("Dmitry")
    bavarianGreeter(42)
    bavarianGreeter("Dmitry", 42)
}

// Why is this useful? When the class is a function type:

// Example, with primitive
class GreaterThan(private val base: Int) : (Int) -> Boolean { // extends function type!
    override fun invoke(i: Int) = i > base // mandatory override
}

fun invokeUseCase1() {
    listOf(1, 2, 3, 4)
        .filter(GreaterThan(2))
        .forEach { println(it) }
}

// Another example, with non primitives
class OlderThan(private val base: Int) : (Person) -> Boolean {
    override fun invoke(i: Person) = i.age > base // especially useful when this logic is too complicated for a lambda
}

fun invokeUseCase2() {
    listOf(Person("Bram", 41), Person("Mary", 42), Person("Jane", 43))
        .filter(OlderThan(42))
        .forEach { println(it) }
}

// Another example, with non primitives
class OlderThanPerson(private val base: Person) : (Person) -> Boolean {
    override fun invoke(i: Person) = i.age > base.age
}

fun invokeUseCase3() {
    val sint = Person("Sinterklaas", 350)
    listOf(Person("Opa", 80), Person("Beppe", 94), Person("Santa", 360))
        .filter(OlderThanPerson(sint))
        .forEach { println(it) }
}

fun main() {
    invokeDemo()
    invokeUseCase1()
    invokeUseCase2()
    invokeUseCase3()
}

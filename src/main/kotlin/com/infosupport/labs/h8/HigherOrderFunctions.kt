package com.infosupport.labs.h8

// 1. Let’s reimplement one of the most commonly used standard library functions:
//    the filter function. To keep things simple, you’ll implement the filter function on String,
//    but the generic version that works on a collection of any element is similar.
//    Its declaration is shown here:
fun String.filter(predicate: (Char) -> Boolean): String {
    TODO()
}

//    The filter function takes a predicate as a parameter.
//    The result is true if the character passed to the predicate needs to be present in the
//    resulting string, or false otherwise.
//    Examples:
fun testFilter() {
    println("ab1c".filter { it in 'a'..'z' })
    println("123bc56".filter { it in '0'..'9' })
}

// a. Implement and test filter.
// b. Now, give predicate a default value (e.g. to filter spaces), so
//    println("ab c".filter()) ==> abc

// c. Create an extension method avgLengthFor on List<String> which returns the average
//    length of the strings, where the strings have to meet a given predicate.
//    Its declaration is shown here:
fun List<String>.avgLengthFor(/* TODO */): Double = TODO()

//    Examples (uncomment when ready):
fun testC() {
    /*
    println(listOf("123", "abc", "de", "f").avgLengthFor { it.all { c -> c in 'a'..'z' } } == 2.0)
    println(listOf("123", "abc", "hans@email.de", "pierre@croissant.fr").avgLengthFor { it.contains('@') } == 16.0)
    println(listOf(123, 45678, 9, 0).avgLengthFor({ it.toString().length }, { it > 100 }) == 4.0)
    */
}

// d. Overload your method from c so that is works on all types and you can specify the way it counts the length.
//    - Generify it with a type parameter T
//    - Create a new first parameter `counter`, with default value `length of toString of element`
//    - Make the second parameter `predicate` nullable with default value null.

//    Examples (uncomment when ready):
fun testD() {
    /*
    println(listOf(123, 45678, 9, 0).avgLengthFor() == 2.5)                          // (average length of all these digits)
    println(listOf(123, 45678, 9, 0).avgLengthFor(predicate = { it > 100 }) == 4.0)  // (idem, but for digits greater than 100)
    println(listOf(123, 45678, 9, 0).avgLengthFor({ it.toString().length }) { it > 100 } == 4.0) // (idem)
    println(listOf(123, 45678, 9, 0).avgLengthFor({ it }, { it > 100 }) == 22900.5)  // (average value of all digits greater than 100)
    println(listOf(Person("Bram", 41), Person("Pieter", 10)).avgLengthFor({ it.name.toString().length }) == 5.0)
    println(listOf(Person("Bram", 41), Person("Pieter", 10)).avgLengthFor(Person::age) { it.age > 40 } == 41.0)
    */
}

fun main() {
    testFilter()
    testC()
    testD()
}

// 2. Take a look at Inlining.kt. Compile this code and decompile it with
//    Tools -> Kotlin -> Show Kotlin Byte code, and then click [Decompile].
//    Analyse the decompiled classes, compare inlined v.s. not inlined,
//    regarding the following issues:
//      - Is the inlined function body inlined to the call-site?
//      - Is the lambda parameter body also inlined to the call-site?
//      - How much larger does the code become?
//      - Is the inlined function body inlined to the call-site when it is declared outside the call-site?

// 3.
// Change the following two functions. To jump out of the lambda, add:
//  - a local return, or
//  - a non-local return
// Experiment and see what happens.
fun returnFromNonInlined() {
    val newList = listOf(1, 2, 3).peekNotInlined {
        println("peeking $it")
        // TODO jump
    }

    println(newList)
}

fun returnFromInlined() {
    val newList = listOf(1, 2, 3).peekInlined {
        println("peeking $it")
        // TODO jump
    }

    println(newList)
}

// If time permits:

// 3. Do (some of) the following exercise from Atomic Kotlin:
//    Functional Programming        Exercise
//      - Higher-Order Functions:   1, 3, 4



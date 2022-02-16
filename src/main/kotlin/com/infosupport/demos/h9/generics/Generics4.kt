package com.infosupport.demos.h9.generics

// Declaring generic functions and classes
// Type parameter constraints

// T must be a Number
fun <T : Number> List<T>.mySum(): Int = this.sumOf { it.toInt() }

// allowed calls:
val sum1 = listOf(1, 2, 3).mySum()
val sum2 = listOf(1.0, 2.0, 3.0).mySum()

// not allowed calls:
// val sum3 = listOf(1.0, 2.0, 3).sum()
// val sum4 = listOf("a", "b").sum()

// ------------

// T must be comparable
fun <T : Comparable<T>> max(first: T, second: T): T {
    return if (first > second) first else second
}

// allowed calls:
val max = max("kotlin", "java")

// not allowed calls:
// val max = max("kotlin", 42)

// ------------
//  Specifying multiple constraints for a type parameter

fun <T> ensureTrailingPeriod(seq: T) where T : CharSequence, T : Appendable {
    if (!seq.endsWith('.')) {
        seq.append('.')
    }
}

// allowed calls:
val hwperiod = ensureTrailingPeriod(StringBuilder("Hello World"))

// not allowed calls:
// val hwperiod = ensureTrailingPeriod("Hello World") // String is not appendable

// BTW, you can use this syntax for one constraint too:
fun <T> List<T>.sum2(): Int where T : Number = this.sumBy { it.toInt() }

fun main() {
    println(sum1)
    println(sum2)
    println(max)

    println(hwperiod)
}

package com.infosupport.demos.h9.generics

// Declaring generic functions and classes
// Type parameter constraints

// sum() of a list: T must be the same numeric type, so
val sum0 = listOf(1, 2, 3).sum()

// not allowed calls:
// val sum1 = listOf(1.0, 2.0f, 3).sum() // not the same
// val sum2 = listOf("a", "b").sum()    // not numeric

// We can relax this with a bound on T:
// T must be a Number
fun <T : Number> List<T>.mySum(): Int = this.sumOf { it.toInt() }

// allowed calls now:
val sum3 = listOf(1, 2, 3).mySum()
val sum4 = listOf(1.0, 2.0f, 3).mySum()

// ------------
// Other example, where T must be comparable
fun <T : Comparable<T>> greatest(first: T, second: T): T {
    return if (first > second) first else second
}

// allowed calls:
val max = greatest("kotlin", "java")

// not allowed calls:
// val max = greatest("kotlin", 42)

// ------------
//  Specifying multiple constraints for a type parameter with `where`:
fun <T> ensureTrailingPeriod(seq: T) where T : CharSequence, T : Appendable { // T must be CharSequence AND Appendable
    if (!seq.endsWith('.')) {
        seq.append('.')
    }
}

// allowed calls:
val hwperiod = ensureTrailingPeriod(StringBuilder("Hello World"))

// not allowed calls:
// val hwperiod = ensureTrailingPeriod("Hello World") // String is not appendable

// BTW, you can use above syntax for one constraint too:
fun <T> List<T>.sum2(): Int where T : Number =
    this.sumOf { it.toInt() }

fun <T> greatest2(first: T, second: T) where T : Comparable<T> =
    if (first > second) first else second

fun main() {
    println(sum3)
    println(sum4)
    println(max)

    println(hwperiod)
}

package com.infosupport.demos.h9.generics

// Variance: generics and subtyping
// Use site variance

// Previous in/out declarations are called declaration site variance.
// There's also use site variance

// Classes like MutableList, aren’t covariant or contravariant in a general case, because they can
//  both produce and consume values of types specified by their type parameters.
//  But it’s common for a variable of that type in a particular function to be used in only one of
//  those roles: either as a producer or as a consumer. For example, consider this simple function.
fun <T> copyData(
    source: MutableList<T>, // source and destination have to contain the same type
    destination: MutableList<T>
) {
    for (item in source) {
        destination.add(item)
        // source.add(item) // would be allowed
    }
}

// To make this function work with lists of different types, you can introduce a second generic parameter.
fun <T : R, R> copyDataTwoTypeParams(
    source: MutableList<T>,
    destination: MutableList<R>
) {
    for (item in source) {
        destination.add(item)
        // source.add(item) // would be allowed
    }
}

// Kotlin provides a more elegant way to express this:
fun <T> copyData2(
    source: MutableList<out T>, // type projection; for source, we can use T in out position only; read only; allows source elements to be a subtype of destination elements
    destination: MutableList<T>
) {
    for (item in source) {      // source produces values (out)
        destination.add(item)   // destination consumes them (in)
        // source.add(item)     // not allowed: consuming on producer
    }

}

fun main() {
    val ints = mutableListOf(1, 2, 3)
    val intsCopy = mutableListOf<Int>()
    val anyItems = mutableListOf<Any>()

    copyData(ints, intsCopy)
    println(intsCopy)

    // not allowed:
    // copyData(ints, anyItems)
    // println(anyItems)

    copyDataTwoTypeParams(ints, anyItems)
    println(anyItems)

    copyData2(ints, anyItems)
    println(anyItems)
}

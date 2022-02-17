package com.infosupport.demos.h9.generics

import java.util.*

// Runtime generics: reified type parameters

// At runtime, <...> is erased:
// Reified means: type parameters aren't erased but materialized,
// which means you can refer to actual type arguments at runtime.

// This only works with inline functions:
inline fun <reified T> isA(value: Any) = value is T // you can use T here!

// Does not compile with non inline:
// fun <T> isA(value: Any) = value is T
// fun <reified T> isA(value: Any) = value is T

fun callIsA() {
    println(isA<Int>(1))
    println(isA<Int>("1"))
    println(isA<List<Int>>(listOf(1)))
    println(isA<List<Int>>(1))
}

// Not with normal functions:
// fun <T> isA(value: Any) = value is T // does not compile

// Other examples
// 1.
fun filterIsInstance() {
    val items = listOf("one", 2, "three")
    println(items.filterIsInstance<String>().map { it.length })
    // Note: result of filterIsInstance<String>() is also typed (List<String>), so no need for casting; it is String!

    // This isn't possible in Java, but filterIsInstance is defined inline with reified type parameter.
    // String is preserved in the inlined byte code. Check in decompiled byte code.
    // Only works when inlining is possible.
}

// 2.
fun replaceClassReferenceByReifiedTypeParameters() {
    val serviceImpl = ServiceLoader.load(Service::class.java).findFirst().orElseThrow() // equivalent to Service.class in Java
    val serviceImplToo = load<Service>() // Much more concise and reusable: pass type as reified type parameter
}

inline fun <reified T> load(): T =
    // you can use T here
    ServiceLoader.load(T::class.java).findFirst().orElseThrow()

// 3.
fun assertThrowsDemo() {
    // see unit test
    throw IllegalArgumentException("Reified T")
}

fun main() {
    callIsA()
    filterIsInstance()
    replaceClassReferenceByReifiedTypeParameters()
}

private interface Service

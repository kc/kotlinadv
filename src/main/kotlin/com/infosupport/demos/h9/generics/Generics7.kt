package com.infosupport.demos.h9.generics

import java.util.*

// Runtime generics: reified type parameters

// At runtime, <...> is erased:
// Reified means: type parameters aren't erased but materialized,
// which means you can refer to actual type arguments at runtime.
// This only works with inline functions:
inline fun <reified T> isA(value: Any) = value is T
// fun <T> isA(value: Any) = value is T // does not compile with non inline

fun callIsA() {
    println(isA<Int>(1))
    println(isA<Int>("1"))
    println(isA<List<Int>>(listOf(1)))
    println(isA<List<Int>>(1))
}

// Not with normal functions:
// fun <T> isA(value: Any) = value is T // does not compile

// Other examples

fun filterIsInstance() {
    val items = listOf("one", 2, "three")
    println(items.filterIsInstance<String>().map { it.length })
    // Note: result of filterIsInstance<String>() is also typed (List<String>), so no need for casting; it is String!

    // This isn't possible in Java, but filterIsInstance is defined inline with reified type parameter.
    // String is preserved in the inlined byte code. Check in decompiled byte code.
    // Only works when inlining is possible.
}

fun replaceClassReferenceByReifiedTypeParameters() {
    val serviceImpl = ServiceLoader.load(Service::class.java) // equivalent to Service.class in Java
    val serviceImplToo = load<Service>() // Much more concise: pass type as reified type parameter
}

internal class Service

inline fun <reified T> load() =
    ServiceLoader.load(T::class.java) // you can use T here

fun main() {
    callIsA()
    filterIsInstance()
    replaceClassReferenceByReifiedTypeParameters()
}

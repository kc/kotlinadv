package com.infosupport.demos.h9.generics

// Variance: generics and subtyping

// Why variance exists: passing an argument to a function ---------

// 0) Imagine that you have a function that takes a Number as an argument.
fun makeDouble(n: Number): Double {
    return n.toDouble()
}

// Q: Is it safe to pass an Int to this function?
// A: Yes, because Int -|> Number
val d = makeDouble(1)

// Other types than numbers are of course not allowed:
// val s = makeDouble("1")
// val s = makeDouble(Person(42))

// What happens when Number and Int become type arguments?

// 1) Imagine that you have a function that takes a List<Number> as an argument.
//    It can only read from the list.
fun processList(list: List<Number>) {
    list.forEach { println(it) } // read from list
    //                              write not possible on Lists
}

// Q: Is it safe to pass a List<Int> to this function?
// A: Yes:
val safe = processList(listOf(1, 2))
// List<T> is said to be covariant: it preserves the subtyping relation:
//      Int  -|>      Number
// List<Int> -|> List<Number>

// Q: How is List<T> defined?
// A: See its source.

// 2) Imagine that you have a function that takes a MutableList<Number> as an argument.
//    It can read from AND write to the list.
fun processMutableList(list: MutableList<Number>) {
    list.forEach { println(it) } // read from list
    list.add(42f)                // write element, allowed since Float -|> Number
}

// Q: Is it safe to pass a MutableList<Int> to this function?
// No!
// val unsafe = processMutableList(mutableListOf<Int>(1, 2)) // doesn't compile

// If this was allowed you would get a runtime error at the write action:
//      ClassCastException: Float cannot be cast to Int
//      Float doesn't belong in a List<String>
// , which would break type safety of generics at compile time.
// Same for other subtypes of Number.

// Q: Is it allowed to pass a MutableList<Any> to this function?
// No!
// val unsafe = processMutableList(mutableListOf<Any>(1, 2)) // doesn't compile
// If this was allowed you would get a runtime error at the write action too.

// MutableList is called invariant:
// Int -|> Number, but
// MutableList<Int> isn't substitutable for MutableList<Number>

// Number -|> Any, but
// MutableList<Any> isn't substitutable for MutableList<Number>
// No subtyping relationship.

// Q: How is MutableList<T> defined?
// A: See its source.


// So
// A: It is allowed, but only for List, not for MutableList.

// How can we relax this a bit more, without losing safety? 
// By making co/contravariant functions: see 9b.

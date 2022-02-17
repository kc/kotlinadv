package com.infosupport.demos.h9.generics

// Variance: covariant on functions

fun covariant(list: MutableList<out Number>) { // out = producer = read only
    list.forEach { println(it) } // read from list
    // list.add(42f)             // write element not allowed, since MutableList can `out`put Numbers only
}

fun contravariant(list: MutableList<in Number>) { // in = consumer = write (and read)
    list.forEach { println(it) } // read from list
    list.add(42f)                // write element allowed too
}

// What can we pass to these functions?

fun passToCovariant() {
    // OK: mutable list of Number and all its subtypes
    covariant(mutableListOf<Number>())
    covariant(mutableListOf<Int>())
    covariant(mutableListOf<Float>())
    //                     <etc. >

    // NOK: the rest (supertypes and unrelated types)
    // covariant(mutableListOf<Any>())    // super type
    // covariant(mutableListOf<String>()) // unrelated type
    // covariant(mutableListOf<Person>()) // unrelated type
}

fun passToContavariant() {
    // OK: mutable list of Number and all its supertypes:
    contravariant(mutableListOf<Number>())
    contravariant(mutableListOf<Any>())

    // NOK: the rest (subtypes and unrelated types)
    // contravariant(mutableListOf<Int>())   // otherwise it could write a float in MutableList<Int>
    // contravariant(mutableListOf<Float>()) // otherwise it could write an int  in MutableList<Float>
    //                            <etc. >

    // contravariant(mutableListOf<String>()) // unrelated type
    // contravariant(mutableListOf<Person>()) // unrelated type
}

// So: define covariance of a parameter of some function f with `out`:
//     f(param: SomeClass<out Number>)
// The function can only read from the param.

// So: define contravariance of a parameter of some function f with `in`:
//     f(param: SomeClass<in Number>)
// The function can read to and write from the param.

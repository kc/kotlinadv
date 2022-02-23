package com.infosupport.demos.h9.generics

import com.infosupport.demos.h11.Person

// Variance: generics and subtyping
// Covariance and contravariance on functions

// Use site variance
fun covariant(list: MutableList<out Number>) { // out = producer = read only
    list.forEach { println(it) } // read from list
    // list.add(42f)             // write element not allowed, since MutableList can `out`put Numbers only
}

// What can we pass to this function?
fun passToCovariant() {
    // OK: mutable list of Number and all its subtypes
    covariant(mutableListOf<Number>())
    covariant(mutableListOf<Int>())
    covariant(mutableListOf<Float>())
    //                     <etc.>

    // NOK: the rest (supertypes and unrelated types)
    // covariant(mutableListOf<Any>())    // super type
    // covariant(mutableListOf<String>()) // unrelated type
    // covariant(mutableListOf<Person>()) // unrelated type
    //                        <etc.>
}

fun contravariant(list: MutableList<in Number>) { // in = consumer = write (and read)
    list.forEach { println(it) } // read from list
    list.add(42f)                // write element allowed too
    list.add(42)                 //
}

// What can we pass to this function?
fun passToContavariant() {
    // OK: mutable list of Number and all its supertypes:
    contravariant(mutableListOf<Number>())
    contravariant(mutableListOf<Any>("One", Person()))

    // NOK: the rest (subtypes and unrelated types)
    // contravariant(mutableListOf<Int>())   // otherwise it could write a float in MutableList<Int>
    // contravariant(mutableListOf<Float>()) // otherwise it could write an int  in MutableList<Float>
    //                            <etc.>

    // contravariant(mutableListOf<String>()) // unrelated type
    // contravariant(mutableListOf<Person>()) // unrelated type
    //                            <etc.>
}

// So: you define a covariant parameter of some function f with `out`:
//     f(param: SomeClass<out Type>)
// The function can only read from the param: param is a producer.
// As type argument you can pass Type and its subtypes.

// So: you define a contravariant parameter of some function f with `in`:
//     f(param: SomeClass<in Type>)
// The function can read to and write from the param: param is a consumer and producer.
// As type argument you can pass Type and its supertypes.

// In JAVA:   PECS (Producer extends Consumer super)
// In Kotlin: POCI (Producer out Consumer in)

package com.infosupport.demos.h9.generics

// Variance: generics and subtyping

// Why variance exists: passing an argument to a function ---------

// Q: Imagine that you have a function that takes a List<Any> as an argument.
//    Is it safe to pass a variable of type List<String> to this function?

// this only reads the list
fun printContents(list: List<Any>) {
    println(list.joinToString())
}

// A1: therefore, this is safe.
fun callprintContents() {
    printContents(listOf("abc", "bac"))
    // works because string is any.
}

// This one also modifies the list...
fun addAnswer(list: MutableList<Any>) {
    list.add(42)
}

// A2: this is NOT safe.
fun callAddAnswer() {
    val strings = mutableListOf("abc", "bac")
    // addAnswer(strings) // doesn't compile

    // If this was allowed you would get:
    // ClassCastException: Integer cannot be cast to String
    // Int doesn't belong in a List<String>
}

// A: you can do it, but you're not allowed to modify the list.

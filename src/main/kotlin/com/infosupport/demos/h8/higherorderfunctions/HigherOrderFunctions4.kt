package com.infosupport.demos.h8.higherorderfunctions

//
// Optional challenge
//

// Simple class to hold a string value
data class Data(val value: String)

// f1 takes a lambda with Data receiver, without arguments
fun f1(action: Data.() -> Unit) {
    action(Data("f1"))
}

// f2 takes a lambda with a single Data argument
fun f2(action: (Data) -> Unit) {
    action(Data("f2"))
}

fun main() {
    // What is printed here? Why?
    f1 { f2 { println(value) } }

    // Same thing, written differently:
//    f1 {
//        f2 {
//            println(value)
//        }
//    }

    // Intermediate results:
//    f1 { println(value) }
//    f2 { println(value) }
}

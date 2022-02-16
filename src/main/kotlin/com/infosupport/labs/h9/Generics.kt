package com.infosupport.labs.h9

// 1. Do (some of) the following exercises from Atomic Kotlin:
//    Power Tools               Exercise
//      - Creating generics:    1 (hint: you'll need to declare Items as 'fun interface...')
//                              3 (use code below as main code)

/*
    fun main() {
        val catBox: Box<Cat> = Box(Cat())
        val outBoxAny: OutBox<Any> = catBox
        val a: Any = outBoxAny.get()
        val inBoxAny: InBox<Any> = Box(217)
        val inBoxCat: InBox<Any> = inBoxAny
        inBoxCat.put(Cat())

    }
*/

// 2. Given is the domain in Domain.kt including a example list of disposable items.
//    Now, create a function `select(): List<String>` so that this code compiles and the tests succeed:

/*
import com.infosupport.labs.eq
import com.infosupport.labs.h9.*

fun main() {
    select<Compost>() eq "[Orange Peel, Apple Core]"
    select<Donation>() eq "[Couch, Clothing]"
    select<Recyclable>() eq "[Plastic, Metal, Cardboard]"
    select<Landfill>() eq "[Trash]"

}
*/



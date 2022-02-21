package com.infosupport.solutions.h9

import com.infosupport.labs.eq
import com.infosupport.labs.h9.*

// 1.
// See Atomic Kotlin

// 2.
inline fun <reified T : Disposable> select(): List<String> = disposables.filterIsInstance<T>().map { it.name }

fun main() {
    select<Compost>() eq "[Orange Peel, Apple Core]"
    select<Donation>() eq "[Couch, Clothing]"
    select<Recyclable>() eq "[Plastic, Metal, Cardboard]"
    select<Landfill>() eq "[Trash]"
}

// 3.
// See Atomic Kotlin

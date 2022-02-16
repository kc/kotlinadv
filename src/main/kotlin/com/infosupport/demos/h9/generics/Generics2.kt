package com.infosupport.demos.h9.generics

// Declaring generic functions and classes

// 1. Generic Functions
fun <T> List<T>.mySlice(idx: IntRange): List<T> = this.slice(idx)
//  ^^^
//  type parameter, used in receiver and return type

val alphabet = ('a'..'z').toList()
val letters1 = alphabet.mySlice<Char>(0..2)
val letters2 = alphabet.mySlice(0..10)

fun <T> List<T>.myFilter(predicate: (T) -> Boolean): List<T> = filter(predicate)
//  ^^^
//  type parameter, used in receiver, argument and return type

// 2. Generic Properties
val <T> List<T>.penultimate: T
    get() = this[size - 2]

val penul = listOf(1, 2, 3, 4).penultimate

// Not possible on regular property:
// val <T> x: T = TODO()

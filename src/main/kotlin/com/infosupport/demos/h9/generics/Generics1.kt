package com.infosupport.demos.h9.generics

// Declaring generic functions and classes
// Type inference

// This is mostly recap, and some special cases for kotlin.

// Type of val is inferred from values in list:
val authors = listOf("Dmitry", "Svetlana")

// No values: specify type explicitly, either way:
val readers1: MutableList<String> = mutableListOf()
val readers2 = mutableListOf<String>()

// Kotlin doesn't support raw types, i.e. val listraw: List. Always use a type argument.

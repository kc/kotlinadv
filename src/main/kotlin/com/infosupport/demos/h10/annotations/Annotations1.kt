package com.infosupport.demos.h9.generics

// Declaring and applying annotations

// Applying -------------------------------------------------------------

// Applying is very similar like in Java.
// ReplaceWith is an annotation as well, but here as param, so without @
@Deprecated("Use removeAt(index) instead.", ReplaceWith("removeAt(index)"))
fun remove(index: Int): Unit = TODO()

fun removeAt(index: Int): Unit = TODO()

fun removeDemo() {
    remove(0) // alt-enter --> removeAt!
}

// Annotations can have parameters of the following types only:
//                                  Example
// - primitive types,               @MyAnnotation(1)
// - strings,                       @MyAnnotation("1")
// - enums,                         @MyAnnotation(Numbers.ONE)
// - class references,              @MyAnnotation(Numbers::class)
// - other annotation classes, and  @MyAnnotation(One)
// - arrays thereof.                @MyAnnotation(arrayOf("4","2"))

// They must be known at compile time, so no var/val allowed (only const).

// Targets -------------------------------------------------------------

// Example:
class TempFolder {
    @get:SuppressWarnings // only apply @SuppressWarnings to the getter of this property
    val folder = "TemporaryFolder"
}

// Syntax:
// @<use-site target>:<Annotation>

// Some supported use-site targets:
// - property       — Java annotations can’t be applied with this use-site target.
// - field          — Field generated for the property.
// - get            — Property getter.
// - set            — Property setter.
// ... (full list: see https://kotlinlang.org/docs/reference/annotations.html#annotation-use-site-targets)

// Some annotations to control how declarations written in Kotlin are compiled to Java bytecode:
// - @JvmName       — Changes the name of a Java method or field generated from a Kotlin declaration

object Static {
    @JvmStatic //   — to expose as static Java method.
    var x = 0

    @JvmOverloads //— generate overloads for a function that has default parameter values
    fun over(i: Int = 0, j: Int): Int = TODO()
}

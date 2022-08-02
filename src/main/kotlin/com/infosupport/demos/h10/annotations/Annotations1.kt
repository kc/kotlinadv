package com.infosupport.demos.h10.annotations

// Declaring and applying annotations
// - Applying
// - Use-site target
// - Controlling the Java API

// 1. Applying ------------------------------------------------
// Applying is very similar to in Java.
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
// - arrays of them.                @MyAnnotation(arrayOf("4","2"))

// They must be known at compile time, so no var/val allowed (only const).

// 2. Use-site target -------------------------------------------------------------
// Use-site target: only apply an annotation to a part of the (Java) code
// Syntax: @<use-site target>:<Annotation>

// Example:
class TempFolder {
    @get:SuppressWarnings // only apply @SuppressWarnings to the getter of this property
    val folder = "TemporaryFolder"
}

// Some supported use-site targets:
// - property       — Java annotations can’t be applied with this use-site target.
// - field          — Field generated for the property.
// - get            — Property getter.
// - set            — Property setter.
// ... (full list: see https://kotlinlang.org/docs/reference/annotations.html#annotation-use-site-targets)

// 3. Controlling the Java API ----------------------------------------------------------------------
// Some annotations to control how declarations written in Kotlin are compiled to Java bytecode:
// @JvmName          — Changes the Java name generated from a Kotlin declaration
// @JvmStatic        — expose as static Java method
// @JvmOverloads     — generate overloads for a function that has default parameter values

object Static {
    @JvmStatic
    var x = 0

    @get:JvmName("someY") // combined with use site target
    var y = 1

    @JvmOverloads
    fun over(i: Int = 0, j: Int): Int = 2*i + j
}

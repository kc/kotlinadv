package com.infosupport.demos.h7.conventions

// Destructuring declarations and component functions

fun main() {
    // data class supports destructuring out of the box
    val (a, b) = Point(10, 20)
    println("($a, $b)")

    // For Point3D, you have to implement operators component1/2/3 yourself by convention
    val (x, y, z) = Point3D(10, 20, 30)
    println("($x, $y, $z)")

    // Useful when you want to return multiple values from a function:
    val (name, ext) = splitFilename("Conventions4.kt")
    println("""File "$name" has extention "$ext".""")

    // Using these conventions, you can iterate over a map directly:
    for ((key, value) in pointMap) {
        println("$key -> $value")
    }
}

class Point3D(val x: Int, val y: Int, val z: Int) {
    operator fun component1() = x
    operator fun component2() = y
    operator fun component3() = z
}

fun splitFilename(fullName: String): Pair<String, String> {
    val (name, extension) = fullName.split('.', limit = 2) // destructured
    return Pair(name, extension)
}


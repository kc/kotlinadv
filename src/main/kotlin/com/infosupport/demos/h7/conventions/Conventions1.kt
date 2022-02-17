package com.infosupport.demos.h7.conventions

// Overloading arithmetic operators + - ! ++ -- / % += -=
// See https://kotlinlang.org/docs/operator-overloading.html

fun main() {
    unaryOperators()
    binaryOperators()
}

// --------------------------------------------
fun unaryOperators() {
    println("unary ---------------")

    var p1 = Point(1, 2)
    var p2 = Point(-1, -2)

    println(-p1)
    println(+p2)
    println(!p2)
    println(++p1)
    println(--p2)
}

// --------------------------------------------
fun binaryOperators() {
    arithmetic()
    bitwise()
    compound()
}

private fun arithmetic() {
    println("arithmetic ---------------")
    // plus
    val p1 = Point(10, 20)
    val p2 = Point(30, 40)
    println(p1 + p2)
    println(p1.plus(p2))

    // others
    println(((p1 % 3) - p2 * Point(2, 2)) / Point(10, 10))
    println(p2 * 1.5)

    // Order matters:
    println("This point is " + p1) // String.plus
    println(p1 + "This point is")  // Point.plus

}

private fun bitwise() {
    println("bitwise ---------------")

    // bitwise operators are infix
    println(0x0F and 0xF0)
    println(0x0F or 0xF0)
    println(0x1 shl 4)

    println(Point(1, 1) shl 4)
}

private fun compound() {
    println("compound ---------------")

    var p = Point(1, 2)
    p += Point(3, 4) // p must be var since it's overwritten
    println(p)

    val mp = MutablePoint(10, 20)
    mp += Point(30, 40) // mp can be val, x and y are mutable, mp stays the same object, implement plusAssign
    println(mp)

    val numbers = ArrayList<Int>()
    numbers += 42 // numbers is val: plusAssign on mutable list, mutates the list
    println(numbers)

    val newNumbers = numbers + listOf(43, 44) // plus returns a new collection
    println(newNumbers)
}

// It's common to define operators as extension functions ----------------------------

// Unary operators
operator fun Point.unaryMinus() = Point(-x, -y)
operator fun Point.unaryPlus() = Point(if (x < 0) -x else x, if (y < 0) -y else y)
operator fun Point.not() = -this
operator fun Point.inc() = Point(x + 1, y + 1)
operator fun Point.dec() = Point(x - 1, y - 1)

// Binary operators
// - Arithmetic
operator fun Point.minus(other: Point) = Point(x - other.x, y - other.y)
operator fun Point.times(other: Point) = Point(x * other.x, y * other.y)
operator fun Point.div(other: Point) = Point(x / other.x, y / other.y)
operator fun Point.rem(other: Point) = Point(x % other.x, y % other.y) // was: mod

// -- You can overload operators with different argument types
operator fun Point.plus(s: String) = "$s [$x, $y]."
operator fun Point.times(scale: Double) = Point((x * scale).toInt(), (y * scale).toInt())
operator fun Point.rem(i: Int) = Point(x % i, y % i)

// - Bitwise: operators are infix
infix fun Point.shl(bitCount: Int) = Point(x shl bitCount, y shl bitCount)

// - Compound: plus for immutable, plusAssign for mutable
operator fun MutablePoint.plusAssign(other: Point): Unit { // must be Unit
    x += other.x
    y += other.y
}







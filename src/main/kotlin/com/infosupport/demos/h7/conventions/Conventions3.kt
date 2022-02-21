package com.infosupport.demos.h7.conventions

import java.time.LocalDate

// Conventions used for collections and ranges
// - Indexed access: a[i] a[i, j] a[i, .. , n]
// - in operator:    a in b
// - range to:       a .. b
// - iterator:       for(x in a .. b)

fun main() {
    accessByIndex()
    inConvention()
    rangeToConvention()
    iteratorConvention()
}

fun accessByIndex() {
    println("accessByIndex ---------------")

    // Accessing elements by index: “get” and “set”
    val value = pointMap["2"]    // get
    pointMap["4"] = Point(4, 4)  // set

    // Implement "indexers" get and set for Point
    val p = Point(100, 200)
    println(p["x"])
    println(p["y"])

    val mp = MutablePoint(0, 0)
    mp["x"] = 42
    mp["y"] = 84
    println(mp)

    mp["x", "y"] = 0
    println(mp)
}

fun inConvention() {
    println("inConvention ---------------")

    val rect = Rectangle(Point(10, 20), Point(50, 50))
    println(Point(20, 30) in rect) // calls Rectangle.contains
    println(Point(5, 5) in rect)
}

fun rangeToConvention() {
    println("rangeToConvention ---------------")

    // With Int
    val intRange = 0..10 // call rangeTo
    println(2 in intRange)

    // With dates
    val now = LocalDate.now()
    val vacation = now..now.plusDays(10)
    println(now.plusWeeks(1) in vacation)

    // With points: overload rangeTo for Point OR let Point implement Comparable
    val point = Point(2, 2)
    val straightLine = Point(0, 0)..Point(10, 10) // == Point(0,0).rangeTo(Point(10,10))
    println(point in straightLine)
}

fun iteratorConvention() {
    println("iteratorConvention ---------------")

    // To be able to iterate over a range of Points, implement ClosedRange<Point>.iterator()
    for (p in Point(0, 0)..Point(10, 10)) {
        println(p)
    }
}

// These are operator funs
// allows val i = p[key]
operator fun Point.get(key: String) =
    when (key) {
        "x" -> x
        "y" -> y
        else -> throw IndexOutOfBoundsException("Invalid coordinate $key.")
    }

// allows p[key] = value
operator fun MutablePoint.set(key: String, value: Int) =
    when (key) {
        "x" -> x = value
        "y" -> y = value
        else -> throw IndexOutOfBoundsException("Invalid coordinate $key.")
    }

// allows p[key1, key2] = value
operator fun MutablePoint.set(key1: String, key2: String, value: Int) {
    this[key1] = value
    this[key2] = value
}

// allows point `in` rectangle
operator fun Rectangle.contains(p: Point): Boolean {
    return p.x in upperLeft.x until lowerRight.x &&
            p.y in upperLeft.y until lowerRight.y
}

// allows for(p in p1..p2)
operator fun ClosedRange<Point>.iterator(): Iterator<Point> =
    object : Iterator<Point> {
        var current = start
        override fun hasNext() = current < endInclusive
        override fun next() = current++
    }


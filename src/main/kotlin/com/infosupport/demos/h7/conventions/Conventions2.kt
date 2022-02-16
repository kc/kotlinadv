package com.infosupport.demos.h7.conventions

// Overloading comparison operators.
// - Equality operator:  ==
// - Ordering operators: >, <, >=, <=

fun main() {
    equality()
    comparing()
}

fun equality() {
    // How to call and implement equality?
    println("equality ---------------")

    val v1 = Value("abc")
    val v2 = Value("abc")

    // Idiomatic call: calls equals which is NOT an operator fun, and checks on nulls.
    println(v1 == v2)

    // Two equivalent implementations:
    println(equals1(v1, v2) == equals2(v1, v2))

    // Both check for nulls
    println(equals1(v1, v2))
    println(equals1(v1, null))
    println(equals1(null, v1))
    println(equals1(null, null))

    println(equals2(v1, v2))
    println(equals2(v1, null))
    println(equals2(null, v1))
    println(equals2(null, null))
}

fun comparing() {
    // How to call and implement comparison?
    println("comparing ---------------")

    val v1 = Value("abc")
    val v2 = Value("bcd")

    // Idiomatic call: calls compareTo (operator fun)
    println(v1 > v2)
    println(v1 <= v2)
    println("abc" < "bcd") // operators also works for Strings
}

class Value(val s: String) {

    // Implementation as regular override
    override fun equals(other: Any?): Boolean {
        if (this === other) return true // note the identity operator ===, which is the same as == in Java (reference check)
        if (other !is Value) return false
        return this.s == other.s
    }

    // Implementation needed when overriding equals
    override fun hashCode() = s.hashCode()

    // Implementation as operator overload (or override if this class wants to implement Comparable)
    operator fun compareTo(v2: Value) = s.compareTo(v2.s)
}

// Option 1
fun equals1(v1: Value?, v2: Value?): Boolean {
    // Idiomatic equals.
    // Under the hood, calls .equals(..) _and_ checks on nulls, see equals2.
    return v1 == v2
}

// Option 2
fun equals2(v1: Value?, v2: Value?): Boolean {
    // Implementation without using '==', but equivalent.
    return v1?.equals(v2) ?: (v2 == null) // v1 is null in ?:
}


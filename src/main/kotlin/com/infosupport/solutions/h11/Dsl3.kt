package com.infosupport.solutions.h11

import com.infosupport.demos.h11.assert

fun main() {
    // 1. Implement this:
    assert { 42 should be greaterThan 10 }
    assert { 42 should be smallerThan 10 }

    // 2. Is this also possible?
    // not really possible: there should be an uneven amount of words for infix to succeed
    assert { (42 should be).greaterAttempt thanAttempt 10 }

    // possible: uneven amount of words
    assert { 42 should be greater than number 10 }
}

// 1. ------------------------------------------

object be

infix fun Int.should(dummy: be) = BeWrapper(this)

class BeWrapper(val value: Int) {

    infix fun greaterThan(x: Int) {
        if (value <= x) throw AssertionError(""""$value" is not greater than "$x"""")
    }

    infix fun smallerThan(x: Int) {
        if (value >= x) throw AssertionError(""""$value" is not smaller than "$x"""")
    }

}

// 2. ------------------------------------------

// attempt:
val BeWrapper.greaterAttempt get() = GreaterWrapper(value) // must be a property, and therefore cannot be infix

// last word must be infix
class GreaterWrapper(val value: Int) {
    infix fun thanAttempt(i: Int) {
        if (value <= i) throw AssertionError(""""$value" is not greater than "$i"""")
    }
}

// better:
object than
object number

infix fun BeWrapper.greater(dummy: than) = GreaterWrapper(value)

infix fun GreaterWrapper.number(i: Int) {
    if (value <= i) throw AssertionError(""""$value" is not greater than "$i"""")
}


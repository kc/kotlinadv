package com.infosupport.demos.h11

import java.time.LocalDate
import java.time.LocalDate.now
import java.time.Period

// Kotlin DSLs in practice

// 2. Defining extensions on primitive types: handling dates
// How can we implement this:
// val yesterday = 1.days.ago
// val tomorrow = 1.days.fromNow

// days is a property on Int; let's define an extension property for that:
val Int.days: Period get() = Period.ofDays(this)

// ago is a property on what days returns, e.g. a Period; let's define an extension property for that
val Period.ago: LocalDate get() = now() - this

// fromNow is also a property on what days returns
val Period.fromNow: LocalDate get() = now() + this

fun main() {
    println(1.days.ago)
    println(1.days.fromNow)
}

// You can find the full implementation of the library, supporting all
// time units and not just days, in the kxdate library on GitHub (https://github.com/yole/kxdate).

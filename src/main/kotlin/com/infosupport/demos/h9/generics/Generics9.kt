package com.infosupport.demos.h9.generics

// Variance: generics and subtyping
// Classes v.s. types and subtypes

// In simple cases, the term `type` and `class` are equivalent,
// e.g. class String can be used as type String.

// Generally the terms are  NOT equivalent.

// But nullable types provide an example of when `type` isn’t the same as `class`.
// E.g. one class Int creates two types: Int and Int?.
// E.g. one class List<T> creates an infinite amount of types: List<Int>, List<String>, List<List<Double>> etc.

// Sub/super: B is a subtype of A if you can use it when A is expected.
// Example https://drek4537l1klr.cloudfront.net/jemerov/Figures/09fig04.jpg

// In simple cases, subtype means essentially the same thing as subclass, but...

// Nullable types provide an example of when subtype isn’t the same as subclass:
// String-|>String? but String?-|>String isn't true:
// - ok
val s: String = "abc"
val t: String? = s
// - nok
// val u: String? = "null"
// val v: String = u

// So again:
// Q: Is it safe to pass a variable of type List<String> to a function expecting List<Any>?
// A: Yes, so List<String> is a subtype of List<Any>.
// List<T> is said to be covariant: it preserves subtyping relation:
// String-|>Any and List<String> -|> List<Any>

// MutableList<String> wasn't substitutable for MutableList<Any>. So it isn't a subtype.
// Also, MutableList<Any> is not subtype of MutableList<String>, because not substitutable that way.
// MutableList is called invariant.

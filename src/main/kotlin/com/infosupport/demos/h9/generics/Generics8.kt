package com.infosupport.demos.h9.generics

// Variance: generics and subtyping
// Classes v.s. types and subtypes

// In simple cases, the term `type` and `class` are equivalent,
// e.g. class String can be used as type String.

// Generally the terms are  NOT equivalent.
// E.g. one class Int creates two types: Int and Int?.
// E.g. one class List<T> creates an infinite amount of types: List<Int>, List<String>, List<List<Double>> etc.

// Sub/super: B is a subtype of A if you can use it when A is expected.
// B -|> A
// Example https://drek4537l1klr.cloudfront.net/jemerov/Figures/09fig04.jpg

// In simple cases, subtype means essentially the same thing as subclass, e.g.
// Int extends Number, Int -|> Number

// but...

// Nullable types provide an example of when subtype isn’t the same as subclass:
// String -|> String? but
// String?-|> String isn't true

// - ok:
val s: String = "abc"
val t: String? = s

// - nok:
// val u: String? = "null"
// val v: String = u

// Now, we know that:
//   Int -|> Number
// but is the same true for generified classes, e.g.
//   Int -|> Number ?=>? List<Int> -|> List<Number>

// Let's find out...

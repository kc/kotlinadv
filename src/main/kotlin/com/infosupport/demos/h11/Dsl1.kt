package com.infosupport.demos.h11

fun main() {
    applyAndWithDemo()
}

fun applyAndWithDemo() {
    val map = mutableMapOf(1 to "one")

    val apply: MutableMap<Int, String> =
        map.apply { this[2] = "two" }   // Q: what's the signature of apply?
    val with: Unit =
        with(map) { this[3] = "three" } // Q: what's the signature of with?

}

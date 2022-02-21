package com.infosupport.solutions.h7

// 1.

class Repository<T>(initSize: Int = 10) {
    private val list: MutableList<T?> = MutableList(initSize) { null }
    override fun toString() = list.joinToString(", ")

    operator fun plusAssign(value: T) {
        list.indexOf(null).let { if (it != -1) list[it] = value else list.add(value) }
    }

    operator fun set(index: Int, value: T) {
        if (list[index] == null) list[index] = value else list.add(value)
    }

    operator fun get(index: Int): T {
        return list[index] ?: throw IllegalArgumentException()
    }
}

fun main() {
    val r = Repository<Int>(5)
    (1..7).forEach {
        r += it * 10
    }

    r[4] = 99

    println(r[7])
}

// 2.
// a) TODO
// b) TODO

// 3.
// See Atomic Kotlin.

// 4.
// a)
// See Atomic Kotlin.
// b)
// See Atomic Kotlin.

package com.infosupport.demos.h7.conventions

import kotlin.reflect.KProperty

// The framework: --------------

open class Entity(open val id: Long)
open class Column<T>(val name: String, length: Int? = null) {
    init {
        val l = if (length != null) "($length)" else ""
        println("Column $name$l created.")
    }

    fun select(e: Entity) = "select $name from ${e} where id=${e.id}"
}

class StringColumn(name: String, length: Int = -1) : Column<String>(name, length) {
    operator fun getValue(e: Entity, desc: KProperty<*>): String {
        println(select(e))
        return if (e is User) "Bram" else "Kotlin for developers" // some hard coded database values...
    }
}

class IntColumn(name: String) : Column<Int>(name) {
    operator fun getValue(e: Entity, desc: KProperty<*>): Int {
        println(select(e))
        return if (e is User) 41 else 3 // some hard coded database values...
    }
}

operator fun <T> Column<T>.setValue(e: Entity, col: KProperty<*>, value: T) {
    println("update ${e::class.java.simpleName} set ${name}=$value where id=${e.id}")
}

fun integer(s: String) = IntColumn(s)

fun varchar(s: String, i: Int) = StringColumn(s, i)

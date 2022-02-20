package com.infosupport.demos.h10.annotations

import ru.yole.jkid.JsonExclude
import ru.yole.jkid.JsonName
import ru.yole.jkid.deserialization.deserialize
import ru.yole.jkid.serialization.serialize

// Declaring and applying annotations

// Using annotations to customize JSON serialization -------------------------------------------

fun main() {
    jkidDemo()
}

fun jkidDemo() {
    val person = Person("Alice", 29)
    println(serialize(person))

    val json = """{"name": "Alice", "age": 29}"""
    println(deserialize<Person>(json))

}

data class Person(
    @JsonName("alias") val firstName: String, // TODO show source of JsonName (in github clone of jkid)
    @JsonExclude val age: Int? = null         // TODO show source of JsonExclude
)

// TODO tell:
// Annotations have meta annotations as in Java, e.g. @Target.
// However, Kotlin's Retention's default is RUNTIME! :-)



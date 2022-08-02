package com.infosupport.demos.h10.annotations

import ru.yole.jkid.JsonExclude
import ru.yole.jkid.JsonName
import ru.yole.jkid.deserialization.deserialize
import ru.yole.jkid.serialization.serialize

// Declaring and applying annotations
// - Using annotations to customize JSON serialization
// - Declaring annotations, meta annotations

// 1. Using annotations to customize JSON serialization ------------------------------------------
fun main() {
    // Nothing fancy here, just a demo of a usage of annotations and an intro for the next source.
    jkidDemo()
}

fun jkidDemo() {
    val person = Person("Alice", 29)
    println(serialize(person))

    val json = """{"name": "Alice", "age": 29}"""
    println(deserialize<Person>(json))

}

data class Person(
    @JsonName("alias") val firstName: String,
    @JsonExclude val age: Int? = null
)

// 2. Declaring annotations, meta annotations -----------------
// TODO show source of JsonName and JsonExclude (see https://github.com/yole/jkid/blob/60ec0e3284acc2d5d90e0f62aa5ec1c9efcf762d/src/main/kotlin/Annotations.kt)
// nothing fancy, just like we're used from Java

// TODO tell:
// - Annotations have meta annotations as in Java, e.g. @Target.
// - However, Kotlin's Retention's default is RUNTIME! :-)

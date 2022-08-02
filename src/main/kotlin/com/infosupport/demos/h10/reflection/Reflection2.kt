package com.infosupport.demos.h10.reflection

import ru.yole.jkid.ValueSerializer
import java.time.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

// Reflection
// - implementing object serialization using reflection
// - customising serialization with annotations

// How to use the reflection API?
// To explore this, we're implementing JKid as a case study.
// We start with serialize(..).
// Note: given are some utils, see Util.kt!

// Notes to instructor:
//  - this source can be used as explanation or lab
//  - solution can be found in the solutions package

fun main() {
    println(serialize(Person("Bram", 43, 183, hasLicense = true, birthDate = LocalDate.of(1979, 8, 22))))
    // should output (basic):
    // {"birthDate": 1979-08-22, "hasLicense": true, "height": 183, "name": "Bram", "shoeSize": 43}
}

// 1. First the basic serialization with serialize(obj).
// How to build this?
// Well, object in, string out, so we start with:
fun serialize(obj: Any): String = buildString { serializeObject(obj) } // (recall buildString from ch5)

// How to build serializeObject?
fun StringBuilder.serializeObject(obj: Any) {
    TODO()
    // a. read properties of obj

    // b. for each property:
    // - append prop.name to stringbuilder
    // - append :         to stringbuilder
    // - append propvalue to stringbuilder

    // c. separate them with a comma and surround the result with { .. }
    // Hint: use .joinTo(...){..} and the functions in Util.kt
}

// 2. Now, how to implement support for @JsonExclude? TODO()

// 3. Now, how to implement support for @JsonName? TODO()
//    When serializing the property's name, we have to see if
//    it's annotated with @JsonName first. If so, use the alias.

// 4. Now, how to implement support for @CustomSerializer? TODO()
//    We have to change the call to serializePropertyValue(prop.get(obj))
//    We don't just serialize, but try to find the serializer and if available, use that one:
//     val value = prop.get(obj)
//     val jsonValue = prop.getSerializer()?.toJsonValue(value) ?: value
//     serializePropertyValue(jsonValue)

// But how to implement KProperty<*>.getSerializer()?
fun KProperty<*>.getSerializer(): ValueSerializer<Any?>? {
    TODO()
    // This should return an instance of CustomSerializer as a ValueSerializer:
    // - use reflection to find if this property is annotated with @CustomSerializer.
    // - if so, get the corresponding KClass
    // - create an instance (or get "the" instance if this is an object i.e. singleton)
    //   How?
    //      - kClass.objectInstance holds "the" instance
    //      - how to create an instance? See below.
    // - return the instance as a ValueSerializer
}

fun <T : Any> KClass<T>.createInstance(): T {
    TODO()
    // - find the no arg ctor with reflection
    // - if not found,throw exception
    // - call the ctor and return that instance
}



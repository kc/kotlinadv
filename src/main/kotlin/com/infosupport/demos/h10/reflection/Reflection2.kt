package com.infosupport.demos.h10.reflection

import ru.yole.jkid.ValueSerializer
import java.time.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

// Reflection: introspecting Kotlin objects at runtime
// How to use?
// To explore this, we're implementing JKid now.

fun main() {
    println(serialize(Person("Bram", 41, 42, birthDate = LocalDate.of(1979, 8, 22))))
    // should output:
    // {"leeftijd": 41, "birthDate": "22-augustus-1979", "name": "Bram", "shoeSize": 42}
}

// 1. First the basic serialization with serialize(object). See Annotations2.jkidDemo
// How to build this?
// Well, object in, string out, so we start with:
fun serialize(obj: Any): String = buildString { serializeObject(obj) }
// (recall buildString from ch5)

// How to build serializeObject?
fun StringBuilder.serializeObject(obj: Any) {
    // a. read properties of obj

    // b. for each property:
    // - append prop.name to stringbuilder
    // - append :         to stringbuilder
    // - append propvalue to stringbuilder

    // c. separate each [key: value] with comma and surround the whole result with { .. }
    // Hint: use Collections.joinTo(...){..} and the functions in Util.kt
}

// 2. Now, how to implement support for @JsonExclude?

// 3. Now, how to implement support for @JsonName?
//    When serializing the property's name, we have to see if
//    it's annotated with @JsonName first. If so, use the alias.

// 4. Now, how to implement support for @CustomSerializer?
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



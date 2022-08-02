package com.infosupport.solutions.h10

import com.infosupport.demos.h10.reflection.Person
import ru.yole.jkid.CustomSerializer
import ru.yole.jkid.JsonName
import ru.yole.jkid.ValueSerializer
import java.time.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

fun main() {
    println(serialize(Person("Bram", 43, 183, hasLicense = true, birthDate = LocalDate.of(1979, 8, 22))))
}

fun serialize(obj: Any): String = buildString { serializeObject(obj) }

// 1.
fun StringBuilder.serializeObject(obj: Any) {
    val sb = this
    // a. read properties of obj
    val memberProperties = obj.javaClass.kotlin.memberProperties

    // c. separate each [key: value] with comma and surround the whole result with { .. }
    // Hint: use Collections.joinTo(...){..} and the functions in Util.kt

    memberProperties
        /* 2. Now, how to implement support for @JsonExclude? Easy, add:
        .filter { it.findAnnotation<JsonExclude>() == null }
        */
        .joinTo(sb, ", ", "{", "}") {
            // b. for each property:
            // 1.
            serializeProperty(it, obj)

            // OR
            // 3. and 4.:
            // serializePropertyWithSupportForJsonAnnotations(it, obj)
            ""
        }

}

private fun StringBuilder.serializeProperty(it: KProperty1<Any, *>, obj: Any) {
    // - append prop.name to stringbuilder
    serializeAndEscape(it.name)

    // - append :         to stringbuilder
    append(": ")

    // - append propvalue to stringbuilder
    serializePropertyValue(it.get(obj))
}

// 3. Now, how to implement support for @JsonName?

private fun StringBuilder.serializePropertyWithSupportForJsonAnnotations(prop: KProperty1<Any, *>, obj: Any) {
    // - append prop.name to stringbuilder
    // 3.
    val jsonNameAnn = prop.findAnnotation<JsonName>()
    val propName = jsonNameAnn?.name ?: prop.name // if it has an alias, use it
    serializeAndEscape(propName)

    // - append :         to stringbuilder
    append(": ")

    // - append propvalue to stringbuilder
    // 4.
    val value = prop.get(obj)
    // serialize with custom serializer if available
    val jsonValue = prop.getSerializer()?.toJsonValue(value) ?: value
    serializePropertyValue(jsonValue)
}

fun KProperty<*>.getSerializer(): ValueSerializer<Any?>? {
    // - use reflection to find if this property is annotated with @CustomSerializer.
    val customSerializerAnn = findAnnotation<CustomSerializer>() ?: return null

    // - if so, get the corresponding KClass
    val serializerClass = customSerializerAnn.serializerClass

    // - get an instance (or "the" instance if this is an object i.e. singleton)
    val valueSerializer = serializerClass.objectInstance ?: serializerClass.createInstance()

    // - return the instance as a ValueSerializer
    @Suppress("UNCHECKED_CAST")
    return valueSerializer as ValueSerializer<Any?>
}

fun <T : Any> KClass<T>.createInstance(): T {
    val noArgConstructor = constructors.find {
        it.parameters.isEmpty()
    }
    noArgConstructor ?: throw IllegalArgumentException(
        "Class must have a no-argument constructor"
    )

    return noArgConstructor.call()
}

package com.infosupport.demos.h10.annotations

import ru.yole.jkid.CustomSerializer
import ru.yole.jkid.DeserializeInterface
import ru.yole.jkid.ValueSerializer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

// Declaring and applying annotations
// - Classes as annotation parameters
// - Generic classes as annotation parameters

// In Kotlin, annotations can have (generic) classes as parameter:

data class Employee(
    // 1. Class as annotation parameter
    @DeserializeInterface(CompanyImpl::class) val company: Company,
    // TODO show DeserializeInterface source:
    // `annotation class DeserializeInterface(val targetClass: KClass<out Any>)`:
    //   - KClass is Kotlin's java.lang.Class
    //   - It's covariant on Any: you can pass any type "extends Any" (out = producer, Producer Extends (PECS))

    // 2. Generic class as annotation parameter
    @CustomSerializer(DateSerializer::class) val birthDate: Date,
    // TODO show CustomSerializer source:
    // `annotation class CustomSerializer(val serializerClass: KClass<out ValueSerializer<*>>)`:
    //   - It's covariant on ValueSerializer<*>:
    //                            you can pass any type "extends ValueSerializer<*>";
    //                            we must use the star projection syntax <*> here,
    //                            because we know nothing of the types of the properties; it may be any type

    // Not allowed e.g. Date::class, since Date != extends ValueSerializer<*>
    // @CustomSerializer(Date::class) val otherDate: Date

)

interface Company {
    val name: String
}

data class CompanyImpl(override val name: String) : Company

object DateSerializer : ValueSerializer<LocalDate> {
    var dateFormat = DateTimeFormatter.ofPattern("dd-LLLL-yyyy")

    override fun toJsonValue(value: LocalDate): Any? =
        value.format(dateFormat)

    override fun fromJsonValue(jsonValue: Any?): LocalDate =
        LocalDate.from(dateFormat.parse(jsonValue as String))
}

package com.infosupport.demos.h9.generics

import ru.yole.jkid.CustomSerializer
import ru.yole.jkid.DeserializeInterface
import ru.yole.jkid.ValueSerializer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

// Declaring and applying annotations

// Classes as annotation parameters

interface Company {
    val name: String
}

data class CompanyImpl(override val name: String) : Company

data class Employee(
    val name: String,

    //                    class as parameter:
    @DeserializeInterface(CompanyImpl::class) val company: Company,

    // TODO show DeserializeInterface source:
    // annotation class DeserializeInterface(val targetClass: KClass<out Any>):
    // - Covariant: you can pass any type "extends Any" (in Java: ? extends Object)
    // - KClass is like java.lang.Class

    @CustomSerializer(DateSerializer::class) val birthDate: Date

    // TODO show CustomSerializer source:
    // annotation class CustomSerializer(val serializerClass: KClass<out ValueSerializer<*>>)
    // - Covariant: you can pass any type "extends ValueSerializer<*>"

)

object DateSerializer : ValueSerializer<LocalDate> {
    var dateFormat = DateTimeFormatter.ofPattern("dd-LLLL-yyyy")

    override fun toJsonValue(value: LocalDate): Any? =
        value.format(dateFormat)

    override fun fromJsonValue(jsonValue: Any?): LocalDate =
        LocalDate.from(dateFormat.parse(jsonValue as String))
}

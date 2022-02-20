package com.infosupport.demos.h10.reflection

import com.infosupport.demos.h10.annotations.DateSerializer
import ru.yole.jkid.CustomSerializer
import ru.yole.jkid.JsonExclude
import ru.yole.jkid.JsonName
import ru.yole.jkid.deserialization.JKidException
import java.time.LocalDate
import kotlin.reflect.KParameter

class Person(
    val name: String,                             // regular prop
    @JsonName("leeftijd") var age: Int,           // prop with alias in json
    shoeSize: Int,                                // prop with custom setter
    @JsonExclude val hasLicense: Boolean = false, // prop excluded from json
    @CustomSerializer(DateSerializer::class)
    val birthDate: LocalDate                      // prop with custom serializer
) {

    var shoeSize: Int = shoeSize                  // custom setter
        set(newSize: Int) {
            require(newSize > 0)
            field = newSize
        }

    fun incAge(i: Int) {                         // regular method
        age += i
    }

}

data class Book(val title: String, val author: Author)
data class Author(val name: String, val age: Int)

fun <T> ClassInfo<*>.getConstructorParameter(propertyName: String): KParameter = jsonNameToParamMap[propertyName]
    ?: throw JKidException("Constructor parameter $propertyName is not found for class $className")

fun <T> ClassInfo<*>.getDeserializeClass(propertyName: String) = jsonNameToDeserializeClassMap[propertyName]

fun <T> ClassInfo<*>.deserializeConstructorArgument(param: KParameter, value: Any?): Any? {
    val serializer = paramToSerializerMap[param]
    if (serializer != null) return serializer.fromJsonValue(value)

    validateArgumentType(param, value)
    return value
}

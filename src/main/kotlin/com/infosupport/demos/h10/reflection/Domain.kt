package com.infosupport.demos.h10.reflection

import com.infosupport.demos.h10.annotations.DateSerializer
import ru.yole.jkid.CustomSerializer
import ru.yole.jkid.JsonExclude
import ru.yole.jkid.JsonName
import ru.yole.jkid.deserialization.JKidException
import java.time.LocalDate
import kotlin.reflect.KParameter

class Person(
    val name: String,                                                 // regular prop
    _shoeSize: Int,                                                   // field with custom setter

    @JsonName("lengte") var height: Int,                              // prop with alias in json
    @JsonExclude val hasLicense: Boolean = false,                     // prop excluded from json
    @CustomSerializer(DateSerializer::class) val birthDate: LocalDate // prop with custom serializer
) {

    var shoeSize: Int = _shoeSize                  // custom setter
        set(newSize) {
            require(newSize > 0)
            field = newSize
        }

    fun incHeight(i: Int) {                       // regular method
        height += i
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

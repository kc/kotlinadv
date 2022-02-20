package com.infosupport.demos.h10.reflection

import ru.yole.jkid.ValueSerializer
import ru.yole.jkid.deserialization.ClassInfoCache
import ru.yole.jkid.deserialization.JKidException
import ru.yole.jkid.deserialization.ObjectSeed
import ru.yole.jkid.deserialization.Parser
import java.io.StringReader
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

// Reflection: introspecting Kotlin objects at runtime
// How to use?

// Now, we're studying the implementation of deserialize<Type>(json).
// Example call:
val json = """{"title": "Catch-22", "author": {"name": "J. Heller", "age": 42}}"""
val b = deserialize<Book>(json)

// How to define and implement?
// Input is: reified type param T, string
// Output is: an instance of T
inline fun <reified T : Any> deserialize(json: String): T {
    val targetClass = T::class      // we can create an instance since T is reified
    val reader = StringReader(json) // we use a StringReader to stream through the results

    // We won't go into too much detail on parsing the json, but focus
    // on the role of reflection. But we need to know the concepts
    // behind the deserializer a bit.

    // This deserializing consists of three phases:
    // - lexer: divides json string into tokens;
    //      1. character tokens like { : , [
    //      2. value tokens like 'Catch-22' (String) and '42' (Int), or a boolean or null
    // - parser: builds a custom object structure (nodes) from the tokens
    // - deserializer: creates and returns an instance of the class(es) from the object structure

    // a node is called a seed here, and a seed is a JsonObject (see below):
    val rootSeed = ObjectSeed(targetClass, ClassInfoCache())
    // the parser takes the root seed and the reader to build more seeds and relations between them:
    Parser(reader, rootSeed).parse()
    // the spawn method is like Builder.build(): it builds the instance(s), recursively:
    return rootSeed.spawn()
}

// Every seed is a JsonObject, on which the parser can call these methods.
interface JsonObject {
    fun setSimpleProperty(propertyName: String, value: Any?)

    fun createObject(propertyName: String): JsonObject

    fun createArray(propertyName: String): JsonObject
}

// A Seed is a JsonObject with the spawn method, but most importantly, also contains a
// ClassInfoCache, which in turn is a map of (KClass , ClassInfo) entries.
interface Seed : JsonObject {
    val classInfoCache: ClassInfoCache

    fun spawn(): Any?

    // ....
}

// ObjectSeed is a Seed for plain objects (see full source code in jkid repo):
/*
class ObjectSeed<out T : Any>(
    targetClass: KClass<T>,
    override val classInfoCache: ClassInfoCache
) : Seed {
    //...

    // This one calls ClassInfo.createInstance(arguments)
    // with all the parsed arguments ([name, value] pairs ) needed for construction.
    // So we take a look at that one, below.
    override fun spawn(): T = classInfoCache[targetClass].createInstance(arguments)
    //...
}
*/

// Now, ClassInfo is where the reflection magic happens:
class ClassInfo<T : Any>(cls: KClass<T>) {
    // TODO show: uses reflection:
    // - target must have a primary ctor:
    private val constructor = cls.primaryConstructor
        ?: throw JKidException("Class ${cls.qualifiedName} doesn't have a primary constructor")

    // - store the class name:
    val className = cls.qualifiedName

    fun createInstance(arguments: Map<KParameter, Any?>): T {
        ensureAllParametersPresent(arguments)
        // TODO tell:
        // We need KCallable.callBy here instead of KCallable.call. Why?
        // KCallable.call doesn’t support default parameter values.
        // Default ctor parameter values, don’t have to be specified in the JSON.
        // callBy expects a  Map<KParameter, Any?>, including only the mandatory
        // parameters in this case, built by ObjectSeed.
        return constructor.callBy(arguments)
    }

    // TODO show: uses reflection:
    private fun ensureAllParametersPresent(arguments: Map<KParameter, Any?>) {
        for (param in constructor.parameters) {
            if (arguments[param] == null && !param.isOptional && !param.type.isMarkedNullable) {
                throw JKidException("Missing value for parameter ${param.name}")
            }
        }
    }

    // TODO show: uses reflection:
    // called by this.deserializeConstructorArgument (left out of this code)
    // checks if type and value of a prop match:
    fun validateArgumentType(param: KParameter, value: Any?) {
        // TODO show: uses reflection:
        if (value == null && !param.type.isMarkedNullable) {
            throw JKidException("Received null value for non-null parameter ${param.name}")
        }
        if (value != null && value.javaClass != param.type.javaType) {
            throw JKidException("Type mismatch for parameter ${param.name}: expected ${param.type.javaType}, found ${value.javaClass}")
        }
    }

    // some internal data...
    val jsonNameToParamMap = hashMapOf<String, KParameter>()
    val paramToSerializerMap = hashMapOf<KParameter, ValueSerializer<out Any?>>()
    val jsonNameToDeserializeClassMap = hashMapOf<String, Class<out Any>?>()
}

// That's the reflection part. See the complete source code in the JKid repo.

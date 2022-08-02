package com.infosupport.demos.h10.reflection

import java.time.LocalDate
import kotlin.reflect.*
import kotlin.reflect.full.memberProperties

// Reflection: introspecting Kotlin objects at runtime

// The API (see class diagram at https://drek4537l1klr.cloudfront.net/jemerov/HighResolutionFigures/figure_10-6.png)

fun main() {
    apiDemo()
}

fun apiDemo() {
    val person = Person("Alice", 29, 42, birthDate = LocalDate.of(1970, 1, 1))

    val kClass = getKClass(person)
    getProperties(kClass)
    callGetters(kClass, person)
    callMethod(person)
    listParametersOfSetters(kClass)
}

// 1. KClass : KAnnotatedElement
private fun getKClass(person: Person): KClass<Person> {
    println(" -------- getKClass -------- ")
    val jClass = person.javaClass // Java's   reflection API
    val kClass = jClass.kotlin    // Kotlin's reflection API

    println(kClass.simpleName)
    return kClass
}

// 2. KProperty : KCallable : KAnnotatedElement
private fun getProperties(kClass: KClass<Person>) {
    println(" -------- getProperties -------- ")
    val memberProperties = kClass.memberProperties // gets only member props, not extension props

    //                         check this type: KProperty1<..> : KProperty
    memberProperties.forEach { prop: KProperty1<Person, *> -> println(prop.name) }
}

// 3a. KFunction : KCallable : KAnnotatedElement
private fun callGetters(kClass: KClass<Person>, p: Person) {
    println(" -------- callGetters -------- ")
    kClass.memberProperties.forEach {
        val getter = it.getter // getter is a KCallable
        val call = getter.call(p) // p is the object on which we call the getter
        println("${getter.name} = $call")
    }
}

// 3b. KFunction1 : KFunction
private fun callMethod(person: Person) {
    println(" -------- callMethod -------- ")
    // A method is a KFunction too and here, incHeight is a KFunction1: it has one argument.
    val kFunction1: KFunction1<Int, Unit> = person::incHeight
    kFunction1.invoke(3) // invoke instead of call
    println(person.height)

    // TODO tell:
    // Q: Where's the source of KFunctionN?
    // A: Nowhere! They are synthetic compiler generated types. This reduces the size of kotlin-runtime.jar
    //    Moreover, N can be any number:
    var fun10: KFunction10<Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Unit>
    var fun20: KFunction20<Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Unit>
    // .. :-)
}

// 4. KParameter : KAnnotatedElement
fun listParametersOfSetters(kClass: KClass<Person>) {
    println(" -------- listParametersOfSetters -------- ")
    kClass.memberProperties.asSequence()
        .filterIsInstance<KMutableProperty<*>>() // setters only on mutable properties
        .map { it.setter }
        .flatMap { it.parameters }
        .forEach { println("parameter ${it.name}: ${it.type}") }

    /*
    // to see what happens, a more verbose variant:
    kClass.memberProperties.asSequence()
        .filterIsInstance<KMutableProperty<*>>() // setters only on mutable properties
        .map { mp: KMutableProperty<*> ->
            print("property ${mp.name} ")
            mp.setter
        }
        .flatMap { s: KMutableProperty.Setter<out Any?> ->
            println("has setter ${s.name} ")
            s.parameters
        } // check the type
        .forEach { param: KParameter ->
            println("   with parameter ${param.name}: ${param.type}")
        }
    */
}


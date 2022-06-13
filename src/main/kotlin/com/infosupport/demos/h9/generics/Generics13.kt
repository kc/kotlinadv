package com.infosupport.demos.h9.generics

import kotlin.reflect.KClass

// Variance: generics and subtyping
// Star projection using *

// ---------------------------------------------

// Generic method WITH type parameter.
fun <T> printFirst(list: List<T>) {
    if (list.isNotEmpty()) {
        println(list.first())
    }
}

// But T isn't used, so:
fun printFirstStar(list: List<*>) {
    if (list.isNotEmpty()) {
        println(list.first())
    }
}

fun printFirstStarML(list: MutableList<*>) {
    if (list.isNotEmpty()) {
        println(list.first())
    }
    // We can't add, because we don't know the specific type:
    // list.add(...?)

    // So, this method is out only, i.e. covariant.

    // MutableList<*> is projected to (acts as) MutableList<out Any?>:
    // when you know nothing about the type of the element, it’s safe
    // to get elements of Any? type, but it’s not safe to put elements into the list.

    // Note that MutableList<*> != MutableList<Any?>
    // Star is a specific type, Any? is the most generic type.
}

// ---------------------------------------------
// Another example:
// Let’s say you need to validate user input, and you declare an interface FieldValidator.
// It contains its type parameter in the in position only, so it can be declared as contravariant.

interface FieldValidator<in T> {
    fun validate(input: T): Boolean
}

object DefaultStringValidator : FieldValidator<String> {
    override fun validate(input: String) = input.isNotEmpty()
}

object DefaultIntValidator : FieldValidator<Int> {
    override fun validate(input: Int) = input >= 0
}

object DefaultLongValidator : FieldValidator<Long> {
    override fun validate(input: Long) = input >= 0
}

// Now imagine that you want to store all validators in the same container and
// get the right validator according to the type of input.
fun attempt1() {
    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
    validators[String::class] = DefaultStringValidator
    validators[Int::class] = DefaultIntValidator

    // This is not safe, because we can do:
    validators[Int::class] = DefaultStringValidator
    // Int --> String :-(

    // Moreover, we can't use it:
//     validators[String::class]!!.validate("") // does not compile, yields:

    // Error: Out-projected type 'FieldValidator<*>' prohibits the use of
    // 'public abstract fun validate(input: T): Boolean
    // defined in com.infosupport.demos.h9.generics.FieldValidator'

    // We can try this and it will work:
    val stringValidator = validators[String::class] as FieldValidator<String> // warning unchecked cast
    stringValidator.validate("")

    // Unsafe, because we can do:
    val fieldValidator = validators[Int::class] as FieldValidator<String> // warning unchecked cast
    fieldValidator.validate("") // runtime exception

}

// This solution isn’t type-safe and is error-prone. So, let’s investigate other options.

object Validators {
    private val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()

    // Safe: T is a specific type here. T of KClass and FieldValidator
    // must match at compile time when calling this method.
    fun <T : Any> registerValidator(kClass: KClass<T>, fieldValidator: FieldValidator<T>) {
        validators[kClass] = fieldValidator
    }

    // Same.
    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(kClass: KClass<T>): FieldValidator<T> =
        validators[kClass] as? FieldValidator<T> // type safe cast
            ?: throw IllegalArgumentException("No validator for ${kClass.simpleName}")
}

inline fun <reified T : Any> FieldValidator<T>.register() {
    Validators.registerValidator(T::class, this)
}

// Utility function with type inference capabilities
inline fun <reified T : Any> getValidator() = Validators[T::class]

fun attempt2() {
    // Safe, because we can only set:
    Validators.registerValidator(String::class, DefaultStringValidator)
    Validators.registerValidator(Int::class, DefaultIntValidator)

    // ... and this is impossible:
    // Validators.registerValidator(Int::class, DefaultStringValidator)

    // Same for getting:
    println(Validators[String::class].validate("Kotlin"))
    println(Validators[Int::class].validate(42))

    // Impossible:
    // println(Validators[String::class].validate(42))

    // With extension functions
    DefaultLongValidator.register()
    val validator: FieldValidator<Long> = getValidator()
    validate(getValidator())
}

fun validate(validator: FieldValidator<Long>) {}

fun main() {
    // attempt1() // may crash
    attempt2()
}

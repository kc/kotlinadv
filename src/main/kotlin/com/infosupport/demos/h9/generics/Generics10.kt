package com.infosupport.demos.h9.generics

// Variance: generics and subtyping
// Covariance

// Given
open class Species {
    open fun spec() = "Species"
}

open class Animal : Species() {
    fun feed() = println("Feeding ${this::class.simpleName}")
    override fun spec() = "Animal"
}

class Cat : Animal() {
    fun meow() = println("Meow!")
    override fun spec() = "Cat"
}

// This one can produce T's.
// Since it has output, T must be marked out and Producer is therefore covariant:
// - Subtyping is preserved.
// - T can be used in so called "out positions": it can produce T's but not consume T's.
// This protects against illegal use and runtime exceptions.
interface Producer<out T> {
    fun produce(): T     // T is in "out position", allowed.

    // fun consume(t: T) // T is in "in position", not allowed.
}

// Some concrete Producers:
class SpeciesProducer : Producer<Species> {
    override fun produce() = Species()
}

class CatProducer : Producer<Cat> {
    override fun produce() = Cat()

}

class StringProducer : Producer<String> {
    override fun produce() = "String"
}

// Expects a Producer of animal.
// Can be any subtype of Producer<Animal>, since producer is covariant.
fun produceAndFeedAnimal(p: Producer<Animal>) {
    p.produce().feed()
}

fun callProduceAndFeedAnimal() {
    // Works, since Producer<Cat> -|> Producer<Animal>
    produceAndFeedAnimal(CatProducer()) // when you delete the out keyword in Producer, this doesn't compile

    // Doesn't work, since Producer<Species> !-|> Producer<Animal>
    // produceAndFeedAnimal(SpeciesProducer())

    // Doesn't work, since Producer<String> !-|> Producer<Animal>
    // produceAndFeedAnimal(StringProducer())
}

fun main() {
    callProduceAndFeedAnimal()
}

// Using out T in constructor parameters ------------------------

// Allowed, even if it looks like an "in position"
class Herd1<out T : Animal>(vararg animals: T)

// Not allowed, since leadAnimal gets a setter and therefore an "in position"
class Herd2</*out */T : Animal>(var leadAnimal: T, vararg animals: T)

// Allowed, since lead animal now is only a private field without getter/setter
class Herd3<out T : Animal>(private var leadAnimal: T, vararg animals: T)

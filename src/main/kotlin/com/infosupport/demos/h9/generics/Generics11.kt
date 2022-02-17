package com.infosupport.demos.h9.generics

// Variance: generics and subtyping
// Contravariance

// Comparator only uses its T in "in positions":
// interface Comparator<in T> {
//     fun compare(e1: T, e2: T): Int
// }

fun useComparator() {
    val anyComparator = Comparator<Any> { e1, e2 ->
        e1.hashCode() - e2.hashCode()
    }
    val strings = listOf("c", "b", "a")

    // expects a Comparator<String> but Comparator<Any> works too,
    // so Comparator<Any> -|> Comparator<String>
    strings.sortedWith(anyComparator)

    // Contravariance reverses subtyping relation:
    // String -|> Any but Comparator<String> <|- Comparator<Any>
    // Comparator is contravariant.
}

// -----------------------

interface Consumer<in T> {
    // fun produce(): T // T is in "out position", not allowed.
    fun consume(t: T)   // T is in "in position", allowed.
}

// Some concrete Consumers:
class SpeciesConsumer : Consumer<Species> {
    override fun consume(t: Species) {
        println("Consuming ${t.spec()}")
    }
}

class AnimalConsumer : Consumer<Animal> {
    override fun consume(t: Animal) {
        t.feed()
    }
}

class CatConsumer : Consumer<Cat> {
    override fun consume(t: Cat) {
        t.meow()
    }
}

// Expects a Consumer of animal.
// Can be any subtype of Consumer<Animal>, since consumer is contravariant.
fun consumeAnimal(c: Consumer<Animal>, a: Animal) {
    c.consume(a)
}

fun callConsumeAnimal() {
    // Allowed: Consumer<Species> -|> Consumer<Animal>, since Consumer is contravariant
    // A SpeciesConsumer can consume cats and animals, just as the expected Consumer<Animal>.
    // Consuming means calling methods on it.
    // Any method of Species (here: spec()) can also be called on animals, since they are inherited.
    consumeAnimal(SpeciesConsumer(), Cat())
    consumeAnimal(SpeciesConsumer(), Animal())

    // Allowed: Consumer<Animal> -|> Consumer<Animal>
    consumeAnimal(AnimalConsumer(), Cat())
    consumeAnimal(AnimalConsumer(), Animal())

    // Suppose this was allowed, what would happen?
    // consumeAnimal(CatConsumer(), Cat())      // this one could work
    // consumeAnimal(CatConsumer(), Animal())   // this one can't work
    //                                             you can't consume a generic Animal as a specific Cat; an Animal can't meow.
    //                                             i.e. you can't call .meow on Animal.
    // Therefore it doesn't work; Consumer<Cat> !-|> Consumer<Animal>
}

fun main() {
    callConsumeAnimal()
}

// So: define contravariance of some class as
//     SomeClass<in T>
// and SomeClass can only be a consumer of T's.

// ------------------------------------
// A class or interface can be covariant on one type parameter and contravariant on another.
interface Function1<in P, out R> {
    operator fun invoke(p: P): R
}

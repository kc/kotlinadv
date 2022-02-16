package com.infosupport.demos.h9.generics

// Declaring generic functions and classes
// Making type parameters non-null

// By default, T is nullable
class Processor<T> { // implicitly T : Any?
    fun process(value: T) = // T is nullable
        value?.hashCode()   // so safe call needed
}

// allowed:
val nulll: Int? = Processor<String?>().process(null)
val nonnull: Int? = Processor<String>().process("Hello")

// not allowed:
// val nulll = Processor<String>().process(null)

// -------
class ProcessorNonNull<T : Any> { // explicitly T : Any
    fun process(value: T) = // T is non null
        value.hashCode()    // no safe call needed

}

// allowed:
val i: Int = ProcessorNonNull<String>().process("Hi")

// not allowed:
// val stringProcessorNonNull = ProcessorNonNull<String?>()

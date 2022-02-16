package com.infosupport.demos.h9.generics

// Runtime generics: erased type parameters
// More or less the same as in Java

// at runtime, <..> is erased:
val list1: List<String> = listOf("a", "b")
val list2: List<Int> = listOf(1, 2, 3)

// TODO: compare with Generics.java

// In kotlin you always have to specify the type argument:
// fun doSomething(list: List) = println(list) // not allowed (raw type)
fun doSomethingWithList(list: List<*>) { // the correct way
    // but you don't know the element type and whether it's a raw list or typed list
    println(list)
}

// As a general rule, it’s not possible to use types with type arguments in `is` checks.
fun doSomethingWithListInt1(value: Any) {
    // The following code won’t compile:
    // if (value is List<Int>) //...

    // but this will compile
    if (value is List<*>) { // special star projection syntax
        // but you don’t get any information about the element type:
        println("value.sum() wont compile")
    }
}

fun doSomethingWithListInt2(c: Collection<Int>) {
    // this will compile too, since corresponding type information (of c) is already known at compile time:
    if (c is List<Int>) { // allowed
        // now you do get information about the element type
        println(c.mySum()) // smart cast c: Collection<Int> -> List<Int>
    }
}

fun doSomethingWithListInt3(c: Collection<*>) {
    // allowed, but <Int> can't be checked at runtime, only whether it's a List; hence the warning
    val intList = c as? List<Int> // ... gets type `List<Int>?` (nullable)
        ?: throw IllegalArgumentException("List is expected") // when it's not a List<Int>

    // At this point intList has type `List<Int>` (not nullable)

    println(intList.mySum()) // runtime exception when it's not a List<Int>
}

fun doSomethingWithListInt3b(c: Collection<*>) {
    val intList = c as? List<Int> // intList has type `List<Int>?`
    println(intList?.mySum()) // runtime exception when it's not a List<Int>
}

fun main() {
    val listInt = listOf(1, 2, 3)
    val listString = listOf("1", "2", "3")
    val setInt = setOf(1, 2, 3)

    doSomethingWithList(listInt)
    doSomethingWithList(listString)

    doSomethingWithListInt1(listInt)

    doSomethingWithListInt2(listInt)

    doSomethingWithListInt3(listInt)
    // doSomethingWithListInt3(setInt)     // fail
    // doSomethingWithListInt3(listString) // fail

    doSomethingWithListInt3b(listInt)
    doSomethingWithListInt3b(setInt)       // no output
    // doSomethingWithListInt3b(listString)// fail
}

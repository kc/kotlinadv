package com.infosupport.demos.h8.higherorderfunctions

// Control flow in higher-order functions

fun lookForAliceForLoop(people: List<Person>) {
    for (person in people) {
        if (person.name == "Alice") {
            println("Found!")
            return // returns from fun lookForAliceForLoop
        }
    }
    println("Done") // won't be printed when Alice is found
    return
}

fun lookForAliceLambda(people: List<Person>) {
    people.forEach {
        if (it.name == "Alice") {
            println("Found!")
            return // returns from fun lookForAliceLambda == non-local return
        }
    }
    println("Done") // won't be printed when Alice is found
}

fun lookForAliceLambdaLocalReturn(people: List<Person>) {
    people.forEach myLabel@{
        if (it.name == "Alice") {
            println("Found!")
            return@myLabel // returns from forEach == local return, like break in for
        }
    }
    println("Done") // will always be printed
}

fun lookForAliceLambdaLocalReturnToo(people: List<Person>) {
    people.forEach {
        if (it.name == "Alice") {
            println("Found!")
            return@forEach // returns from forEach == local return
        }
    }
    println("Done") // will always be printed
}

fun lookForAliceAnonymousFunction(people: List<Person>) {
    people.forEach(fun(person) { // check this syntax
        if (person.name == "Alice") {
            println("Found!")
            return // returns from anonymous function
        }
    })
    println("Done") // will always be printed
}

fun labeledThis() {
    val apply = StringBuilder().apply sb@{
        listOf(1, 2, 3).apply {
            this.forEach { println(it) }    // "inner"-this: the List
            this@sb.append(this.toString()) // "outer"-this: the StringBuilder
        }
    }
}

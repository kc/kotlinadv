package com.infosupport.solutions.h10

// 1.
class PersonService {
    @Create // TODO: nog uitwerken...
    lateinit var dao: PersonDao
}

annotation class Create

class PersonDao

inline fun <reified T> get(): T? {
    val t = T::class.java
    return t.getConstructor().newInstance()
}

fun main() {
    val personService = get<PersonService>()

    // assertions: see test
}

// 2. See JKid repo, branches "solution-date-format" and "solution-map". See README.md in repo.

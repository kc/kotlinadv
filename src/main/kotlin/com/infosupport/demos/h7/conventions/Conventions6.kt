package com.infosupport.demos.h7.conventions

import java.beans.PropertyChangeListener
import java.time.LocalDate.now

// Lazy loading property
// Delegated properties applications/examples

fun main() {
    lazyLoading()

    summing()
    propertyChangeAware()
    dynamicallyFillingProperties()
    frameworkForEntities()
}

private fun summing() {
    val addition = Add(4, 2)
    println(addition.sum) // sum.getValue is called

    val myOrder = Order(100, 22, now())
    println(myOrder.sum)
}

fun lazyLoading() {
    val p = PersonWithLazyEmails("Bram") // has no emails loaded yet
    println("$p created.")

    val emails = p.emails                // emails are loaded when requested
    println(emails)
}

private fun propertyChangeAware() {
    val p = PersonAwareOfPropertyChanges("Bram", 41, 1000)
    p.addPropertyChangeListener(
        PropertyChangeListener {
            println("${it.propertyName} changed from ${it.oldValue} to ${it.newValue}")
        }
    )
    p.age = 42
    p.salary = 1100
}

fun dynamicallyFillingProperties() {
    val json = hashMapOf(
        "name" to "Bram Dyna",
        "age" to "41",
        "company" to "Info Support"
    )

    val p = PersonFromJson(json)

    println(p.name) // p.getName --> json["name"]
    println(p.age)  // p.getAge  --> json["age"]
    //                 company is ignored
}

fun frameworkForEntities() {
    val u = User(1)
    println(u.name) // get name from db
    u.age += 1      // update age to db

    val c = Course(10)
    println(c.name)     // get from db
    println(c.grade)    // get from db
    c.level += 1        // update to db

}




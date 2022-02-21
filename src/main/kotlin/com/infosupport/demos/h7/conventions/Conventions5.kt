package com.infosupport.demos.h7.conventions

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// Delegated properties: the by, get- and setValue convention

fun main() {
    // TODO show and tell

    // Find the answer to the universe, life and everything...
    withClassicProperty()
    withHelperClass()
    withDelegatedProperty()
    withDelegatedPropertyMulti()
}

fun withClassicProperty() {
    val c = WithClassicProperties(2)
    val oldValue = c.p // getP
    println(oldValue)

    c.p = "4"          // setP
    val newValue1 = c.p
    println(newValue1)

    c.p = "???"
    val newValue2 = c.p
    println(newValue2)

    // same for WithClassicPropertiesToo
    val c2 = WithClassicPropertiesToo(33)
    val oldValue2 = c2.p
    println(oldValue2)
    // ... etc.

}

fun withHelperClass() {
    val wh = WithHelperClass(2)
    val oldValue = wh.p // getP --> helper.getValue
    println(oldValue)

    wh.p = "4"          // setP --> helper.setValue
    val newValue1 = wh.p
    println(newValue1)

    wh.p = "???"
    val newValue2 = wh.p
    println(newValue2)

    // same for WithHelperClassToo
    val wh2 = WithHelperClassToo(33)
    val oldValue2 = wh2.p // getP
    println(oldValue2)
    // ... etc.
}

fun withDelegatedProperty() {
    val w = WithDelegatedProperty(2)

    val oldValue = w.name // getP --> delegate.getValue
    println(oldValue)

    w.name = "4"          // setP --> delegate.setValue
    val newValue = w.name
    println(newValue)

    w.name = "???"
    val newValue2 = w.name
    println(newValue2)
}

fun withDelegatedPropertyMulti() {
    val f = Foo(2)

    val oldValue = f.p // getP --> delegateMulti.getValue
    println(oldValue)

    f.p = "4"          // setP --> delegateMulti.setValue
    val newValue = f.p
    println(newValue)

    f.p = "???"
    val newValue2 = f.p
    println(newValue2)

    // same for Bar -------------------
    val b = Bar(33)
    val oldValue2 = b.p
    println(oldValue2)
    // ... etc.

    // same for Person ----------------
    val bram = Person(41, "Bram")
    val oldValue3 = bram.p
    println(oldValue3)
    // ... etc.

    // etc.
}

// Not DRY -------------------
class WithClassicProperties(var i: Int) {
    var p: String
        get() = "The value is ${isEverything(i)}."
        set(value) {
            i = value.toIntOrNull() ?: 42
        }
}

class WithClassicPropertiesToo(var i: Int) {
    var p: String
        get() = "The value is ${isEverything(i)}."
        set(value) {
            i = value.toIntOrNull() ?: 42
        }
}

// More DRY ------------------
class WithHelperClass(i: Int) {
    var h = Helper(i)

    var p: String = i.toString()
        get() = h.p
        set(value) {
            h.p = value
            field = h.p
        }

}

class WithHelperClassToo(i: Int) {
    var h = Helper(i)

    var p: String = i.toString()
        get() = h.p
        set(value) {
            h.p = value
            field = h.p
        }

}

class Helper(var i: Int) {
    var p: String
        get() = "The value is ${isEverything(i)}."
        set(value) {
            i = value.toIntOrNull() ?: 42
        }
}

// DRY ------------------
// 1. Simple
class WithDelegatedPropertySimple(var i: Int) {
    var p: String by Delegate()
}

class Delegate {
    // operator getValue, check the syntax!
    operator fun getValue(source: WithDelegatedPropertySimple, property: KProperty<*>) =
        "The value is ${isEverything(source.i)}." + property.name

    // operator setValue, check the syntax!
    operator fun setValue(source: WithDelegatedPropertySimple, property: KProperty<*>, value: String) {
        source.i = value.toIntOrNull() ?: 42
    }
}

// 1. Reusable (using HasI and override var i)
class WithDelegatedProperty(override var i: Int) : HasI { // implements HasI, delegate handles HasI
    var name: String by IsEverythingDelegate()
}

class WithDelegatedPropertyToo(override var i: Int) : HasI {
    var nameToo: String by IsEverythingDelegate()
}

class IsEverythingDelegate {

    // operator getValue, check the syntax!
    operator fun getValue(hi: HasI, property: KProperty<*>) =
        "The value is ${isEverything(hi.i)}." + property.name

    // operator setValue, check the syntax!
    operator fun setValue(hi: HasI, property: KProperty<*>, value: String) {
        hi.i = value.toIntOrNull() ?: 42
    }
}

// DRY for multiple classes ------
class Foo(override var i: Int) : HasI { // implements HasI
    var p: String by DelegateMulti()
}

class Bar(override var i: Int) : HasI { // implements HasI
    var p: String by DelegateMulti()    // p is a String
}

class Person(age: Int, var name: String) : HasI { // implements HasI
    override var i = age                          // i is a String
    var p: String by DelegateMulti()
}

class DelegateMulti : ReadWriteProperty<HasI, String> { // must implement ReadWriteProperty<HasI, String>

    // operator getValue, check the syntax!
    override operator fun getValue(thisRef: HasI, property: KProperty<*>) = // first param is a HasI, because clients (e.g. Bar) implement HasI
        "The value is ${isEverything(thisRef.i)}."

    // operator setValue, check the syntax!
    override operator fun setValue(thisRef: HasI, property: KProperty<*>, value: String) {
        thisRef.i = value.toIntOrNull() ?: 42
    }
}

interface HasI {
    var i: Int
}

private fun isEverything(i: Int): String = if (i == 42) "The answer to everything" else i.toString()

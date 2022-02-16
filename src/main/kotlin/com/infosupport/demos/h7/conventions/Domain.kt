package com.infosupport.demos.h7.conventions

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.lang.Math.floor
import java.lang.Math.pow
import java.time.LocalDate
import kotlin.math.sqrt
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

data class Point(val x: Int, val y: Int) : Comparable<Point> {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)

    override fun compareTo(other: Point) = floor(vectorLength(this) - vectorLength(other)).toInt()

    private fun vectorLength(p: Point) = sqrt(pow(p.x.toDouble(), 2.0) + pow(p.y.toDouble(), 2.0))
}

data class MutablePoint(var x: Int, var y: Int)

val pointMap = mutableMapOf(
    "1" to Point(1, 1),
    "2" to Point(2, 2),
    "3" to Point(3, 3)
)

// ------------

data class Rectangle(val upperLeft: Point, val lowerRight: Point)

// -------------
open class PropertyChangeAware {
    protected val changeSupport = PropertyChangeSupport(this) // java beans api

    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.addPropertyChangeListener(listener)
    }
}

class PersonAwareOfPropertyChanges(
    val name: String, anAge: Int, aSalary: Int
) : PropertyChangeAware() {

    private val observer = { prop: KProperty<*>, oldValue: Int, newValue: Int ->
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }

    // manual implementation
    var age: Int by ObservableProperty(anAge, changeSupport)

    // Kotlin library support
    var salary: Int by Delegates.observable(aSalary, observer)
}

class ObservableProperty(
    var propValue: Int, val changeSupport: PropertyChangeSupport
) {
    operator fun getValue(p: PersonAwareOfPropertyChanges, prop: KProperty<*>): Int = propValue

    operator fun setValue(p: PersonAwareOfPropertyChanges, prop: KProperty<*>, newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
}

class Add(override val a: Int, override val b: Int) : TwoOperands {
    val sum by Sum()
}

class Order(total: Int, vat: Int, val date: LocalDate) : TwoOperands {

    override val a = total
    override val b = vat

    val sum by Sum()
}

interface TwoOperands {
    val a: Int
    val b: Int
}

class Sum

operator fun Sum.getValue(
    thisRef: TwoOperands,
    property: KProperty<*>
) = thisRef.a + thisRef.b

data class PersonWithLazyEmails(val name: String) {
    // lazy loading using by lazy {...}
    val emails by lazy { loadEmails(this) }

    // Manual lazy loading:
    // private var _emails: List<Email>? = null
    // val emails: List<Email>
    //     get() {
    //         if (_emails == null) {
    //             _emails = loadEmails(this)
    //         }
    //         return _emails!!
    //     }
}

data class Email(val from: String, val to: String, val subject: String)

fun loadEmails(person: PersonWithLazyEmails): List<Email> {
    println("Load emails for ${person.name}")
    return listOf(
        Email("Bram", "Mieke", "Groceries"),
        Email("Mieke", "Bram", "Diner"),
        Email("Gijs", "Niek", "Game time"),
        Email("Koen", "Bram", "Painting")
    )
}

class PersonWithDynamicProperties(
    // a dynamic set of properties (key/value)
    json: HashMap<String, String>
) {
    // required properties
    val name: String by json
    val age: String by json
}

// Entities (uses EntityFramework.kt)

object Users {
    val name = StringColumn("name")
    val age = IntColumn("age")
}

data class User(override val id: Long) : Entity(id) {
    var name: String by Users.name
    var age: Int by Users.age
}

object Courses {
    val name = StringColumn("name")
    val level = IntColumn("level")
    val grade = IntColumn("grade")
}

data class Course(override val id: Long) : Entity(id) {
    var name: String by Courses.name
    var level: Int by Courses.level
    var grade: Int by Courses.grade
}


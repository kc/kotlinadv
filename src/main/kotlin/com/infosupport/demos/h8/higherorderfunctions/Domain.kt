package com.infosupport.demos.h8.higherorderfunctions

import java.util.concurrent.locks.ReentrantLock

data class SiteVisit(
    val path: String,
    val duration: Double,
    val os: OS
)

enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }

val log = listOf(
    SiteVisit("/", 34.0, OS.WINDOWS),
    SiteVisit("/", 22.0, OS.MAC),
    SiteVisit("/login", 12.0, OS.WINDOWS),
    SiteVisit("/signup", 8.0, OS.IOS),
    SiteVisit("/", 16.3, OS.ANDROID)
)

data class Person(val name: String, val age: Int)

val people = listOf(
    Person("Alice", 29), Person("Bob", 31), Person("Chris", 22), Person("Dave", 42),
    Person("Eden", 65), Person("Frank", 1), Person("Gwen", 25), Person("Harry", 41),
)

val lock = ReentrantLock()

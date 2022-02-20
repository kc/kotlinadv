package com.infosupport.labs.h11

// 1. Gradle DSL

// Let's write this little bit of DSL now, so that the code in boot compiles.
// Use the invoke convention on `dependencies`.

fun main() {
    boot()
}

fun boot() {
    val dependencies = Dependencies()

    // TODO uncomment and implement:

    // You can call compile on `dependencies` directly:
    // dependencies.compile("junit:junit:4.11")

    // or

    // You can invoke `dependencies` with a lambda as parameter:
    // dependencies {
    //     compile("junit:junit:4.11")
    // }
}

class Dependencies {
    // ...? TODO
}

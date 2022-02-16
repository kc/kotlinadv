package com.infosupport.labs.h11

// 2. Let's write this little bit of DSL now, so that the code in boot compiles. Use teh invoke convention on dependencies.

fun boot() {

    val dependencies = Dependencies()

    // You can call compile on `dependencies` directly:
    // TODO uncomment and implement:
    // dependencies.compile("junit:junit:4.11")

    // or

    // You can invoke `dependencies` with a lambda as parameter:
    // TODO uncomment and implement:
    // dependencies {
    //     compile("junit:junit:4.11")
    // }
}

class Dependencies {
    // ...? TODO
}

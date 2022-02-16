package com.infosupport.solutions.h11

// 2. Let's write this little bit of DSL now, so that the code above compiles.

fun main() {
    boot()
}

fun boot() {
    val dependencies = Dependencies()

    dependencies.compile("junit:junit:4.11")
    dependencies {
        compile("junit:junit:4.11")
    }
}

class Dependencies {
    fun compile(s: String) {
        println("Added dependency on $s")
    }

    operator fun invoke(body: Dependencies.() -> Unit) {
        body()
    }
}

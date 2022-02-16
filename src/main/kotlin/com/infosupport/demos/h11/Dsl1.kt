package com.infosupport.demos.h11

// Building structured APIs: lambdas with receivers in DSLs
// Lambdas with receivers and extension function types

fun extensionFunction() {
    // define an extension function on a StringBuilder
    fun StringBuilder.builderAction() {
        this.append("ExtensionFunction!")
    }

    // create a StringBuilder and call the extension function ON it
    val sb = StringBuilder("Hello ")
    sb.builderAction()
    val result = sb.toString()

    // print result
    println(result)

    // CONS: no DSL like structure
}

/*  λ = "lambda" :-) */
fun λ() {
    // define a λ with a StringBuilder
    val builderAction: (StringBuilder) -> Unit = { sb -> sb.append("λ!") }

    // create a StringBuilder and call the λ WITH it
    val sb = StringBuilder("Hello ")
    builderAction(sb)
    val result = sb.toString()

    // print result
    println(result)

    // CONS: the λ has input (sb, or it); this can be more concise..:
}

/*  λ = "lambda" :-) */
fun λWithReceiver() {
    // define a λ on a StringBuilder
    val builderAction: StringBuilder.() -> Unit = { append("λWithReceiver!") }

    // create a StringBuilder and call the λ ON it
    val sb = StringBuilder("Hello ")
    sb.builderAction()
    val result = sb.toString()

    // print result
    println(result)

    // PROS: the λ body is more concise,
    //       moreover we can create a DSL structure now:
}

// define a λ as an argument:
fun buildHelloString(builderAction: StringBuilder.() -> Unit): String {
    // lambda is gone here

    // create a StringBuilder and call the λ ON it
    val sb = StringBuilder("Hello ")
    sb.builderAction() // the sb gets sucked into the lambda scope, to be accessed; therefore, we can do this.append(), or even append() in the lambda
    val result = sb.toString()

    // return the result
    return result
}

fun dsl() {
    val result = buildHelloString { append("buildString!") } // DSL
    println(result)
}

fun main() {
    extensionFunction()
    λ()
    λWithReceiver()
    dsl()

    // and lastly:
    applyAndWithDemo()
}

fun applyAndWithDemo() {
    val map = mutableMapOf(1 to "one")

    val apply: MutableMap<Int, String> =
        map.apply { this[2] = "two" }   // Q: what's the signature of apply?
    val with: Unit =
        with(map) { this[3] = "three" } // Q: what's the signature of with?

    println(
        buildHelloString {
            append(map)
            append("!")
        }
    )
}

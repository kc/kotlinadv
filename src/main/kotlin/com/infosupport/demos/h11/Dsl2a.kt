package com.infosupport.demos.h11

// Building structured APIs: lambdas with receivers in DSLs
// Builders, HTML example

// How is this implemented?
// Let's implement a piece of simple HTML DSL...
// (concepts first, full implementation in next source file...)

fun main() {
    println(createTable())
}

fun createTable() =
    table {
        tr {
            td {
            }
        }
    }

// We start with the top element "table" as a top level function:
fun table(render: TABLE.() -> Unit) = TABLE().apply(render)

// The domain in concept:

open class Tag {
    // TODO:
    // - each tag has a name to remember
    // - keep track of all the tags in a list
    // - write a toString that serves as a final build()-function
}

class TABLE : Tag() { // Utility classes that shouldn’t appear explicitly in the code, and that’s why they’re named in capital letters
    fun tr(render: TR.() -> Unit) { // Lambda with receiver of type TR; table can only contain tr
        // TODO
    }
}

class TR : Tag() {
    fun td(render: TD.() -> Unit) { // Lambda with receiver of type TD; tr can only contain td
        // TODO
    }
}

class TD : Tag()

// Full implementation in next source...


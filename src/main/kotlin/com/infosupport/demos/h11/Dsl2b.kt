package com.infosupport.demos.h11.impl

// Building structured APIs: lambdas with receivers in DSLs
// Builders, HTML example

// Full implementation (refactored in the next source file...)

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

fun table(render: TABLE.() -> Unit) = TABLE().apply(render)

open class Tag(val name: String) { // TODO show - each tag has a name to remember
    // TODO show - keep track of all the tags in a list
    protected val children = mutableListOf<Tag>()

    // TODO show - write a toString that serves as a final build()-function
    override fun toString() = """<$name>${children.joinToString("")}</$name>"""
}

class TABLE : Tag("table") { // TODO show
    fun tr(render: TR.() -> Unit) {
        // TODO show
        val tr = TR()
        tr.render()
        children.add(tr)
    }
}

class TR : Tag("tr") {
    fun td(render: TD.() -> Unit) {
        // TODO show
        val td = TD()
        td.render()
        children.add(td)
    }
}

class TD : Tag("td")

// (refactored in the next source file...)

package com.infosupport.demos.h11.impl.refac

// Building structured APIs: lambdas with receivers in DSLs
// Builders, HTML example

// Full implementation (refactored in the next source file...)

fun main() {
    println(createTable())
    println(createAnotherTable())
}

fun createTable() =
    table {
        tr {
            td {
            }
        }
    }

fun table(render: TABLE.() -> Unit) = TABLE().apply(render)

open class Tag(val name: String) {

    protected val children = mutableListOf<Tag>()

    // TODO show generic render
    protected fun <T : Tag> render(child: T, render: T.() -> Unit) {
        child.render()
        children.add(child)
    }

    override fun toString() = """<$name>${children.joinToString("")}</$name>"""
}

class TABLE : Tag("table") {
    fun tr(render: TR.() -> Unit) {
        // TODO show
        render(TR(), render)
    }
}

class TR : Tag("tr") {
    fun td(render: TD.() -> Unit) {
        // TODO show
        render(TD(), render)
    }
}

class TD : Tag("td")

// TODO show
fun createAnotherTable() = table {
    for (i in 1..2) {
        tr {
            td {
            }
        }
    }
}

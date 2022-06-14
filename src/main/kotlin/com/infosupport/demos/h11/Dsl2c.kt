package com.infosupport.demos.h11.impl.refac

// Building structured APIs: lambdas with receivers in DSLs
// Builders, HTML example

// Refactored implementation

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

fun createAnotherTable() = table {
    for (i in 1..2) {
        tr {
            td {
            }
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

// DSL marker ensures that we can only call methods from classes annotated with this marker from the direct scope.
//
// When in a tr { } lambda, we can only call methods defined on TR, not on TABLE even though this is in scope.
// Therefore, this code will not compile:
//
// table {
//   tr {
//     tr { }
//   }
// }
//
// This helps structure of the code and side-effects that the user did not expect.
@DslMarker
annotation class HtmlDsl

@HtmlDsl
class TABLE : Tag("table") {
    fun tr(render: TR.() -> Unit) {
        // TODO show
        render(TR(), render)
    }
}

@HtmlDsl
class TR : Tag("tr") {
    fun td(render: TD.() -> Unit) {
        // TODO show
        render(TD(), render)
    }
}

@HtmlDsl
class TD : Tag("td")


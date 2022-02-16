package com.infosupport.solutions.h11

import kotlinx.html.*
import kotlinx.html.stream.createHTML

// LAB: refactor dropdownExample() into dropdownExampleRef(). Create the necessary
// functions like dropdownButton, dropdownMenu etc.

fun dropdownExample() = createHTML()
    // createHTML() returns TagConsumer<T>, so we have to define an ext. fun. on TagConsumer<T>,
    // but the body works on DIV, so lambda should be defined on DIV
    .dropdown {
        // this = DIV, so dropdownButton should be defined on DIV,
        // but the body works on BUTTON, so lambda should be defined on BUTTON
        dropdownButton { +"Dropdown" }

        // this = DIV, so dropdownMenu should be defined on DIV,
        // but the body works on UL, so lambda should be defined on UL
        dropdownMenu {
            // this = UL, so item should be defined on UL,
            // but the body works on LI, so lambda should be defined on LI
            item { link("#") { +"Action" } }
            item { link("#") { +"Another action" } }

            // this = UL, so functions below should be defined on UL,
            // and body works on nothing
            divider()
            dropdownHeader("dropdown-header")

            // see li { a }above
            item { link("#") { +"Separated link" } }
        }
    }

private fun <T> TagConsumer<T>.dropdown(block: DIV.() -> Unit) {
    div("dropdown", block)
}

private fun DIV.dropdownButton(block: BUTTON.() -> Unit) {
    button(classes = "btn dropdown-toggle") {
        block()
        span(classes = "caret")
    }
}

private fun DIV.dropdownMenu(block: UL.() -> Unit) {
    ul(classes = "dropdown-menu", block = block)
}

private fun UL.item(block: LI.() -> Unit) {
    li(block = block)
}

private fun LI.link(href: String, block: A.() -> Unit) {
    a(href, block = block)
}

private fun UL.divider() {
    li { role = "separator"; classes = setOf("divider") }
}

private fun UL.dropdownHeader(h: String) {
    li { classes = setOf(h); +"Header" }
}

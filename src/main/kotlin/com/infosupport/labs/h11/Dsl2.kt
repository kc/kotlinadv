package com.infosupport.labs.h11

import kotlinx.html.*
import kotlinx.html.stream.createHTML

// 2. HTML DSL

// Refactor dropdownExample() into dropdownExampleDSL().
//
// Create the necessary functions like dropdownButton, dropdownMenu etc.
// Hints:
//  - start from the inside, i.e. at li {...}
//  - use can use your IDE's "refactor" options and watch the outcome,
//      but also, try a few refactorings manually
//  - use the comments inside the function as hints

fun dropdownExample() = createHTML()
    // createHTML() returns TagConsumer<T>, so dropdown should be defined on TagConsumer<T>,
    // but the body works on DIV, so lambda should be defined on DIV
    .div(classes = "dropdown") {
        // this = DIV, so dropdownButton should be defined on DIV,
        // but the body works on BUTTON, so lambda should be defined on BUTTON
        button(classes = "btn dropdown-toggle") {
            // this = BUTTON, so fun should be defined on BUTTON,
            +"Dropdown"
            span(classes = "caret")
        }
        ul(classes = "dropdown-menu") {
            // this = UL, so item should be defined on UL,
            // but the body works on LI, so lambda should be defined on LI
            li { a("#") { +"Action" } }
            li { a("#") { +"Another action" } }

            // this = UL, so functions below should be defined on UL,
            // and body works on nothing
            li { role = "separator"; classes = setOf("divider") }
            li { classes = setOf("dropdown-header"); +"Header" }

            // see item
            li { a("#") { +"Separated link" } }
        }
    }

/*
fun dropdownExampleDSL() = createHTML().dropdown {
    dropdownButton { +"Dropdown" }
    dropdownMenu {
        item { link("#") { +"Action" } }
        item { link("#") { +"Another action" } }
        divider()
        dropdownHeader("dropdown-header")
        item { link("#") { +"Separated link" } }
    }
}
*/


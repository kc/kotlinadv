package com.infosupport.demos.h11

import kotlinx.html.*
import kotlinx.html.stream.createHTML

// Building structured APIs: lambdas with receivers in DSLs
// Kotlin builders: enabling abstraction and reuse

// How can we build this piece of (bootstrap) styled dropdown with kotlin dsl?
/*
<div class="dropdown">
  <button class="btn dropdown-toggle">
    Dropdown
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu">
    <li><a href="#">Action</a></li>
    <li><a href="#">Another action</a></li>
    <li role="separator" class="divider"></li>
    <li class="dropdown-header">Header</li>
    <li><a href="#">Separated link</a></li>
  </ul>
</div>
*/

// Initial attempt (note: you need org.jetbrains.kotlinx:kotlinx-html-jvm dependency):

fun main() {
    println(dropdownExample())
}

fun dropdownExample() = createHTML().div(classes = "dropdown") {
    button(classes = "btn dropdown-toggle") {
        +"Dropdown"
        span(classes = "caret")
    }
    ul(classes = "dropdown-menu") {
        li { a("#") { +"Action" } }
        li { a("#") { +"Another action" } }
        li { role = "separator"; classes = setOf("divider") }
        li { classes = setOf("dropdown-header"); +"Header" }
        li { a("#") { +"Separated link" } }
    }
}

// We can do better, see below. These look like custom language constructs, i.e. DSL!
// LAB: see (...)labs.Dsl2.kt
/*
fun dropdownExampleRef() = createHTML().dropdown {
    dropdownButton { +"Dropdown" }
    dropdownMenu {
        item("#", "Action")
        item("#", "Another action")
        divider()
        dropdownHeader("Header")
        item("#", "Separated link")
    }
}
*/





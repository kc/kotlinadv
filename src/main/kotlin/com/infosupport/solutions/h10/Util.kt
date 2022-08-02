package com.infosupport.solutions.h10

import ru.yole.jkid.joinToStringBuilder
import java.time.LocalDate

fun StringBuilder.serializeAndEscape(s: String) {
    append('"')
    s.forEach { append(it.escape()) }
    append('"')
}

fun Char.escape(): Any = when (this) {
    '\\' -> "\\\\"
    '\"' -> "\\\""
    '\b' -> "\\b"
    '\u000C' -> "\\f"
    '\n' -> "\\n"
    '\r' -> "\\r"
    '\t' -> "\\t"
    else -> this
}

fun StringBuilder.serializePropertyValue(value: Any?) {
    when (value) {
        null -> append("null")
        is String -> serializeAndEscape(value)
        is Number, is Boolean, is LocalDate -> append(value.toString())
        is List<*> -> serializeList(value)
        else -> serializeObject(value)
    }
}

fun StringBuilder.serializeList(data: List<Any?>) {
    data.joinToStringBuilder(this, prefix = "[", postfix = "]") {
        serializePropertyValue(it)
    }
}


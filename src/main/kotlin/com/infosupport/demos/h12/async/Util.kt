package com.infosupport.demos.h12.async

import kotlinx.html.currentTimeMillis
import java.time.LocalTime.now
import java.time.format.DateTimeFormatter.ofPattern
import kotlin.math.round
import kotlin.reflect.KFunction

fun measure(n: Int, launch: () -> Unit): Double {
    print("Launching $n x '${launch}' ")
    val start = currentTimeMillis()
    repeat(n) { launch() }
    val duration = currentTimeMillis() - start
    println("took $duration ms.")

    return duration.toDouble()
}

fun log(msg: String) {
    val t = Thread.currentThread()
    val now = now().format(ofPattern("kk:mm:ss.SSS"))
    println("[$now] @${t.name}(${t.id}) $msg")
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun printheader(title: KFunction<*>) {
    println("----------------- $title -----------------")
}

fun rand(max: Long) = (Math.random() * max).toLong()

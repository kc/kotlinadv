package com.infosupport.demos.h12.async.channels.coffeeshop

fun log(v: Any) = println("[${Thread.currentThread().name}] $v")

fun Float.format(digits: Int): String = java.lang.String.format("%.${digits}f", this)

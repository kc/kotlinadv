package com.infosupport.demos.h12.async.channels.coffeeshop

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// Concurrent coffeeshop: https://miro.medium.com/max/2400/1*5RsE1shRY2v46sl-mHA8oQ.png

// convert CoffeeShop1 to process the orders using two coroutines
// the orders are processed twice but they're processed concurrently
fun main() = runBlocking {
    val orders = listOf(
        Menu.Cappuccino(CoffeeBean.Regular, Milk.Whole),
        Menu.Cappuccino(CoffeeBean.Premium, Milk.Breve),
        Menu.Cappuccino(CoffeeBean.Regular, Milk.NonFat),
        Menu.Cappuccino(CoffeeBean.Decaf, Milk.Whole),
        Menu.Cappuccino(CoffeeBean.Regular, Milk.NonFat),
        Menu.Cappuccino(CoffeeBean.Decaf, Milk.NonFat)
    )
    log(orders)
    val t = measureTimeMillis {
        coroutineScope {
            launch(CoroutineName("barista-1")) { makeCoffee(orders) }
            launch(CoroutineName("barista-2")) { makeCoffee(orders) }
        }
    }
    println("Execution time: $t ms")
}

// takes a list of orders and processes them one at a time
private suspend fun makeCoffee(orders: List<Menu>) {
    for (o in orders) {
        log("Processing order: $o")
        when (o) {
            is Menu.Cappuccino -> {
                val groundBeans = grindCoffeeBeans(o.beans())
                val espressoShot = pullEspressoShot(groundBeans)
                val steamedMilk = steamMilk(o.milk())
                val cappuccino = makeCappuccino(o, espressoShot, steamedMilk)
                log("Serve: $cappuccino")
            }
        }
    }
}

private suspend fun grindCoffeeBeans(beans: CoffeeBean): CoffeeBean.GroundBeans {
    log("Grinding beans")
    delay(30)
    return CoffeeBean.GroundBeans(beans)
}

private suspend fun pullEspressoShot(groundBeans: CoffeeBean.GroundBeans): Espresso {
    log("Pulling espresso shot")
    delay(20)
    return Espresso(groundBeans)
}

private suspend fun steamMilk(milk: Milk): Milk.SteamedMilk {
    log("Steaming milk")
    delay(10)
    return Milk.SteamedMilk(milk)
}

private suspend fun makeCappuccino(order: Menu.Cappuccino, espressoShot: Espresso, milk: Milk.SteamedMilk): Beverage.Cappuccino {
    log("Combining ingredients")
    delay(5)
    return Beverage.Cappuccino(order, espressoShot, milk)
}

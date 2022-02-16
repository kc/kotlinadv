package com.infosupport.demos.h12.async.channels.coffeeshop

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.selects.select

class EspressoMachine(scope: CoroutineScope) : CoroutineScope by scope {
    data class PullEspressoShotRequest(val groundBeans: CoffeeBean.GroundBeans, val espressoChan: SendChannel<Espresso>)

    data class SteamMilkRequest(val milk: Milk, val steamMilkChan: SendChannel<Milk.SteamedMilk>)

    // fields:
    private val portafilterOne: SendChannel<PullEspressoShotRequest> = actor() {
        consumeEach {
            log("Pulling espresso shot on portafilter one")
            delay(20)
            it.espressoChan.send(Espresso(it.groundBeans))
            it.espressoChan.close()
        }
    }

    private val portafilterTwo: SendChannel<PullEspressoShotRequest> = actor() {
        consumeEach {
            log("Pulling espresso shot on portafilter two")
            delay(20)
            it.espressoChan.send(Espresso(it.groundBeans))
            it.espressoChan.close()
        }
    }

    private val steamWandOne: SendChannel<SteamMilkRequest> = actor() {
        consumeEach {
            log("Steaming milk with steam wand one")
            delay(10)
            it.steamMilkChan.send(Milk.SteamedMilk(it.milk))
            it.steamMilkChan.close()
        }
    }

    private val steamWandTwo: SendChannel<SteamMilkRequest> = actor() {
        consumeEach {
            log("Steaming milk with steam wand two")
            delay(10)
            it.steamMilkChan.send(Milk.SteamedMilk(it.milk))
            it.steamMilkChan.close()
        }
    }

    // methods:
    // How can we implement the functions pullEspressoShot and steamMilk?

    // One approach is to model these two functions as suspendible.
    // That way the caller will provide the input (type of milk or type of ground coffee beans)
    // and await the output (steamed milk or an espresso shot). But what about the internals of the espresso
    // machine? How does the espresso machine limit the number of espresso shots pulled at the same time?

    // What if we constructed two channels, one for the portafilter and one for the steam wand,
    // with a fixed buffer size of 2. We could then send the input to the appropriate channel.
    // Using the channel is a good way to communicate.
    // But setting the buffer size to 2 doesn't mean we’re pulling two espresso shots at once.
    // Rather, it means we can have up to two pending espresso shot requests while pulling one

    // What if we create a channel for each portafilter. And similarly, we create a channel for
    // each steam wand. This is closer to what we want. We can launch a coroutine for each
    // portafilter and associate each portafilter with a channel. This is a good candidate for an actor.

    suspend fun pullEspressoShot(groundBeans: CoffeeBean.GroundBeans) = select<Espresso> {
        // select works a lot like a switch statement but for channels
        // select picks the first channel that is ready (here: portafilterOne or portafilterTwo)
        // By ready we mean this could be the first channel that is ready to send to
        // or ready to receive from. The select expression suspends if none of the channels are ready

        // We need a way to communicate the result from the portafilter actor back to the select statement.
        // This is why we create a channel:
        val channel = Channel<Espresso>()

        val req = PullEspressoShotRequest(groundBeans, channel)
        portafilterOne.onSend(req) {
            channel.receive() // returns the expresso from this portafilter
        }
        portafilterTwo.onSend(req) {
            channel.receive() // returns the expresso from this portafilter
        }
    }

    suspend fun steamMilk(milk: Milk) = select<Milk.SteamedMilk> {
        val chan = Channel<Milk.SteamedMilk>()
        val req = SteamMilkRequest(milk, chan)
        steamWandOne.onSend(req) {
            chan.receive()
        }
        steamWandTwo.onSend(req) {
            chan.receive()
        }
    }

    fun shutdown() {
        portafilterOne.close()
        portafilterTwo.close()
        steamWandOne.close()
        steamWandTwo.close()
    }
}

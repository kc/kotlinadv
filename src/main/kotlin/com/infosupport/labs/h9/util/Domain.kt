package com.infosupport.labs.h9

val disposables = listOf(
    Compost("Orange Peel"),
    Compost("Apple Core"),
    Donation("Couch"),
    Donation("Clothing"),
    Recyclable("Plastic"),
    Recyclable("Metal"),
    Recyclable("Cardboard"),
    Landfill("Trash")
)

interface Disposable {
    val name: String
    fun action(): String
}

class Compost(override val name: String) :
    Disposable {
    override fun action() = "Add to composter"
}

interface Transport : Disposable

class Donation(override val name: String) :
    Transport {
    override fun action() = "Call for pickup"
}

class Recyclable(override val name: String) :
    Transport {
    override fun action() = "Put in bin"
}

class Landfill(override val name: String) :
    Transport {
    override fun action() = "Put in dumpster"
}





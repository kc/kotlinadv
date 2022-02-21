package com.infosupport.demos.h9.generics

// Declaring generic functions and classes
// Generic Classes
//               type parameter
interface MyList<T> { // Declares T, called the type parameter
    operator fun get(index: Int): T // use T
    // ...
}

// Non generic subclass, providing T as String (= type argument)
class StringList : MyList<String> { // String is called the type argument
    override fun get(index: Int): String = TODO()
}

// Generic subclass, providing T as U
// U is declared as a NEW type parameter and used as type argument for T in List<T>
class ArrayList<U> : MyList<U> {
    override fun get(index: Int): U = TODO()
}

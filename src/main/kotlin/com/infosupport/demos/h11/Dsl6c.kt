package com.infosupport.demos.h11

import com.infosupport.demos.h7.conventions.Column
import com.infosupport.demos.h7.conventions.integer
import com.infosupport.demos.h7.conventions.varchar

// Kotlin DSLs in practice

// 3. Member extension functions: internal DSL for SQL

// First a DSL for DDL. How can we define a table like this in Kotlin?
// CREATE TABLE IF NOT EXISTS Country (
//      id INT AUTO_INCREMENT NOT NULL,
//      name VARCHAR(50) NOT NULL,
//      CONSTRAINT pk_Country PRIMARY KEY (id)
// )

// What about this:

object Country : Table("Country") {
    // integer/varchar is just a utility function.
    // What about autoIncrement and primaryKey?
    // These are part of our DDL DSL.
    // Q: Where do we want to define them? What is the applicability scope?
    // A: As so called "member extensions", in Table. See class Table below.
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 50)
}

object Customer : Table("Customer") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 50)
    val email = varchar("email", 255).unique()
}

open class Table(val table: String) {

    init {
        println(" ========== Creating table $table ==========")
    }

    // These are the member extensions: member of Table AND extension function on Column.
    // This way, you constrain their applicability scope. You can’t specify the properties of a column
    // outside the context of a table: the necessary methods won’t resolve.
    fun <T> Column<T>.primaryKey(): Column<T> {
        println("Column $table.$name set to primary key.")
        return this
    }

    fun <T : Number> Column<T>.autoIncrement(): Column<T> {
        println("Column $table.$name set to auto increment.")
        return this
    }

    fun <T> Column<T>.unique(): Column<T> {
        println("Column $table.$name set to unique.")
        return this
    }
}

// So, this doesn't work and it won't make sense out of the context of a Table:
fun defineTable() {
    val integer = integer("age")
    // integer.primaryKey()

    // Maybe we should define integer(..) as a member extension as well?
    // LAB: try this out, see (...)labs.Dsl3.kt exercise 3.
}

// Next, how can we implement a DSL for DML (i.e. select ...)?
// SELECT cu.name
//   FROM Country as co
//   JOIN Customer as cu ON co.id = cu.countryId
//  WHERE co.name = 'USA'

// LAB: see (...)labs.Dsl3.kt exercise 4. and 5.

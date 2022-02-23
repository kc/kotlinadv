package com.infosupport.solutions.h11

import com.infosupport.demos.h11.Country
import com.infosupport.demos.h11.Customer
import com.infosupport.demos.h11.Table
import com.infosupport.demos.h7.conventions.Column

// 3. How can we implement this query?
fun query() {
    (Country join Customer)
        .select { Country.name eq "USA" }
        .forEach { println(it) }
}

// - define the methods join and select on class ...?
infix fun Table.join(other: Table): Table {
    println("Table $table joined with ${other.table}.")
    return Table("$table FULL OUTER JOIN ${other.table}")
}

// - select receives a lambda and returns a Query.
// The lambda should have a receiver of type SqlExpressionBuilder.
fun Table.select(where: SqlExpressionBuilder.() -> Boolean): Query {
    return Query("SELECT * FROM $table WHERE ${where(SqlExpressionBuilder)}")
}

class Query(private val command: String = "") {
    // - define forEach on class Query
    fun forEach(function: (s: String) -> Unit) {
        function(command)
    }
}

// - SqlExpressionBuilder can be a singleton
object SqlExpressionBuilder {
    // - this way, we can define the infix method eq as member extension in SqlExpressionBuilder on Column.
    infix fun <T> Column<T>.eq(value: T): Boolean {
        println("${this.name} = $value?")
        return this.name == value
    }

}

fun main() {
    query()
}

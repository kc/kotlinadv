package com.infosupport.labs.h11

// 3. How can we implement a DSL for DML (i.e. select ...)?
// SELECT cu.name
//   FROM Country as co
//   JOIN Customer as cu ON co.id = cu.countryId
//  WHERE co.name = 'USA'

// The DSL should look like this:
// (Country join Customer)
//     .select { Country.name eq "USA" }
//     .forEach { println(it) }

// Some hints:
// - use the given classes Table, Country, Customer.
// - define the methods join and select on class ...?
// - select
//      - receives a lambda and returns a (new class) Query.
//      - the lambda should have a receiver of type SqlExpressionBuilder.
// - SqlExpressionBuilder can be a singleton
// - this way, we can define the infix method eq as member extension _in_ SqlExpressionBuilder _on_ Column,
//   so we can only use eq within the context of a SqlExpressionBuilder
// - define forEach on class Query

// 4. (IF TIME PERMITS) Extend your DSL to support:
// (Country join Customer)
//     .select { Country.name eq "USA" and Customer.age gt 18}
//     .forEach { println(it) }

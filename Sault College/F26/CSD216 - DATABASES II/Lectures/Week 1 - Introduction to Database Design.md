Recommended reading: *Database Design for Mere Mortals*, Chapters 1-3.

By the end of this lesson you should be able to:
- Distinguish operational and analytical databases
- Discuss the advantages and disadvantages of relational and NoSQL databases
- Describe the relational model
- State the objectives of good database design
- Define common relational database terms

## Two kinds of database

Databases generally fall into one of two categories, depending on what job they do.

> [!note] Operational databases
> Operational databases handle the day-to-day running of a system. They store moment-to-moment updates: a new order in an e-commerce app, a user click on an LMS. Because they're constantly being read from and written to, they belong to what's called **OLTP** (online transaction processing) systems. Data here is dynamic and always changing.

> [!note] Analytical databases
> Analytical databases exist for long-term, high-level decision making rather than daily operations. They store historical data, often as snapshots pulled from an operational database, and once a value is inserted it rarely changes. These are **OLAP** (online analytical processing) systems, and writes are much less frequent than reads.

This course is mostly about designing operational databases, with a short unit on analytical design near the end.

## Relational databases

Most modern operational databases are **relational**. The idea was conceived in 1969 by Dr. Edgar F. Codd. Data lives in *relations* (tables) made of *tuples* (rows), where each relation stores information about one entity, each tuple is one instance of that entity, and two relations can be connected by matching values in a shared field.

Two advantages stand out:
- **Flexible querying** through SQL
- **Built-in integrity**, meaning the database itself enforces rules about what data is valid

> [!important] ACID transactions
> Relational databases guarantee data integrity through ACID transactions:
> - **Atomicity** – an operation completes fully or fails as a whole, never halfway
> - **Consistency** – every database constraint holds once an operation finishes
> - **Isolation** – concurrent operations leave the database exactly as if they'd run one after another
> - **Durability** – once an operation completes, it survives even a catastrophic failure (usually because it gets written to disk)

### Where relational databases struggle

Some applications need to handle huge volumes of data or transactions. Vertical scaling (buying better hardware) only gets you so far. Horizontal scaling (spreading the database across many machines) is possible but genuinely hard, and maintaining ACID guarantees across all those machines limits how many operations per second you can achieve.

This tension between really big data and the guarantees relational databases provide is what pushed the rise of NoSQL databases.

## NoSQL databases

NoSQL databases handle larger volumes of data and transactions by loosening data integrity guarantees. That trade-off changes how you design them:

- Keeping related information together in one place matters more than splitting it across separate relations
- Joins are avoided entirely, since they become too slow once you're working at Big Data scale

> [!tip] Don't assume relational design skills transfer directly
> The design instincts you'll build in this course for relational databases are, in several respects, the opposite of what good NoSQL design looks like. We'll cover NoSQL design separately later in the course.

## Other kinds of database software

**Relational Database Management Systems (RDBMS)** are the software used to create, maintain, and operate a relational database. They typically follow a client/server model: the [[RDBMS]] runs a central server holding the database, and applications connect to that server to read and write data, which lets the same data serve many applications at once. Examples include PostgreSQL, MS SQL, MySQL, and Oracle.

**Personal / embedded databases** are database libraries embedded directly inside an application, with no server involved. They're usually file-based and single-user, and show up often in desktop and mobile apps. Examples include H2, MS SQL Express, and SQLite.

> [!note] Course roadmap
> This course focuses on database design in this order: relational database design first, then a brief unit on NoSQL design, then a brief unit on analytical database design.

## Why bother with database design?

A reasonable question: why not just start from an existing database and tweak it to fit your needs?

> [!warning] What improper design costs you
> - Inaccurate data entry
> - Difficulty retrieving accurate information
> - Costly business mistakes

> [!important] What good design gets you
> - Accurate information storage and retrieval
> - More efficient data storage
> - A database that's easier to maintain
> - Applications that are easier to build on top of the database

A helpful comparison: approach database design the way you'd approach designing a house. Nobody starts framing walls before there's a blueprint. In the same way, you define the *logical* design of a database (what needs does it serve, what shape should the data take) before you touch the *physical* implementation.

## Why theory matters here (and why you can relax about it)

Relational database design is rooted in mathematics, specifically set theory and first-order predicate logic. That's what guarantees a well-designed database will actually work as intended: predictable, reliable behavior with well-understood properties.

> [!tip] You don't need the math
> You don't need to understand set theory or predicate logic to design or use relational databases. The methodology you'll learn packages that theory into practical steps.

## The design methodology

This course teaches the methodology from Michael J. Hernandez's *Database Design for Mere Mortals*: an organized, step-by-step process that both deepens your understanding of the database you're building and results in a well-designed relational database.

Traditional database design has three phases:

1. **Requirements analysis** – understand what the database actually needs to do
2. **Data modeling** – design the initial table and relationship structure
3. **Normalization** – eliminate modification problems by breaking large tables into smaller ones, each built around a single theme, then testing the result against a set of rules called *normal forms*

> [!example] What normalization checks for
> A *normal form* is a rule you can use to check whether a table has a specific kind of modification problem. Normalization itself is a tedious, repetitive process, and it's genuinely easy to get wrong by hand.

Hernandez's methodology is really built around two questions: can we identify the characteristics of a properly normalized table, and can we use those characteristics as a template for every table we build during the design process? The answer to both is yes.

> [!warning] Follow the methodology fully
> Hernandez's methodology produces well-designed databases, but only if you follow it fully and rigorously. Skipping steps or improvising partway through undermines the guarantees it's supposed to give you.

## Terminology you'll need

The vocabulary of relational databases splits naturally into four groups.

| Value-related | Structure-related | Relationship-related | Integrity-related |
|---|---|---|---|
| Data | Relation/table | Relationship | Field specification |
| Information | Record/row | One-to-one | Entity integrity |
| Null | Field/column | One-to-many | Domain integrity |
| | Key | Many-to-many | Referential integrity |
| | View | Participation type | Business rules |
| | Index | Participation degree | |

### Value-related terms

- **Data** is the raw values stored in the database.
- **Information** is data that's been processed into something meaningful.
- **Null** represents a missing, unknown, or optional value. It is *not* the same as zero or an empty string. Be explicit in your design about where nulls are possible, and avoid them where you can: they cause problems in math and logic expressions (any expression touching a null returns null) and behave oddly inside aggregate functions.

### Structure-related terms

**Relation/table** is the main structure of a database. Each one stores information about a single *subject*, and that subject is usually either an object or an event.

> [!example] Object tables vs event tables
> An object table stores something real: a person, a building, a vehicle, a product. Its characteristics become the table's columns.
> An event table stores a specific occurrence: an appointment, a bank transaction, a purchase order. Its characteristics also become columns, but each row represents something that happened rather than something that exists.

Formally, a relation is a table of data with eight properties:

1. Each row contains data about one entity instance
2. Each column contains data about one attribute of the relation's entity
3. Each cell contains only a single value
4. All values in one column share the same domain/data type
5. Each column has a unique name
6. The order of columns doesn't matter
7. The order of rows doesn't matter
8. No two rows have identical sets of values

> [!note] The same ideas, different names
> Depending on the context, people use different words for the same underlying concepts.

| Theme | Concrete instance | Fact about the theme |
|---|---|---|
| Table | Row | Column |
| Relation | Tuple | Attribute |
| File | Record | Field |
| Entity | Entity instance | Attribute |

There's also a distinction between **data tables** and **validation tables**. A data table stores data to supply information, and its contents change often. Most tables are data tables. A validation (or category, or lookup) table stores data used to implement integrity, its contents rarely change, and it often supplies the options in a dropdown list in an application's UI. Province names or status codes are typical examples.

A **field/attribute** is the smallest structure in a relational database. It represents one aspect of its table's subject, and the rule runs both directions: each individual piece of data lives in exactly one field, and a field holds exactly one individual piece of data. Getting this right at the field level matters a lot for good design overall.

> [!warning] Poorly designed fields
> Watch for three common mistakes:
> - A **multipart field** stores two or more distinct facts in a single value (for example, a "Client City, State, Zip" field crammed into one column)
> - A **multivalue field** stores multiple instances of the same kind of value (for example, an "Account Rep" field listing several names separated by commas)
> - A **calculated field** stores a value that could just be computed from other fields (for example, a "Client Full Name" field that duplicates First Name and Last Name)
>
> Good database design avoids all three.

A **record/row** represents one instance of its table's subject. It's a complete set of fields: some fields may hold null, but a record still includes every field the table defines. Its primary key value is unique across the whole table, and relationships between tables are modeled through individual records referring to each other.

A **view** (or stored query) is a virtual table built from fields drawn out of one or more base tables. It doesn't store data itself, though the RDBMS may cache its results. Views are useful for consolidating related data from multiple tables into a single report, controlling which users or roles can see specific information, and implementing integrity through a validation view.

> [!note] Materialized/indexed views are the exception
> Many modern RDBMSs also offer materialized or indexed views. Unlike ordinary views, these *do* store data, and they can be indexed to improve performance.

A **primary key (PK)** is one or more fields that uniquely determine the records of a table. A PK value identifies exactly one record, no two records share the same PK value, and this is what enforces table-level (entity) integrity. Every table in a relational database should have a primary key.

A **foreign key (FK)** establishes a relationship between two tables. It's an exact copy of the primary key from some other table, and its value determines which record in that other table this record is related to. Foreign keys enforce relationship-level (referential) integrity: an FK value must match an existing PK value, and the database should prevent deleting a PK value that some FK still refers to.

- A **simple key** consists of a single field.
- A **composite key** consists of more than one field.

An **index** is a physical structure that can improve read performance, much like the index at the back of a textbook. It's often confused with the idea of a key, but the two are different things: a key is about uniquely identifying records, an index is about speeding up how the database finds them.

### Relationship-related terms

A **relationship** may be formed between two tables either by matching a foreign key value with a primary key value, or by adding a join (also called associative or linking) table between them. Relationships enable multi-table queries and views, help reduce redundant data, and help implement referential integrity.

> [!warning] Relations and relationships are not the same thing
> A *relation* is any table of data with the eight properties listed earlier. A *relationship* is a connection formed between two relations via matching values in a shared field. It's an easy pair of terms to mix up, but they describe completely different things.

Every relationship has a **relationship type**, a **participation type**, and a **participation degree**.

**Relationship type** describes how many records on each side can be related:

> [!example] One-to-one
> Tables A and B have a one-to-one relationship if each record in B relates to one record in A, and each record in A relates to one record in B. The foreign key in the child table refers to the primary key in the parent. This pattern sometimes models a supertype/subtype relationship, or implements a specific business rule. Example: an *Employees* table and a *Compensation* table, where each employee has exactly one compensation record.

> [!example] One-to-many
> Tables A and B have a one-to-many relationship if each record in B relates to one record in A, but each record in A may relate to many records in B. The foreign key sits in B (the "many" side, or child), referring to the primary key in A (the "one" side, or parent). This is the most common relationship type. Example: an *Agents* table and an *Entertainers* table, where one agent represents several entertainers.

> [!example] Many-to-many
> Tables A and B have a many-to-many relationship if each record in B may relate to many records in A, and each record in A may relate to many records in B. This always requires a third join table, whose primary key is the combination of two foreign keys, one pointing to each side of the relationship. Example: a *Students* table and a *Classes* table, connected through a *Student Schedule* linking table, since one student can take many classes and one class has many students.

**Participation type** describes whether a relationship is required:

- A's participation is **mandatory** if a record in A must exist before you can add records to B
- A's participation is **optional** if no record in A is required before adding records to B

**Participation degree** describes the minimum and maximum number of records in B that each record in A may be related to (for example, 1 to 4).

### Integrity-related terms

A **field specification** (or domain) identifies every aspect of a field, grouped into three areas:

- **General**: basic aspects like name, description, and parent table
- **Physical**: how the field is stored and represented to users, such as data type, length, and display format
- **Logical**: constraints on the field's values, such as whether it's required, what range it accepts, and its default value

Data integrity itself breaks down into four kinds:

- **Entity (table-level) integrity**: no two rows in a relation share the same primary key value
- **Domain (field-level) integrity**: every value in a column respects a well-defined domain, meaning its data type plus any additional constraints
- **Referential (relationship-level) integrity**: every foreign key value points to a valid primary key value
- **Business rules**: constraints imposed on the database by an organization's own workflows and needs, on top of everything above

---

These four terminology groups (value, structure, relationship, integrity) will come up constantly for the rest of the course, so it's worth getting comfortable with them now, before diving into the actual design methodology in the coming weeks.

For more background, see [[Databases]], [[Data Structures]], and [[Software Engineering]] for how design methodology and structural thinking carry over from general software design.

Further reading:
- [Codd's 1970 paper introducing the relational model](https://dl.acm.org/doi/10.1145/362384.362685)
- [ACID properties overview (Wikipedia)](https://en.wikipedia.org/wiki/ACID)
- [NoSQL databases explained (MongoDB)](https://www.mongodb.com/nosql-explained)

## Tags

#databases #database_design #relational_databases #NoSQL #ACID #data_integrity #normalization #CSD216

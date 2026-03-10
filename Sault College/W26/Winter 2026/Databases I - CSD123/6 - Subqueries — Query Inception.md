# The Problem That Makes Subqueries Necessary

Consider a simple-sounding question: "Which employees earn more than the average salary?" Your first instinct might be to write something like this:

```sql
-- This looks logical, but it FAILS.
SELECT name FROM employees
WHERE salary > AVG(salary);
```

SQL will reject this outright. The reason is a fundamental rule: **aggregate functions like `AVG()`, `SUM()`, and `MAX()` cannot be used inside a `WHERE` clause.** T

The `WHERE` clause filters individual rows one at a time, but `AVG()` needs to look at _all_ rows at once to compute a result. 

These two operations happen at different stages of query execution; they simply cannot coexist on the same line.

The naive workaround is to run two separate queries, first manually find the average, then hardcode that number into a second query:

```sql
-- Step 1: Find the average (assume the result is 4.5)
SELECT AVG(qty) FROM assignments;

-- Step 2: Hardcode the result into a new query
SELECT name FROM minions WHERE qty > 4.5;
```

This _works_, but it is brittle. Tomorrow the average might be 5.2, and your hardcoded query is now silently wrong. 

> [!important] The Core Idea 
> A subquery lets you embed one `SELECT` statement inside another. The inner query runs first, calculates its result, and hands that result to the outer query — all in one shot, with no hardcoding.

# Definition

> [!note] What Is a Subquery? 
> A **subquery** (also called a _nested query_ or _inner query_) is a complete `SELECT` statement placed inside another SQL statement. 
> - It must always be wrapped in parentheses `()`. 
> - The inner query generally executes first, and its result is used by the outer (_parent_) query.

Subqueries are full `SELECT` statements, always enclosed in parentheses, where the inner query executes first, and they can be placed in `SELECT`, `FROM`, `WHERE`, or `HAVING` clauses.

# The 3 Structural Types

Here is the most important mental shift when learning subqueries: _the type of result the inner query returns completely determines how you must write the outer query._ There are three possibilities, and each one unlocks different operators and placement rules.

|Type|What the Inner Query Returns|Where It Lives|Operators Used|
|---|---|---|---|
|**Scalar**|1 row × 1 column (a single value)|`WHERE` clause|`=`, `<`, `>`, `!=`, etc.|
|**List**|Many rows × 1 column (a list of values)|`WHERE` clause|`IN`, `NOT IN`, `ANY`, `ALL`|
|**Table**|Many rows × many columns (a full table)|`FROM` clause|Standard `SELECT` logic|

Think of it this way: if a subquery gives back _one number_, the outer query can compare against it directly. 
- If it gives back _a list of IDs_, the outer query needs to check membership in that list. 
- If it gives back _an entire table_, the outer query can treat it like any other table and query it normally.

# Scalar Subqueries

> [!note] Definition
> A **scalar subquery** returns exactly **one value**, one row and one column. Because it behaves identically to a single hardcoded number or string, the outer query can use any standard comparison operator against it.

This is the most frequently used type of subquery in everyday SQL. Aggregate functions (`AVG`, `MIN`, `MAX`, `SUM`, `COUNT`) naturally collapse many rows into a single value, making them the perfect engine for scalar subqueries.

> [!example] Find all parts that cost more than the average
> 
> ```sql
> SELECT PartName, Cost
> FROM Parts
> WHERE Cost > (
>     -- The inner query runs first and returns a single number, e.g. 193.04
>     -- That number is then substituted in, making the WHERE clause:
>     -- WHERE Cost > 193.04
>     SELECT AVG(Cost)
>     FROM Parts
> );
> ```

> [!example] Find the cheapest part(s) in the factory
> 
> ```sql
> SELECT PartName
> FROM Parts
> WHERE Cost = (
>     -- MIN() always returns exactly one value — safe for scalar use
>     SELECT MIN(Cost)
>     FROM Parts
> );
> ```

You can use `=`, `!=`, `<`, `>`, `<=`, or `>=` — any operator that makes sense when comparing two single values works here.

> [!warning] The Crash Scenario
> Scalar Subqueries Must Return Exactly One Value If you use `=` (or any single-value comparison operator) but your inner query accidentally returns **two or more rows**, the database throws a fatal error. The engine is confused because it cannot compare one thing to multiple things simultaneously.
> 
> This is why aggregate functions are so heavily used with scalar subqueries — `AVG()`, `MIN()`, `MAX()` always reduce everything down to exactly one value, so they are inherently safe. If you write a scalar subquery _without_ an aggregate, be very confident it can only ever return one row.

# List (Column) Subqueries

> [!note] Definition
> A **list subquery** returns a **single column containing multiple rows**. The outer query uses set membership operators to check whether each row belongs to (or is absent from) that list.

The reason you need different operators here is straightforward: if the inner query returns `(501, 503, 509)`, asking `WHERE MinionID = (501, 503, 509)` is nonsensical — an ID cannot be equal to three things at once. The `IN` operator was designed precisely for this: it asks "is this value _anywhere_ in the list?"

The three operators available are **`IN`** (match found in list), **`NOT IN`** (no match found in list), and **`ANY` / `ALL`** (advanced comparisons like "greater than _any_ value in the list" or "greater than _all_ values in the list").

> [!example] Find all minions who have ever worked the Graveyard shift
> 
> ```sql
> SELECT Name, HireDate
> FROM Minions
> WHERE MinionID IN (
>     -- This returns a list of MinionIDs, e.g. (2, 3, 8, 11...)
>     -- The outer query checks: "is this minion's ID in that list?"
>     SELECT MinionID
>     FROM Assignments
>     WHERE Shift = 'Graveyard'
> );
> ```

You can also stack subqueries inside each other — the innermost one always executes first, passing its result up to the next level.

> [!example] Deep nesting — find minions who built bots that used a specific part
> 
> ```sql
> SELECT Name
> FROM Minions
> WHERE MinionID IN (
>     -- Level 1: which minions were assigned to those specific bots?
>     SELECT MinionID
>     FROM Assignments
>     WHERE BotID IN (
>         -- Level 2: which bots used Part #20 (the unstable Explosive Jam)?
>         SELECT BotID
>         FROM BillOfMaterials
>         WHERE PartID = 20
>     )
> );
> ```
> 
> Read from the inside out: first we find the bots that used Part 20, then we find the minions assigned to those bots, then we retrieve their names.

> [!warning] Three Rules You Cannot Break with List Subqueries **One column only** — the inner `SELECT` must return exactly one column. Writing `SELECT Name, ID` will cause an error because `IN` can only compare one thing at a time. **Data type match** — the column in the outer query must be the same data type as the column returned by the inner query. **NULLs poison `NOT IN`** — if the subquery list contains even a single `NULL` value, `NOT IN` will return zero results. Always add `WHERE column IS NOT NULL` inside your inner query when using `NOT IN`.

---

## Scalar vs. List at a Glance

|Feature|Scalar Subquery|List Subquery|
|---|---|---|
|Dimensions|1 Row × 1 Column|Many Rows × 1 Column|
|Valid Operators|`=`, `!=`, `<`, `>`, `<=`, `>=`|`IN`, `NOT IN`, `ANY`, `ALL`|
|Aggregate Usage|Very common — `AVG`, `MIN`, `MAX`|Rare — usually filters raw IDs|
|Failure Mode|Crashes if it returns 2+ rows|Crashes if it selects 2+ columns|

---

## Part 3: Table Subqueries (Inline Views / Derived Tables)

> [!note] Definition A **table subquery** (also called an _inline view_ or _derived table_) returns multiple columns and multiple rows — an entire table. It lives in the `FROM` clause, and the outer query treats it exactly like a real database table.

The elegant logic behind this comes from a simple observation rooted in relational theory: _every SELECT query produces a table as its output — so you can write a SELECT against that output just as you would against any stored table._ The result exists temporarily in memory and is discarded after the query finishes.

Three rules govern inline views: they go in the **`FROM` clause** (not `WHERE`), they **must be given an alias** using `AS alias_name` (the outer query needs a name to reference it by), and they enable **encapsulation** — complex logic lives inside, and a clean outer query sits on top.

> [!example] Find the maximum number of assignments held by any single minion SQL forbids `MAX(COUNT(*))` directly — you cannot nest aggregate functions. The inline view neatly solves this by splitting it into two passes.
> 
> ```sql
> SELECT MAX(TotalAssignments) AS HighestWorkload
> FROM (
>     -- Step 1 (inner): build a virtual table of each minion's assignment count
>     SELECT MinionID, COUNT(*) AS TotalAssignments
>     FROM Assignments
>     GROUP BY MinionID
> ) AS MinionWorkloads;  -- The alias is MANDATORY — without it, SQL errors out
> -- Step 2 (outer): now simply find the MAX of that virtual column
> ```

The three main use cases for inline views are **double aggregation** (doing `COUNT` then `MAX`, as above), **pre-filtering** (shrinking a massive million-row table into a small virtual table _before_ performing expensive JOINs), and **complex math** (performing calculations with clean aliases in the inner query, then referencing those simple aliases in the outer query).

---

## Part 4: Correlated Subqueries

All three types above are **uncorrelated** — the inner query is completely self-contained. It runs once, produces a result, and has no knowledge of the outer query. A _correlated_ subquery breaks this model.

> [!note] Definition A **correlated subquery** is a subquery that **references a column from the outer query**. This creates a dependency between the two queries, which means the inner query cannot run just once — it must execute _once for every single row_ evaluated by the outer query.

Think of it as a loop built into SQL. The outer query picks its first row and passes that row's specific data (like a MinionID) down into the inner query. The inner query then runs completely using that value. The result is evaluated. Then the outer query moves to its second row, passes that data down, and the inner query runs again. This continues for every row in the outer table.

> [!example] Using EXISTS with a correlated subquery — find all minions who have at least one assignment
> 
> ```sql
> SELECT Name
> FROM Minions m          -- 'm' is an alias for the outer table
> WHERE EXISTS (
>     -- This inner query runs once per minion.
>     -- It uses m.MinionID — a value from the OUTER query — as its filter.
>     -- EXISTS only needs to find ONE matching row; then it stops immediately.
>     SELECT 1            -- We don't care what's returned, just whether a row exists
>     FROM Assignments a
>     WHERE a.MinionID = m.MinionID   -- THIS is the correlation link
> );
> ```
> 
> The line `WHERE a.MinionID = m.MinionID` is what makes this correlated. The inner query is asking: "for _this specific minion_ from the outer query, does an assignment exist?"

`EXISTS` is particularly well-suited for correlated subqueries because it short-circuits — the moment it finds even one matching row, it stops and returns `TRUE`, rather than scanning the entire table.

> [!warning] Performance — Correlated Subqueries Scale Poorly If the outer table has 10,000 rows, the inner query runs **10,000 times**. This is O(N²) behaviour — performance degrades rapidly as the table grows. On large production databases, correlated subqueries can become serious bottlenecks.
> 
> Always ask yourself: _can this be rewritten as an `INNER JOIN`?_ Joins allow the database engine's query optimizer to use far more efficient execution strategies.

---

## Subqueries vs. JOINs

Many problems can be solved with either approach. Understanding the trade-offs is what separates good SQL writers from great ones.

|Criteria|Subqueries|JOINs|
|---|---|---|
|Output Columns|Limited to the outer query's tables|Can output columns from any joined table|
|Execution Speed|Often slower, especially if correlated|Usually faster due to query optimizer|
|Readability|High — reads like a natural logical sentence|Moderate — can get cluttered|
|Aggregate Filtering|Excellent and natural|Requires awkward `GROUP BY` + `HAVING`|

Choose a subquery when you need to filter rows based on a calculated aggregate, when the inner result is only needed for _filtering_ (not display), or when readability is the top priority. Choose a JOIN when your `SELECT` clause needs to display columns from multiple tables, or when you are working with very large tables and performance matters.

> [!tip] A Simple Mental Test Ask: "Do I need data _from_ the second table in my output, or am I just using it to _filter_ the first table?" If filtering only — a subquery is clean and natural. If you need to show columns from both tables — use a JOIN.

---

## Best Practices & Formatting

> [!tip] Three Formatting Habits to Build Now **Indent the inner query** by one tab or a few spaces — this visually separates the layers of logic and makes nesting depth obvious at a glance. **Start the subquery on a new line** — never bury `(SELECT ...)` at the end of a long `WHERE` clause string. **Comment complex logic** — if you are nesting three levels deep, add a `-- comment` explaining _why_ that structure is necessary.

**Readable (good):**

```sql
SELECT Name, HireDate
FROM Minions
WHERE MinionID IN (
    -- Find all minion IDs assigned to the Graveyard shift
    SELECT MinionID
    FROM Assignments
    WHERE Shift = 'Graveyard'
);
```

**Unreadable (avoid):**

```sql
SELECT Name, HireDate FROM Minions WHERE MinionID IN (SELECT MinionID FROM Assignments WHERE Shift = 'Graveyard');
```

Both produce identical results. Only one of them is maintainable six months later.

---

## Summary

Subqueries elevate SQL from basic data retrieval to programmatic logic. The key mental framework: **always start by asking what your inner query returns.** One value → scalar with standard comparison operators. A list of values → `IN` or `NOT IN`. An entire table → put it in `FROM` with an alias. And if you need per-row logic that depends on the outer context — that is a correlated subquery, used carefully given its performance cost.

Further reading: [SQL Subqueries — W3Schools](https://www.w3schools.com/sql/sql_subqueries.asp) | [Subqueries in SQL — Mode Analytics](https://mode.com/sql-tutorial/sql-sub-queries/)

---

## Tags

#databases #SQL #subqueries #nested_queries #query_optimization #relational_databases #scalar_subquery #inline_view #correlated_subquery
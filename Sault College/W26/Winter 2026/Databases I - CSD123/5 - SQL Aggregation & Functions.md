# Aggregate Functions

> [!NOTE] The Core Concept
> Normal SQL returns **one row per record**. [[Aggregate Functions]] collapse many rows into **one summary row**.
> 
> Think of it like this: instead of listing every receipt in a store, you just want the total sales for the day.

## COUNT()

Counts the number of rows (or non-null values in a column).

```sql
SELECT COUNT(*) FROM Minions;
-- Result: 10,000
```

**Important distinction:**

- `COUNT(*)` – counts **all rows**, including those with NULLs
- `COUNT(ColumnName)` – counts only **non-NULL values** in that column

```sql
SELECT COUNT(ReportsToID) FROM Minions;
-- If Gru's ReportsToID is NULL, he is NOT counted!
```

> Nulls are invisible to `COUNT(column)`. Always know which version you need.

## SUM()

Adds up all values in a numeric column.

```sql
SELECT SUM(Cost) FROM Parts;
-- Result: $1,000,000
```

**Constraint:** Only works on **number columns**.

## AVG()

Calculates the mean value across a column.

```sql
SELECT AVG(Cost) FROM Parts;
```

## MIN() and MAX()

Find the lowest or highest value in a column.

```sql
SELECT MAX(Cost) FROM Parts;  -- Most expensive item
SELECT MIN(Cost) FROM Parts;  -- Cheapest item
```

# Combining Multiple Aggregates

You can run several aggregates in a single query:

```sql
SELECT COUNT(*) AS TotalParts,
       MAX(Cost) AS MostExpensive,
       AVG(Cost) AS AveragePrice
FROM Parts;
```

> Aggregate functions (COUNT, SUM, AVG, MIN, MAX) turn many rows into one number. They operate on an entire column — or a group of rows when combined with GROUP BY.

# GROUP BY & HAVING

`AVG(Cost)` gives one average for the whole table. But what if you want the average **per bot model**? You need **buckets**.

## GROUP BY

**GROUP BY** divides rows into groups _before_ applying aggregates. Each group gets its own calculation.

> Imagine three physical buckets on the floor, one per bot model. You drop each row into the right bucket, then count/sum/average what's inside each bucket separately.

```sql
SELECT ModelName, COUNT(BotID)
FROM Bots
GROUP BY ModelName;
```

Result:

- Cookie-Bot 3000: 4 units
- Mega-Cookie: 1 unit

## The Golden Rule of GROUP BY

> **Every column in SELECT must either be in GROUP BY or wrapped in an aggregate function.**

```sql
-- ERROR: Status is not grouped and not aggregated
SELECT ModelName, Status, COUNT(*)
FROM Bots
GROUP BY ModelName;
```

Fix it by adding `Status` to `GROUP BY`:

```sql
SELECT ModelName, Status, COUNT(*)
FROM Bots
GROUP BY ModelName, Status;
```

## HAVING

HAVING filters _groups_ **after** they've been formed. You cannot use `WHERE` for this because `WHERE` runs before grouping.

**WHERE vs. HAVING — the key difference:**

|Clause|Filters|Timing|
|---|---|---|
|`WHERE`|Individual **rows**|Before grouping|
|`HAVING`|Entire **groups**|After grouping|

**Analogy:** `WHERE` checks each person at the door. `HAVING` checks the crowd after they've gathered.

```sql
SELECT ModelName, COUNT(*)
FROM Bots
WHERE Status = 'Active'       -- Filter rows first
GROUP BY ModelName
HAVING COUNT(*) > 2;          -- Then filter groups
```

Logic: Keep only Active bots → bucket by model → count → discard small buckets.

# Combining JOINs with Aggregates

Real-world questions often require both.

**Question:** How many bots did Kevin build?

```sql
SELECT m.Name, COUNT(a.BotID)
FROM Minions m
JOIN Assignments a ON m.MinionID = a.MinionID
GROUP BY m.Name;
```

**Question:** Total cost of parts per bot model?

```sql
SELECT b.ModelName, SUM(p.Cost * bom.Qty)
FROM Bots b
JOIN BillOfMaterials bom ON b.BotID = bom.BotID
JOIN Parts p ON bom.PartID = p.PartID
GROUP BY b.ModelName;
```

> GROUP BY buckets your data; HAVING filters those buckets. WHERE cannot filter aggregated results — that's HAVING's job.

# Scalar Functions

## Scalar vs. Aggregate

| Type                                    | Input     | Output  | Used for     |
| --------------------------------------- | --------- | ------- | ------------ |
| **Aggregate** (COUNT, SUM)              | Many rows | 1 value | Summarizing  |
| **[[Scalar Functions]]** (UPPER, ROUND) | 1 row     | 1 row   | Transforming |

> Scalar functions work **row by row** — they don't collapse data.

You **can** mix them:

```sql
SELECT UPPER(Name), COUNT(*) ... GROUP BY Name
```

## String Functions

### UPPER() / LOWER()

change text case:

```sql
SELECT UPPER(Name) FROM Minions;     -- KEVIN, STUART, BOB
WHERE LOWER(Name) = 'kevin';        -- case-insensitive search
```

### LENGTH() 

count characters:

```sql
SELECT Name, LENGTH(Name)
FROM Minions
WHERE LENGTH(Name) < 5;
-- Result: Bob (3), Dave (4)
```

### SUBSTRING()

extract part of a string:

```sql
-- Syntax: SUBSTRING(Column, Start, Length)
SELECT SUBSTRING(Name, 1, 1) FROM Minions;
-- Result: K, S, B, D (first initials)
```

### CONCAT() / ||

join strings together:

```sql
-- SQLite / PostgreSQL:
SELECT Name || ' is a Minion' AS Description FROM Minions;
-- MySQL:
SELECT CONCAT(Name, ' is a Minion') AS Description FROM Minions;
-- Result: 'Kevin is a Minion'
```

## Math Functions

### ROUND()

limit decimal places:

```sql
SELECT ROUND(AVG(Cost), 2) FROM Parts;
-- Result: $176.49 (not $176.333333...)
```

### ABS()

absolute value (remove the negative sign):

```sql
SELECT ABS(-500);
-- Result: 500
```

Useful when calculating differences where direction doesn't matter.

## Date Functions

### NOW() / DATE('now')

get the current date:

```sql
SELECT DATE('now');
-- Result: 2026-02-03
```

**Days since a date:**

```sql
SELECT julianday('now') - julianday(BuildDate) FROM Bots;
```

## Type & Null Functions

### CAST()

convert a value from one data type to another:

```sql
SELECT CAST(Cost AS INTEGER) * 2 FROM Parts;
-- Forces text '500' to behave as the number 500
```

### COALESCE()

replace NULLs with a default value:

```sql
SELECT m.Name, COALESCE(a.Shift, 'Lazy') AS Status
FROM Minions m
LEFT JOIN Assignments a ON m.MinionID = a.MinionID;
-- If Shift is NULL, display 'Lazy' instead
```

> Scalar functions clean and format data one row at a time. They can appear in SELECT, WHERE, and even inside aggregate functions.

# Order of Execution

This is the sequence SQL **actually** processes a query — not the order you write it:

| Step | Clause        | What happens                   |
| ---- | ------------- | ------------------------------ |
| 1    | `FROM / JOIN` | Load the table(s)              |
| 2    | `WHERE`       | Filter individual rows         |
| 3    | `GROUP BY`    | Form groups/buckets            |
| 4    | `HAVING`      | Filter groups                  |
| 5    | `SELECT`      | Calculate scalars & aggregates |
| 6    | `ORDER BY`    | Sort the result                |
| 7    | `LIMIT`       | Cut the result                 |

>This order explains **why** you can't use `WHERE` to filter aggregates — WHERE runs at step 2, before groups even exist.

# The Ultimate Report Query

Pulling it all together — all caps names, rounded costs, only for expensive minions:

```sql
SELECT UPPER(m.Name), ROUND(SUM(p.Cost), 0) AS TotalCost
FROM Minions m
JOIN Assignments a ON m.MinionID = a.MinionID
JOIN Bots b ON a.BotID = b.BotID
JOIN BillOfMaterials bom ON b.BotID = bom.BotID
JOIN Parts p ON bom.PartID = p.PartID
GROUP BY m.Name
HAVING SUM(p.Cost) > 100;
```

# Tags

#SQL #Databases #CSD123 #AggregateFunction #GroupBy #Having #ScalarFunctions #QueryOptimization #Week5
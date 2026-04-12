
Think of a database like a factory. Before you can produce anything, you need to build the physical structure first. Then, once the factory is up and running, you fill it with products and start managing them.

|Factory Analogy|Database Reality|
|---|---|
|The building|The database|
|Rooms|Tables|
|Equipment in each room|Columns|
|Products manufactured|Rows|
|Workers|SQL commands|

This is all about **how to build that factory** — creating tables, adding data, modifying it, and removing it when needed. Related: [[Databases]], [[Software Engineering]]

# SQL Command Families

SQL is not just one thing, it is split into four main families of commands, each with a different job.

> [!note] The Four SQL Families
> 
> - **DDL** — _Data Definition Language_: creates and modifies the **structure** of the database (tables, columns, etc.)
> - **DML** — _Data Manipulation Language_: works with the **actual data** inside the tables
> - **DCL** — _Data Control Language_: handles **permissions and security**
> - **TCL** — _Transaction Control Language_: manages **committing or rolling back** changes

# DDL — Building the Structure

DDL is what you use to define how the database is organized. You are not touching any real data yet — you are just setting up the framework.

> [!important] Key DDL Commands
> 
> - `CREATE` — makes a new table
> - `ALTER` — modifies an existing table
> - `DROP` — completely removes a table (structure and all data)
> - `TRUNCATE` — removes all rows but keeps the table structure

## CREATE TABLE

This is how you create a new, empty table ready to hold data.

```sql
CREATE TABLE Minions (
    MinionID   INT,
    Name       VARCHAR(50),
    Skill      VARCHAR(50)
);
```

When this runs, you get an **empty table** — like an empty spreadsheet with the column headers already set up.

## Data Types

Every column needs a **data type** that tells the database what kind of information it will store. Choosing the right type matters because it affects storage, performance, and what operations you can do.

| Data Type      | What it stores              | Example               |
| -------------- | --------------------------- | --------------------- |
| `INT`          | Whole numbers               | 1, 42, 500            |
| `VARCHAR(50)`  | Text up to 50 characters    | 'Kevin', 'Leadership' |
| `DECIMAL(6,2)` | Numbers with decimal places | 1234.56               |
| `DATE`         | Calendar dates              | 2024-01-15            |

> [!tip] Think Before You Pick a Type
> If a column stores someone's phone number, use `VARCHAR`, not `INT` — phone numbers can start with a zero and you never do math on them!

## Constraints — Safety Rules for Your Data

Constraints are rules you attach to columns to **protect the quality of your data**. They stop bad or duplicate data from getting in.

> [!note] Common Constraints
> 
> - **`PRIMARY KEY`** — uniquely identifies each row; must be unique and cannot be NULL
> - **`NOT NULL`** — the column must always have a value; it cannot be left empty
> - **`UNIQUE`** — no two rows can have the same value in this column
> - **`DEFAULT`** — if no value is provided, use this one automatically

### PRIMARY KEY Example

```sql
CREATE TABLE Villains (
    VillainID   INT PRIMARY KEY,
    VillainName VARCHAR(50)
);
```

Rules for primary keys:

- Each value must be **unique**
- Cannot be **NULL**
- Only **one primary key** per table

### DEFAULT Example

```sql
CREATE TABLE Robots (
    RobotID INT PRIMARY KEY,
    Status  VARCHAR(20) DEFAULT 'Idle'
);

INSERT INTO Robots (RobotID)
VALUES (1);
-- Status automatically becomes 'Idle'
```

Even though no `Status` value was inserted, the `DEFAULT` constraint fills it in automatically.

## ALTER TABLE

Once a table exists, you can **modify its structure** without having to delete and recreate it. This is very useful when requirements change over time.

```sql
ALTER TABLE Minions
ADD BananaConsumption INT;
```

This adds a brand new column called `BananaConsumption` to the existing `Minions` table. All existing rows will have `NULL` in that new column.

## DROP vs TRUNCATE vs DELETE

These three commands all remove data, but they behave very differently:

> [!important] Know the Difference
> 
> |Command|Removes Rows?|Removes Table Structure?|Selective?|
> |---|---|---|---|
> |`DROP TABLE`|Yes|Yes — table is gone completely|No|
> |`TRUNCATE TABLE`|Yes — all of them|No — keeps the table|No|
> |`DELETE`|Yes|No|Yes — can use `WHERE`|


# DML — Working with the Data

Once the structure is built, DML commands let you **fill it, read it, update it, and clean it out**

> [!note] Key DML Commands
> 
> - `INSERT` — add new rows
> - `SELECT` — read rows (covered in earlier lectures)
> - `UPDATE` — modify existing rows
> - `DELETE` — remove specific rows

## INSERT

Add a single row:

```sql
INSERT INTO Minions
VALUES (1, 'Kevin', 'Leadership', 500);
```

Add multiple rows at once (more efficient):

```sql
INSERT INTO Minions VALUES
    (2, 'Bob',    'Chaos',           1000),
    (3, 'Stuart', 'Music',           1252),
    (4, 'Dave',   'Rocket Building', 4520);
```

## SELECT

```sql
-- Return all columns and all rows
SELECT * FROM Minions;

-- Return only specific columns
SELECT Name, Skill FROM Minions;
```

## UPDATE

Modify the value of a column in one or more existing rows.

```sql
UPDATE Minions
SET Skill = 'Advanced Chaos'
WHERE MinionID = 2;
```

This only changes **Bob's** row because of the `WHERE` clause.

> [!warning] 
> UPDATE without WHERE is Dangerous If you forget the `WHERE` clause, **every single row** in the table gets updated.
> 
> ```sql
> -- This changes ALL minions' skill — probably not what you want!
> UPDATE Minions
> SET Skill = 'Advanced Chaos';
> ```

## DELETE

Remove one or more rows from a table.

```sql
DELETE FROM Minions
WHERE MinionID = 3;
-- Stuart is removed
```

> [!warning] 
> DELETE without WHERE Destroys Everything Just like `UPDATE`, if you leave out the `WHERE` clause, **all rows are deleted**.

# Example — The Villain & Minions Database

Here is how a full database creation and population workflow looks, step by step.

**Step 1 — Clean up any leftover tables from before:**

```sql
DROP TABLE IF EXISTS Robots;
DROP TABLE IF EXISTS Minions;
DROP TABLE IF EXISTS Villains;
```

**Step 2 — Create the tables:**

```sql
CREATE TABLE Villains (
    VillainID   INT PRIMARY KEY,
    VillainName VARCHAR(50),
    EvilPlan    VARCHAR(100),
    DangerLevel INT
);

CREATE TABLE Minions (
    MinionID     INT PRIMARY KEY,
    MinionName   VARCHAR(50),
    Skill        VARCHAR(50),
    LoyaltyLevel INT
);

CREATE TABLE Assignments (
    AssignmentID INT PRIMARY KEY,
    VillainID    INT,
    MinionID     INT,
    Mission      VARCHAR(100)
);
```

**Step 3 — Insert the data:**

```sql
INSERT INTO Villains VALUES
    (1, 'Gru',     'Steal the Moon',           9),
    (2, 'Vector',  'Steal the Pyramid',         7),
    (3, 'Dr Evil', 'Demand 1 Million Dollars',  8);

INSERT INTO Minions VALUES
    (1, 'Kevin',  'Leadership',      10),
    (2, 'Bob',    'Chaos',            6),
    (3, 'Stuart', 'Music',            7),
    (4, 'Dave',   'Rocket Building',  8);

INSERT INTO Assignments VALUES
    (1, 1, 1, 'Moon Heist'),
    (2, 1, 4, 'Rocket Construction'),
    (3, 2, 2, 'Pyramid Recon'),
    (4, 3, 3, 'Laser Testing');
```

**Step 4 — Verify everything looks right:**

```sql
SELECT * FROM Villains;
SELECT * FROM Minions;
SELECT * FROM Assignments;
```

> [!tip] 
> Always Verify After Inserting Running a quick `SELECT *` after bulk inserts is a good habit — it helps catch mistakes like wrong column order or missing values before they cause bigger problems.

The schema for this database looks like this:

```
Villains              Assignments           Minions
-----------           -----------           ----------
VillainID (PK) <---   VillainID             MinionID (PK)
VillainName           MinionID  ----------> MinionName
EvilPlan              AssignmentID (PK)     Skill
DangerLevel           Mission
```

# The Three Most Dangerous SQL Commands

> [!warning] SQL Disasters to Avoid These three commands can cause serious, hard-to-reverse damage:
> 
> 1. **`UPDATE` without `WHERE`** — overwrites every row
> 2. **`DELETE` without `WHERE`** — wipes every row
> 3. **`DROP TABLE`** — destroys the table structure AND all the data permanently
> 
> `DROP TABLE` is sometimes called the "nuclear option" — there is no undo without a backup.

```sql
-- This is irreversible (without a backup):
DROP TABLE Assignments;
-- Table structure: gone. All rows: gone. No undo.
```

# Debugging SQL

When your SQL fails, follow this checklist:

1. **Read the error message** — it usually tells you exactly what went wrong
2. **Check column names** — a typo in a column name will cause an error
3. **Check data types** — inserting text into an `INT` column will fail
4. **Verify table structure** — use `DESCRIBE tablename;` to see what the table looks like

# Tags

#databases #sql #ddl #dml #create_table #insert #update #delete #constraints #primary_key #data_types #sql_basics
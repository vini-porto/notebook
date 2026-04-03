
| Problem                                                 | Solution                                          |
| ------------------------------------------------------- | ------------------------------------------------- |
| Queries are getting too long and complex                | **Views** — save the query once, reuse it forever |
| Searching millions of rows is too slow                  | **Indexes** — skip directly to the data you need  |
| Users are accidentally (or maliciously) destroying data | **DCL** — control who can do what                 |

# Views

## What is a View?

> [!note] Definition 
> A **View** is a saved SQL query that the database treats like a real table. It does **not** store any data itself — it pulls data dynamically from the underlying base tables every time you query it.

The best analogy: a view is like a **window into a room**. The room (the table) holds all the actual data. The window (the view) just gives you a specific, filtered way to look at it. If furniture moves in the room, the view through the window updates instantly.

## Why Use Views? The Two S's

**1. Simplicity** Instead of writing a massive JOIN query every morning, you write it once, save it as a view, and from then on query it like a simple table. _Hide the ugly code._

**2. Security** You can expose only the columns you want. If you have a `Payroll` table with `MinionID`, `Name`, `BankAccount`, and `Salary`, you can create a view that shows only `MinionID` and `Name`. Users querying the view physically **cannot** ask for the `Salary` column. _Hide the sensitive data._

## Creating a View

The syntax is very simple — just put `CREATE VIEW [Name] AS` in front of a `SELECT` statement.

> [!tip] Naming Convention 
> Prefix your view names with `vw_` or `v_` so you never confuse them with real tables. For example: `vw_ActiveMinions`.

```sql
-- Create a view of only active minions for HR
CREATE VIEW vw_ActiveMinions AS
SELECT MinionID, Name, Skill
FROM Minions
WHERE Status = 'Active';
```

## Querying a View

Once created, query it exactly like a normal table. You can even add extra `WHERE` filters on top.

```sql
-- Basic query
SELECT * FROM vw_ActiveMinions;

-- Further filtering on top of the view
SELECT Name FROM vw_ActiveMinions WHERE Skill = 'Chaos';
```

## Security Use Case: The Phonebook

```sql
-- Payroll has: MinionID, Name, BankAccount, Salary
-- We expose only the safe columns
CREATE VIEW vw_MinionPhonebook AS
SELECT MinionID, Name FROM Payroll;
```

Now you can give all minions access to `vw_MinionPhonebook` — they can query it freely, but they will **never** be able to retrieve `Salary` or `BankAccount` through it.

## Complexity Use Case: The Boss's Dashboard

```sql
-- Without a view, Dr. Nefario has to type this every time:
SELECT b.Model, p.PartName, p.Cost
FROM Bots b JOIN Parts p ON b.PartID = p.PartID;

-- With a view, save it once...
CREATE VIEW vw_BotCost AS
SELECT b.Model, p.PartName, p.Cost
FROM Bots b JOIN Parts p ON b.PartID = p.PartID;

-- ...and Nefario just types this forever:
SELECT * FROM vw_BotCost;
```

## Modifying and Dropping Views

To **change** a view's underlying query, you don't use `ALTER`. Use `CREATE OR REPLACE` (or drop and recreate):

```sql
DROP VIEW IF EXISTS vw_ActiveMinions;

CREATE VIEW vw_ActiveMinions AS
SELECT MinionID, Name, Skill, Shift
FROM Minions WHERE Status = 'Active';
```

To **delete** a view:

```sql
DROP VIEW vw_ActiveMinions;
```

> [!important] Dropping a View Does NOT Delete Data 
> When you drop a view, you are only removing the "window". The underlying table (e.g., `Minions`) is completely untouched. You cannot lose data by dropping a view.

## Updatable Views

Can you run `INSERT` or `UPDATE` through a view? Sometimes, yes — but only if the view is simple enough that the database can figure out exactly which base table row to modify.

|View Type|Updatable?|Condition|
|---|---|---|
|Simple view|Yes|Maps 1-to-1 to a single table, no grouping or math|
|Complex view|No|Contains `JOIN`, `GROUP BY`, `DISTINCT`, or aggregate functions like `AVG`, `SUM`|

> [!warning] Why Complex Views Are Read-Only 
> If a view joins two tables, the database doesn't know which base table to apply your `INSERT` to. So it refuses entirely.

# Indexes

## The Performance Nightmare

Imagine the `Bots` table has **5 million rows**. You run:

```sql
SELECT * FROM Bots WHERE SerialNo = 'X99';
```

Without an index, the database does a **Full Table Scan** — it reads every single row from row 1 to row 5,000,000 to find the match. This is extremely slow and will crash your app under load.

## The Book Analogy

> [!example] Book Index Analogy 
> How do you find mentions of "Gru" in a 1,000-page book?
> 
> - **Without an index:** Read every page from 1 to 1,000.
> - **With an index:** Flip to the back, look up "G", see "Pages 14, 52", jump straight there.
> 
> A database index works exactly the same way — it's a sorted lookup structure that points directly to the physical data.

## The B-Tree: How Indexes Work Internally

> [!note] B-Tree (Balanced Tree) 
> 
> Indexes are stored in memory using a **B-Tree** (Balanced Tree) data structure. At each step, the search space is cut in half: "Is the value greater than 50? Go right. Less than 50? Go left."
> 
> Searching **1 million rows** takes about **20 steps** instead of 1,000,000. That's the power of a B-Tree.

For more on B-Trees, see: [B-Tree Visualization](https://www.cs.usfca.edu/~galles/visualization/BTree.html)

This connects to [[Data Structures]] and [[Algorithms]] — the B-Tree is one of the most important structures in all of computer science.

## Two Types of Indexes

### Clustered Index

- Determines the **physical sort order** of the table (like a phonebook sorted A–Z).
- **Automatically created** when you define a `PRIMARY KEY`.
- **Only ONE per table** — because data can only be physically sorted one way.

### Non-Clustered Index

- A **separate lookup table** containing the indexed column values and a pointer to the actual row.
- Like a textbook's index at the back — it's separate from the content pages.
- **Many per table** — you can have as many as you need (within reason).

## Creating an Index

```sql
-- Index the Skill column because we search by it often
CREATE INDEX idx_minion_skill ON Minions (Skill);
```

> [!tip] Naming Convention 
> Prefix with `idx_`, followed by the table name, followed by the column name. Example: `idx_minion_skill`.

### Composite Indexes

Index multiple columns together when your queries frequently filter on both:

```sql
CREATE INDEX idx_skill_status ON Minions (Skill, Status);

-- This index speeds up queries like:
SELECT * FROM Minions WHERE Skill = 'Music' AND Status = 'Active';
```

## The Great Trade-Off

> [!important] Read Speed vs. Write Speed 
> Indexes make **reading** (SELECT) lightning fast, but they make **writing** (INSERT, UPDATE, DELETE) **slower**.
> 
> Every time you insert a new row, the database must also update and reorganize every index attached to that table. The more indexes you have, the more work each write operation takes.

## When NOT to Index

1. **Very small tables** — a full table scan of 100 rows is faster than checking an index.
2. **Columns updated constantly** — the index will slow down every write.
3. **Low-uniqueness columns** — columns like `Gender` or `Yes/No` flags. An index on a column with only 2 possible values provides almost no benefit.

## Dropping an Index

```sql
DROP INDEX idx_minion_skill;
```

This restores write performance at the cost of read performance. Use it when an index is no longer helping, or when writes are being bottlenecked.

## Verifying Your Index Works: EXPLAIN

> [!tip] The EXPLAIN 
> Command Put `EXPLAIN` before any query to see the database's execution plan — its "roadmap" for how it will find the data.
> 
> ```sql
> EXPLAIN SELECT * FROM Minions WHERE Skill = 'Music';
> ```
> 
> - **"Table Scan"** in the output = bad. No index is being used.
> - **"Index Seek"** in the output = fast. The index is working.

# DCL (Data Control Language)

## What is DCL?

> [!note] Definition 
> **Data Control Language (DCL)** manages user permissions in the database. There are two primary commands:
> 
> - `GRANT` — gives a user permission to do something.
> - `REVOKE` — takes that permission away.

This connects to [[Cybersecurity]] — access control is one of the foundational concepts in securing any system.

## The Principle of Least Privilege

> [!important] Core Security Concept 
> Users should only have the **bare minimum permissions** required to do their job. Nothing more.
> 
> - A reporting tool only needs `SELECT`.
> - A data entry clerk needs `INSERT` and `UPDATE`, but not `DROP` or `DELETE`.
> - An intern should never have `DROP TABLE` permissions.

## GRANT

Give a user permission to perform specific actions on specific tables.

```sql
-- Basic syntax
GRANT [Privilege] ON [Table] TO [User];

-- Let Dave read the Minions table
GRANT SELECT ON Minions TO 'Dave';

-- Let Kevin read and add new Minions
GRANT SELECT, INSERT ON Minions TO 'Kevin';

-- Give Dr. Nefario full admin access to everything
GRANT ALL PRIVILEGES ON *.* TO 'DrNefario';
```

## REVOKE

Take permissions away from a user.

```sql
-- Basic syntax
REVOKE [Privilege] ON [Table] FROM [User];

-- Remove Dave's ability to delete from Assignments
REVOKE DELETE ON Assignments FROM 'Dave';
```

> [!note] REVOKE is Surgical 
> Revoking one privilege does not remove others. In the example above, Dave loses `DELETE` on `Assignments`, but keeps any other privileges he was previously granted (like `SELECT`).

## The Ultimate Defense: Views + DCL Combined

This is the most powerful pattern in the lecture. The goal: let minions update their own phone numbers without ever touching the `Payroll` table.

**Step 1:** Create an updatable view that hides the sensitive columns.

```sql
CREATE VIEW vw_MinionPhonebook AS
SELECT MinionID, Name FROM Payroll;
```

**Step 2:** Grant permissions on the view only — not on the base table.

```sql
GRANT UPDATE ON vw_MinionPhonebook TO 'AllMinions';
```

Now minions can update their name through the view, but they cannot touch `Salary` or `BankAccount` — those columns don't even exist in the view.

## DCL as Defense Against SQL Injection

> [!warning] SQL Injection 
> 
> A hacker using SQL Injection might inject something like:
> 
> ```sql
> '; DROP TABLE Users; --
> ```
> 
> If your web app's database user was only `GRANT`ed `SELECT` permission, the `DROP TABLE` command will be **rejected by the database**. DCL is your last line of defense.

For more on SQL Injection, see: [OWASP SQL Injection](https://owasp.org/www-community/attacks/SQL_Injection)

## Roles: Group Security

Managing permissions for 10,000 individual users is impractical. Instead, create **Roles** (groups of permissions) and assign users to roles.

```sql
-- Create a role
CREATE ROLE 'DataEntry';

-- Grant permissions to the role
GRANT INSERT, UPDATE ON Parts TO 'DataEntry';

-- Assign users to the role
GRANT 'DataEntry' TO 'Stuart', 'Bob', 'Kevin';
```

Now if you need to change what `DataEntry` workers can do, you change the role once — and all 3 (or 3,000) users are updated instantly.

# Practice Exercises

> [!example] Exercise 1: View Generation **Scenario:** Dr. Nefario wants a view named `vw_FailedBots` that shows the `ModelName` of any bot with a `'Failed'` status.
> 
> **Answer:**
> 
> ```sql
> CREATE VIEW vw_FailedBots AS
> SELECT ModelName
> FROM Bots
> WHERE Status = 'Failed';
> ```

> [!example] Exercise 2: Index & Security **Scenario 1:** Queries searching the `Minions` table by `HireDate` are very slow. Fix it. **Scenario 2:** Dave was just promoted to HR. Give him permission to add new minions.
> 
> **Answers:**
> 
> ```sql
> -- Scenario 1: Create an index on HireDate
> CREATE INDEX idx_minion_hiredate ON Minions(HireDate);
> 
> -- Scenario 2: Grant Dave INSERT permission
> GRANT INSERT ON Minions TO 'Dave';
> ```

---

# Summary

|Concept|What it does|Key Command(s)|
|---|---|---|
|**View**|Virtual table — saves a query, hides complexity or sensitive columns|`CREATE VIEW`, `DROP VIEW`|
|**Index**|B-Tree lookup — speeds up `SELECT` at the cost of slower writes|`CREATE INDEX`, `DROP INDEX`, `EXPLAIN`|
|**DCL**|Controls who can do what — enforces Least Privilege|`GRANT`, `REVOKE`, `CREATE ROLE`|

> [!important] The Golden Combo Views + DCL together = a powerful security layer. Create a view to hide sensitive columns, then grant permissions only on the view. Users can interact with data safely without ever touching the raw tables.

---

Related notes: [[Databases]], [[Data Structures]], [[Algorithms]], [[Cybersecurity]]

---

## Tags

#databases #sql #views #indexes #dcl #security #btree #data_control_language #least_privilege #sql_injection #roles #permissions
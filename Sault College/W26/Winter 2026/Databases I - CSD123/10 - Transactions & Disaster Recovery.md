# Why Do We Even Need This?

Imagine this scenario. Dr. Nefario wants to move 1,000 bananas from the Vault to the Breakroom. In SQL, that requires **two separate commands**:

```sql
UPDATE Inventory SET qty = qty - 1000 WHERE room = 'Vault';
UPDATE Inventory SET qty = qty + 1000 WHERE room = 'Breakroom';
```

Query 1 runs fine — 1,000 bananas disappear from the Vault. Then, mid-execution, someone trips over the server power cord. The server shuts down **before Query 2 runs**.

Now 1,000 bananas have vanished from the Vault and never arrived in the Breakroom. They are just... gone.

> [!warning] This is the core danger 
> When multiple SQL statements depend on each other, a crash or error between them can leave the database in a **broken, inconsistent state**. This is called a **partial execution** problem, and it is one of the most dangerous things that can happen to a database.
> 
# The ACID Properties

To solve the partial execution problem, enterprise databases like MySQL and SQL Server are built around four fundamental guarantees, collectively known as **ACID**.

> [!note] What is ACID? ACID stands for:
> 
> - **A** — Atomicity
> - **C** — Consistency
> - **I** — Isolation
> - **D** — Durability
> 
> If a database engine cannot guarantee **all four**, it cannot be trusted with financial or critical data.

## Atomicity — All or Nothing

> [!important] The core idea 
> A **transaction** is a bundle of multiple SQL statements treated as a single unit. Atomicity guarantees that **either ALL statements succeed, or NONE of them do**.

If step 3 of a 4-step transaction fails, the database automatically **undoes steps 1 and 2** as well. There is no in-between.

> [!example] The Banana Transfer, fixed 
> If the banana transfer had been wrapped in an atomic transaction, when the server rebooted after the power cut, the database engine would detect that Query 2 never finished. It would **automatically reverse Query 1**, and all 1,000 bananas would reappear in the Vault — safe and accounted for.

## Consistency — Following the Rules

> [!note] Definition 
> Consistency means that every transaction must bring the database from one **valid state** to another **valid state**. It must respect all rules, constraints, and data types defined in the schema.

> [!example] Consistency in Action Imagine a transaction tries to:
> 
> - Set a Minion's salary to `"Banana"` instead of a number
> - Assign a `BotID` that does not exist in the Bots table (a foreign key violation)
> 
> Consistency means the database will **instantly reject the entire transaction** and throw an error. Bad data never gets in.

## Isolation — No Peeking

> [!note] Definition 
> Isolation ensures that **concurrent transactions do not interfere with each other**. A transaction in progress is invisible to all other transactions until it is fully complete.

This is critical in multi-user systems where hundreds of users may hit the database at the exact same millisecond.

> [!example] The Last Jetpack Thruster 
> There is exactly 1 Jetpack Thruster left in Inventory. Kevin and Stuart both click "Assign to my Bot" at the exact same time.
> 
> **Without Isolation:** Both transactions read "qty = 1", both proceed, and both claim the same thruster. Now there are -1 thrusters. Chaos.
> 
> **With Isolation:** The database forces them into a queue. Kevin gets the thruster, inventory drops to 0, and Stuart receives an "Out of Stock" error. Correct behavior.

## Durability

> [!note] Definition 
> Once a transaction is **committed** (finalized), it is written permanently to the **physical hard drive**. Even if the server loses power 0.1 seconds after the commit, the data will still be there when it reboots.

The key distinction is: data is not just sitting in fast but volatile **RAM** — it is on the disk.

> [!example] Durability in Action 
> You finish inserting 50 new Minion hires, click Save (Commit), and see a success message. A microsecond later the building loses power. Because of Durability, the database had already guaranteed the data was written to disk before sending you that success message. When the server reboots, all 50 Minions are still there.

# Transaction Control Language (TCL)

You already know the other SQL sub-languages:

- **DDL** — `CREATE`, `DROP`, `ALTER`
- **DML** — `INSERT`, `UPDATE`, `DELETE`
- **DQL** — `SELECT`
- **DCL** — `GRANT`, `REVOKE`

**TCL** is the final piece. It lets DBAs manually control transactions — grouping statements together and testing them safely before committing.

## The Four TCL Commands

### `START TRANSACTION`

```sql
START TRANSACTION;
```

Tells the database: _"Hold on. Don't save anything permanently yet. Open a temporary workspace."_

> [!tip] SQL Server note 
> In Microsoft SQL Server, this command is written as `BEGIN TRAN` instead.

### `COMMIT`

```sql
COMMIT;
```

Tells the database: _"Everything worked. Save this to the hard drive permanently."_

Once you `COMMIT`, Atomicity and Durability lock the data in. **You cannot undo a COMMIT.**

### `ROLLBACK`

```sql
ROLLBACK;
```

Tells the database: _"I made a mistake. Undo everything since I typed START TRANSACTION."_

Think of it as **Ctrl+Z** for your database operations.

### `SAVEPOINT`

```sql
SAVEPOINT savepoint_name;
ROLLBACK TO savepoint_name;
```

> [!note] Why do we need SAVEPOINTs? 
> 
> If you are running a massive script with 100 steps and step 99 fails, a full `ROLLBACK` deletes all 98 previously successful steps. That is painful.
> 
> A **SAVEPOINT** is a checkpoint — a bookmark you place mid-transaction. If something fails later, you can roll back _only to that checkpoint_, not all the way to the beginning.

> [!example] SAVEPOINT in practice
> 
> ```sql
> START TRANSACTION;
> 
> INSERT INTO Minions VALUES (21, 'Phil');
> SAVEPOINT phil_added;
> 
> INSERT INTO Assignments VALUES (99, 21, 'Stolen Moon'); -- ERROR! Mission doesn't exist.
> ROLLBACK TO phil_added;
> 
> COMMIT;
> ```
> 
> Result: Phil is successfully hired. The bad assignment is discarded. The good work is preserved.

## TCL as a Safety Net

> [!tip] The DBA's best trick for risky operations Ever been terrified of running a `DELETE` statement? What if you forget the `WHERE` clause and wipe the entire table?
> 
> Use this workflow:
> 
> 1. `START TRANSACTION;`
> 2. Run your `DELETE FROM Minions WHERE Status = 'Retired';`
> 3. `SELECT * FROM Minions;` — inspect the result carefully
> 4. If something is wrong: `ROLLBACK;`
> 5. If it looks perfect: `COMMIT;`
> 
> Nothing is permanent until you say so.

## Database Locks

When you start a transaction and `UPDATE` a row, the database places a **lock** on that row. No other user can modify that row until you `COMMIT` or `ROLLBACK`.

> [!warning] Always finish your transactions
>  If you start a transaction, update some rows, and then go to lunch without committing or rolling back — you will lock those rows for everyone else. In a busy application, this can freeze entire features for your users.

## Deadlocks

A **deadlock** is a special case where two transactions are stuck waiting on each other forever.

|Kevin's transaction|Stuart's transaction|
|---|---|
|Locks **Table A**|Locks **Table B**|
|Needs **Table B** to finish|Needs **Table A** to finish|
|Waiting for Stuart...|Waiting for Kevin...|

Neither can proceed. This is a **deadlock**.

> [!note] How the database resolves a deadlock 
> 
> The database engine detects the standoff automatically. It picks one transaction to be the **"Victim"**, forcefully **kills and rolls back** that transaction, and frees its locks so the other transaction can complete.
> 
> The application layer must be coded to **catch this error** and retry the victim's request. The database will not retry it for you.

# Disaster Recovery

ACID properties protect the database from bad code and power outages. But what about:

- The server room catching fire?
- A ransomware attack encrypting the entire machine?
- A flood destroying the building?

For physical disasters, we need **Disaster Recovery (DR)** protocols.

## The SQL Dump — How Backups Are Made

Tools like `mysqldump` export your entire database into a massive text file packed with `CREATE TABLE` and `INSERT` statements — a complete reconstruction script.

> [!example] 
> If the server dies entirely, you buy a new server, run the dump file as a script, and the entire database is rebuilt from scratch.

## Full vs. Incremental Backups

|Type|What it does|When to use|
|---|---|---|
|**Full Backup**|Copies the entire database (e.g., 500 GB)|Weekly, usually on Sunday nights|
|**Incremental Backup**|Copies only data that changed since the last backup|Daily, much faster and smaller|

Enterprises typically use a **combination of both** to balance recovery completeness against disk space and network cost.

## Point-in-Time Recovery (PITR)

> [!note] Definition 
> Modern enterprise databases keep a **running log of every single transaction** ever executed. PITR lets a DBA rewind the database to its exact state at any specific moment in history.

> [!example] Bob accidentally runs `DROP TABLE Payroll` at 2:14 PM on a Wednesday.
> 
> With PITR, the DBA can restore the database to exactly **2:13 PM** — one minute before Bob's mistake — recovering everything without losing the rest of the day's work.

## The 3-2-1 Backup Rule

> [!important] The golden rule of IT Disaster Recovery
> 
> - **3** — Keep **three copies** of your data (1 primary + 2 backups)
> - **2** — Store them on **two different types of media** (e.g., local server + external hard drive)
> - **1** — Keep at least **one copy off-site** (e.g., AWS, Azure, or a different physical city)
> 
> This protects against hardware failure, local disasters, and even ransomware that might encrypt your local backups.

## The Golden Rule of Backups

> [!warning] 
> Critical mindset for any DBA _"An untested backup is not a backup. It is just a wish."_
> 
> Many companies have suffered catastrophic data loss only to discover their automated backups had been **silently failing for months**. A DBA must **regularly practice restoring** backups to a test server to verify the files actually work.

# Summary

|Concept|One-line reminder|
|---|---|
|**Atomicity**|All or nothing — no partial updates|
|**Consistency**|Rules are always enforced — bad data is rejected|
|**Isolation**|Concurrent transactions can't see each other mid-flight|
|**Durability**|A committed transaction survives any crash|
|**START TRANSACTION**|Opens a safe temporary workspace|
|**COMMIT**|Saves permanently — cannot be undone|
|**ROLLBACK**|Undoes everything back to START TRANSACTION|
|**SAVEPOINT**|A checkpoint to rollback to mid-transaction|
|**Lock**|Prevents other users from editing a row mid-transaction|
|**Deadlock**|Two transactions blocking each other — DB picks a victim|
|**SQL Dump**|Full database export as a reconstruction script|
|**Incremental Backup**|Only backs up changes since the last backup|
|**PITR**|Rewinds the database to any specific moment in time|
|**3-2-1 Rule**|3 copies, 2 media types, 1 off-site|

# Tags

#databases #transactions #ACID #TCL #SQL #disaster_recovery #data_integrity #backups #deadlocks
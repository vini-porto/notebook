
So far we've built a solid database — tables, views, indexes, and security. But here's the catch: right now the only way to use it is by writing raw SQL. Nobody in the real world wants to do that. This lecture is about closing that gap — how real apps connect to databases, how to do it safely, and what to do when SQL itself isn't the right tool anymore.

# The Client-Server Model

## SQLite vs. Enterprise Databases

> [!note] SQLite 
> SQLite stores the entire database as **a single file on your hard drive**. It's perfect for learning or for mobile apps, but it doesn't scale.

When you move to production-grade systems like **MySQL**, **PostgreSQL**, or **SQL Server**, the database becomes a **running engine (a Server)**. It sits on a machine, listens on a network port, and waits for clients to connect to it.

To talk to this server, you need a **Client** — a program that sends requests to the server and shows you the results.

## What is DBeaver?

**DBeaver** is a _universal database client_. It doesn't store any data itself — it just gives you a visual interface to connect to almost any database server in the world (MySQL, PostgreSQL, SQL Server, SQLite, and more).

> [!tip] Learning 
> DBeaver once means you can connect to any database engine you're not locked into one tool.

## The Connection String

To connect a client to a server, you need four things:

|Field|What it means|Example|
|---|---|---|
|**Host**|Where is the server?|`localhost` or `192.168.1.10`|
|**Port**|Which network door?|`3306` for MySQL|
|**Username**|Who are you?|`root`, `dave`|
|**Password**|The key|`*****`|

Together, these four pieces are called a **connection string**. Think of it like a phone number with a room extension and a password.

# Database-Driven Applications

## Why Raw Data Isn't Enough

Users don't want to stare at rows and columns of SQL output. They want **buttons, charts, dashboards, and images**. We need a bridge between the database and the human — that bridge is an **application**.

## The 3-Tier Architecture

Modern applications are built in three separate layers, each with a very specific job.

> [!important] The 3-Tier Architecture
> 
> 1. **Presentation Tier** — What the user sees (Front-End)
> 2. **Logic Tier** — The brain that processes requests (Back-End)
> 3. **Data Tier** — Where data lives (Database)

### Tier 1: The Presentation Tier (Front-End)

This is everything the user sees and interacts with directly. It's built using:

- **HTML** — the structure (headings, buttons, forms)
- **CSS** — the styling (colours, fonts, layout)
- **JavaScript** — the interactivity (clicks, animations, sending data)

The front-end never talks to the database directly. It talks to the middle layer.

### Tier 2: The Logic Tier (Back-End / Application Server)

This is the _brain_ of the app. It lives on a **web server** and is built using languages like **Node.js**, **Python**, **Java**, or **C#**.

When a user clicks a button, JavaScript sends the request to this server. The server figures out what to do — it writes a SQL query, talks to the database, gets the result, and sends it back to the front-end.

### Tier 3: The Data Tier (Database)

The **storage vault**. This is your MySQL, PostgreSQL, or SQL Server database. Its only job is to store data safely, enforce rules, and respond to queries efficiently.

## The Flow of Information

Here's how all three tiers work together in a real example:

> [!example] A user submits a form
> 
> 1. User clicks **"Submit Assignment"** on the web page (Presentation Tier)
> 2. JavaScript sends the data (e.g., `BotID = 4`) to the Application Server
> 3. Node.js (Logic Tier) dynamically builds an `INSERT INTO` SQL string
> 4. The SQL is sent to the Database (Data Tier)
> 5. The database saves the record and returns a "Success" message
> 6. The success message travels back up through the tiers to show the user a confirmation

## Database Drivers

> [!note] What are Drivers? 
> A **driver** is a piece of software that lets a programming language (like Python or Node.js) communicate with a specific database engine. Examples: `ODBC`, `JDBC`, `npm mysql2` (for Node.js + MySQL)

Drivers handle the complex network handshake so developers can simply pass a SQL string and get results back — without worrying about the low-level connection details.

# SQL Injection

## What is SQL Injection?

> [!warning] SQL Injection
> If a user types malicious SQL code into a form field, and the application just **glues it directly into a SQL string**, the database will execute it as real SQL.

**Example attack:** A login form asks for a username. Instead of their name, a hacker types:

```
'; DROP TABLE Assignments; --
```

If the app naively builds the query like this:

```sql
SELECT * FROM Users WHERE Name = '' + userInput + ''
```

The final query becomes:

```sql
SELECT * FROM Users WHERE Name = ''; DROP TABLE Assignments; --'
```

The database runs both statements — and the entire `Assignments` table is gone.

## The Solution: Parameterized Queries

> [!important] 
> Always use Parameterized Queries Never concatenate (glue) user input directly into SQL strings.

Instead, use **placeholders** (`?` or `@param`) in your SQL, and pass the data separately:

```sql
SELECT * FROM Minions WHERE Name = ?
```

The application sends the **SQL structure first**, then the **user data second** as a separate parameter. The database engine treats the input as _pure data_ — it can never be interpreted as a SQL command, no matter what the user types.

```javascript
// Node.js example (safe)
const query = "SELECT * FROM Minions WHERE Name = ?";
db.execute(query, [userInput]);
```

> [!tip] 
> Parameterized queries are the single most important security practice when building database-driven applications. Make it a habit — always.


# Introduction to NoSQL

## When Relational Databases Hit Their Limits

SQL databases are built on strict rules — fixed tables, defined columns, enforced data types, relationships. That rigidity is great for consistency, but it creates problems:

- What if the **structure of the data changes constantly**?
- What if you have **petabytes of data** arriving every second?
- What if you need to serve **millions of users globally** at the same time?

## Scaling: Vertical vs. Horizontal

> [!note] Two Ways to Scale
> 
> - **Vertical Scaling (SQL)** — Buy a _bigger, more expensive_ single server (more RAM, faster CPU). There's a physical limit to how big one machine can get.
> - **Horizontal Scaling (NoSQL)** — Buy _100 cheap servers_ and chain them together. Add more servers as traffic grows.
> 
> **SQL scales UP. NoSQL scales OUT.**

## What is NoSQL?

**NoSQL** stands for _"Not Only SQL"_ (sometimes _Non-Relational_). It's a completely different philosophy for storing data:

- No rigid tables
- No forced relationships
- No fixed columns
- Prioritises **speed**, **flexibility**, and **horizontal scaling**

> [!important] 
> NoSQL doesn't replace SQL, it solves _different_ problems. Some problems are better suited to SQL; others are better suited to NoSQL.

## The 4 Types of NoSQL Databases

|Type|Example|Best For|
|---|---|---|
|**Document Databases**|MongoDB|Web apps, flexible data, JSON|
|**Key-Value Stores**|Redis|Caching, sessions, ultra-fast lookups|
|**Column-Family Stores**|Cassandra|Massive write-heavy workloads|
|**Graph Databases**|Neo4j|Relationships, social networks, recommendations|

# Deep Dive: Document Databases (MongoDB)

The most popular NoSQL format. Instead of rows, data is stored as **Documents** — and those documents are formatted as **JSON**.

## Understanding JSON

> [!note] JSON — JavaScript Object Notation 
> JSON organises data as **key-value pairs** inside curly braces `{ }`. It can nest arrays and objects inside each other, allowing complex, multi-level data in a single document.

```json
{
  "name": "Kevin",
  "age": 7,
  "skills": ["Bananas", "Leadership"],
  "address": {
    "city": "Villain-ville",
    "country": "Despicable Land"
  }
}
```

This is perfect for web development because the front-end (JavaScript) and the database both use the **exact same format**.

## SQL vs. Document DB

> [!example] Storing Recipes **In SQL:** A chocolate chip cookie has 8 ingredients. A wedding cake has 40. You'd need:
> 
> - A `Recipes` table
> - An `Ingredients` table
> - A `RecipeIngredients` linking table (Many-to-Many)
> - A complex JOIN query just to get one recipe
> 
> **In a Document Database:** The whole recipe is just one JSON document:
> 
> ```json
> {
>   "recipe": "Chocolate Chip Cookies",
>   "ingredients": ["Flour", "Sugar", "Chocolate Chips"],
>   "bakeTime": "12 mins"
> }
> ```
> 
> No JOINs. Just fetch the document.

## Schema-less Flexibility

In a NoSQL document collection, **each document can have completely different fields**. No `ALTER TABLE` needed.

```json
// Document 1
{ "Name": "Kevin", "Skill": "Leadership" }

// Document 2 — totally different fields, same collection
{ "Name": "Stuart", "FavoriteFood": "Banana", "EyeCount": 1 }
```

> [!warning] Flexibility has a trade-off 
> While schema-less design is powerful, it means the database doesn't enforce consistency. It's up to the application to make sure the data makes sense. This can lead to messy, inconsistent data if you're not disciplined.

# Other NoSQL Flavours

## Key-Value Stores (Redis)

The **simplest and fastest** databases in existence. Like a giant dictionary:

- You give it a **Key** → it gives you a **Value**
- Stored entirely **in RAM** (not on disk) — hence the extreme speed

**Primary use case:** Caching. When a web page loads, Redis stores the result of expensive database queries so the next request can get the data in milliseconds without hitting the main database at all.

> [!example] Key: `"User_123_Cart"` → Value: `"Laser Gun, Freeze Ray"`

## Graph Databases (Neo4j)

Built specifically to **map relationships** between entities rather than store data in tables.

Used by:

- **Social networks** — "Minion A is friends with Minion B, who works with Minion C"
- **Recommendation engines** — Amazon's "Customers who bought this also bought..." or Netflix's "You might also like..."

# SQL vs. NoSQL

## ACID vs. BASE

> [!important] Two Consistency Models **SQL → ACID**
> 
> - **A**tomicity — A transaction is all-or-nothing
> - **C**onsistency — The database is always in a valid state
> - **I**solation — Transactions don't interfere with each other
> - **D**urability — Once saved, data survives crashes
> 
> **NoSQL → BASE**
> 
> - **B**asically **A**vailable — The system is always up, even if some data is stale
> - **S**oft state — Data might be temporarily inconsistent
> - **E**ventually consistent — All nodes will _eventually_ agree on the same data
> 
> ACID guarantees perfect correctness at the cost of some speed. BASE prioritises speed and availability at the cost of instant consistency.

## When to Choose SQL

1. The data is **highly structured and predictable** (it always looks the same)
2. **Data integrity is the top priority** — finance, healthcare, HR, payroll
3. You need **complex queries across multiple tables** (JOINs, aggregations, subqueries)

## When to Choose NoSQL

1. Building a **massive web app** with millions of global users (Twitter, Netflix, Instagram)
2. Your **data structure changes constantly** — IoT sensors, social media feeds, logs
3. You need to **develop fast** using JSON and JavaScript across the full stack

## The Modern Reality: Polyglot Persistence

> [!important] Real companies use both You don't have to pick one forever. The modern approach is called **Polyglot Persistence** — using multiple database types together, each for what it does best.
> 
> - **SQL** (MySQL/PostgreSQL) → billing, user accounts, financial records
> - **MongoDB** → product catalogues, content management
> - **Redis** → session caching, shopping carts, fast lookups
> 
> Use the right tool for the right job.

See also: [[Databases]], [[Software Engineering]], [[Cybersecurity]], [[Computer Networks]]

**Further reading:**

- [MongoDB Documentation](https://www.mongodb.com/docs/)
- [OWASP SQL Injection Guide](https://owasp.org/www-community/attacks/SQL_Injection)
- [Redis Introduction](https://redis.io/docs/get-started/)
- [Parameterized Queries explained (PortSwigger)](https://portswigger.net/web-security/sql-injection)

# Tags

#databases #sql #nosql #mongodb #redis #application_architecture #sql_injection #security #web_development #client_server #json #three_tier_architecture
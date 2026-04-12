# Managed vs. Unmanaged Services

> [!note] Definitions 
> **Unmanaged service** — You provision and control the resource yourself. You decide how it handles load, failures, and scaling. Example: running a database on an [[EC2]] instance.
> 
> **Managed service** — AWS handles the heavy lifting: scaling, patching, backups, and fault tolerance are taken care of automatically. You focus on configuring and using the service. Example: [[Amazon RDS]], [[Amazon S3]].

The trade-off is simple: **unmanaged = more control**, **managed = less operational burden**.

# [[Amazon RDS]]

## What Problem Does It Solve?

Running your own relational database on a server is hard work. You are responsible for:

- Server hardware maintenance
- OS installation and patching
- Database software patching
- Taking regular backups
- Ensuring the database stays available (high availability)
- Planning for future growth (scalability)
- Data security

[[Amazon RDS]] takes almost all of this off your plate.

> [!important] 
> With Amazon RDS, **you manage your application and data**. AWS manages everything else: the OS, the database software, backups, scaling, power, and hardware.

## The Journey from On-Premises to the Cloud

|Scenario|What YOU manage|What AWS manages|
|---|---|---|
|On-premises database|Everything (hardware, networking, OS, DB, backups, HVAC...)|Nothing|
|Database on EC2|OS patching, DB software, backups|Hardware, data center|
|Amazon RDS / Aurora|Application optimization, data|OS, DB software, backups, scaling, availability|

## The DB Instance

The **database instance** is the fundamental unit of Amazon RDS. Think of it as an isolated database environment that can hold multiple databases inside it. You choose:

- The **instance class** (how much CPU and RAM)
- The **storage type** (affects speed and cost)
- The **database engine** to run

Amazon RDS supports six database engines: **[[MySQL]]**, **IBM DB2**, **Microsoft SQL Server**, **[[PostgreSQL]]**, **[[MariaDB]]**, and **Oracle**.

## Running RDS Inside a VPC

You typically run your RDS instance inside an [[Amazon VPC]] (Virtual Private Cloud). This lets you:

- Control your network environment
- Place the database in a **private subnet** (hidden from the internet)
- Only allow access from specific application servers

> [!tip] 
> The subnet you choose also determines the **Availability Zone** (the physical data center location) where your database lives.

## Multi-AZ Deployment

One of the most powerful RDS features is **Multi-AZ deployment**.

Here is how it works:

1. You enable Multi-AZ on your database instance.
2. AWS automatically creates a **standby copy** of your database in a _different_ Availability Zone (a different physical location).
3. All transactions are **synchronously replicated** to the standby — meaning both copies are always in sync.
4. If the primary instance fails, AWS **automatically promotes the standby** to become the new primary.

> [!important] 
> Because your application connects to the database using a **DNS endpoint name** (not a specific IP address), a failover is transparent — your application does not need any code changes to switch over to the standby copy.

## Scaling for Read-Heavy Workloads

For databases that receive a lot of _read_ requests (like a news website), RDS supports **read replicas**.

- Updates from the primary database are **asynchronously copied** to the replica (slight delay is acceptable).
- Your application can send read queries to the replica, reducing load on the primary.
- Read replicas can be placed in a **different AWS Region**, which helps with disaster recovery and reduces latency for geographically distant users.

> [!warning] Multi-AZ vs. Read Replicas 
> - **Multi-AZ** is about **availability and failover** — it protects you when something goes wrong. 
> - **Read replicas** are about **performance and scale** — they help when you have too many read queries. They are _not_ the same thing.

## When to Use (and Not Use) Amazon RDS

> [!tip] Use Amazon RDS when you need:
> 
> - Complex queries or transactions (typical SQL workloads)
> - Medium-to-high query rates (up to ~30,000 IOPS)
> - High durability
> - A single database node

> [!warning] Do NOT use Amazon RDS when you need:
> 
> - Extremely high write rates (e.g., 150,000 writes per second)
> - Sharding across many nodes due to massive data size
> - Simple GET/PUT operations that a NoSQL database handles better
> - Deep customization of the database engine itself
> 
> In those cases, consider [[Amazon DynamoDB]] (NoSQL) or running your database engine directly on [[EC2]].

## RDS Pricing Considerations

The cost of RDS depends on several factors:

- **Clock hours** — you pay from when an instance starts to when it stops.
- **DB instance class** — bigger instances cost more.
- **Purchase type** — _On-Demand_ (pay per hour, no commitment) vs. _Reserved_ (upfront payment for 1 or 3 years, significant discount).
- **Storage** — provisioned storage is billed; backup storage up to 100% of your provisioned size is free while the instance is active.
- **Multi-AZ deployment** — costs more than a single-AZ setup because you are running two copies.
- **Data transfer** — inbound is free; outbound is tiered by volume.

# Amazon DynamoDB

## Relational vs. Non-Relational Databases

| Feature        | Relational (SQL)                              | Non-Relational (NoSQL)                            |
| -------------- | --------------------------------------------- | ------------------------------------------------- |
| Data structure | Structured tables with defined columns        | Flexible — can be unstructured or semi-structured |
| Query language | SQL                                           | Varies (DynamoDB uses its own query model)        |
| Scaling        | Typically scales _vertically_ (bigger server) | Scales _horizontally_ (more servers)              |
| Best for       | Complex joins, transactions                   | High-speed reads/writes, variable data shapes     |

## What is Amazon DynamoDB?

[[Amazon DynamoDB]] is a fully managed **NoSQL** database service designed for applications that need:

- Consistent, **single-digit-millisecond latency**
- Massive scale (some tables hold _billions_ of items)
- Automatic storage scaling

> [!note] 
> DynamoDB is serverless from your perspective — AWS handles all the underlying infrastructure, partitioning, and replication across multiple facilities automatically.

Key features include:

- **SSD storage** for all data — fast reads by default.
- **Global tables** — automatically replicate data across multiple AWS Regions.
- **Encryption at rest** — data is protected without extra configuration.
- **Time-to-Live (TTL)** — automatically delete items after a specified time.
- **Automatic scaling** — DynamoDB can monitor the table load and adjust read/write throughput automatically.

## Core Components: Tables, Items, and Attributes

Think of DynamoDB in terms of three building blocks:

- **Table** — a collection of data (similar to a table in SQL, but schema-flexible).
- **Item** — a single record in a table (like a row in SQL). Each item is uniquely identifiable.
- **Attribute** — a data element within an item (like a column in SQL), but items in the same table can have _different_ attributes.

> [!example] 
> A `Products` table might have one item with attributes `{productId, name, price}` and another with `{productId, name, price, color, size}` — both in the same table, no schema migration needed.

## Primary Keys

DynamoDB requires every item to have a **primary key**. There are two types:

- **Partition key** (simple primary key) — a single attribute that uniquely identifies the item. DynamoDB uses this to determine which physical partition (storage location) holds the data.
- **Partition key + Sort key** (composite primary key) — two attributes combined. Useful when you want to group related items together and sort them.

> [!example] 
> A `Books` table might use `Author` as the partition key and `Title` as the sort key. All books by the same author are grouped together, and within that group they are sorted by title. This makes it very fast to query "all books by Stephen King."

## Querying vs. Scanning

There are two ways to retrieve data:

- **Query** — uses the primary key to find items directly. This is **fast and efficient** because DynamoDB knows exactly where to look.
- **Scan** — reads through _every single item_ in the table looking for matches. This is **flexible but slow** for large tables.

> [!warning] 
> Always prefer **Query** over **Scan** for performance. Design your primary keys to support the queries your application needs most.

# Amazon Redshift

## What is Amazon Redshift?

[[Amazon Redshift]] is a fully managed **data warehouse** service. While RDS and DynamoDB are designed for _transactional_ workloads (fast reads/writes for live applications), Redshift is designed for **analytical** workloads, running complex queries across massive amounts of historical data to produce business insights.

> [!note] 
> A **data warehouse** is a database optimized for analytical queries rather than day-to-day transactions. Instead of "add this order to the system," it answers questions like "what were our total sales by region for the past 3 years?"

## Parallel Processing Architecture

Redshift uses a **leader node + compute nodes** model:

1. The **leader node** receives queries from your BI tool or SQL client, creates an execution plan, and distributes work to the compute nodes.
2. The **compute nodes** execute their assigned parts of the query in parallel and return intermediate results to the leader.
3. The **leader node** aggregates the results and returns the final answer.

This **massively parallel processing (MPP)** architecture is why Redshift can query petabytes of data and return results in seconds.

> [!note] 
> The **Amazon Redshift Spectrum** feature extends this further — it can run queries directly against data stored in [[Amazon S3]], without loading it into Redshift first. This means you can query _exabytes_ of data cost-effectively.

## Key Features

- **Columnar storage** — instead of storing data row by row (like traditional databases), Redshift stores data column by column. This is much more efficient for analytical queries that only need a few columns from billions of rows.
- **Automatic scaling** — scale your cluster up or down with a few clicks in the [[AWS Console]].
- **Security built-in** — encryption at rest and in transit.
- **Standard SQL** — use the tools you already know.
- **JDBC and ODBC connectors** — compatible with popular BI tools (Tableau, Power BI, etc.).

## Use Cases

|Who uses it|Why|
|---|---|
|Enterprises migrating from traditional data warehouses|Agility — start small, scale fast, no procurement delays|
|Small businesses|Cost-effective — no need to buy expensive hardware|
|SaaS companies|Provides analytics capabilities within their applications|
|Any data-heavy business|Handles petabyte-scale data that breaks other systems|

# Amazon Aurora

## What is Amazon Aurora?

[[Amazon Aurora]] is a relational database service that is **compatible with MySQL and PostgreSQL**, but built from the ground up for the cloud. It combines:

- The **performance and availability** of expensive commercial databases (like Oracle)
- The **simplicity and cost** of open-source databases (like MySQL)

> [!important] 
> Aurora is a **fully managed** service. It automates provisioning, patching, backups, failure detection, and repair — all without you needing to intervene.

Because it is drop-in compatible with MySQL and PostgreSQL, you can migrate existing applications to Aurora with little or no code changes.

## Why Choose Aurora Over Standard RDS?

The main reason is **high availability and resilience**.

- Aurora stores **multiple copies of your data across multiple Availability Zones** automatically (not an optional add-on like Multi-AZ in RDS).
- It continuously backs up data to [[Amazon S3]].
- It supports up to **15 read replicas** (RDS supports up to 5 for most engines), dramatically reducing read load on the primary.
- It is designed for **instant crash recovery**.

## Crash Recovery

> [!important] 
> After a database crash, traditional databases must replay a "redo log" from the last checkpoint — this can take a long time. Aurora handles this differently:
> 
> - It performs crash recovery work **on every read operation**, spread continuously across the system.
> - This means the restart time after a crash is **less than 60 seconds** in most cases.
> - The **buffer cache** (a memory structure used to speed up reads) is stored _outside_ the database process, so it is available **immediately** after a restart — no warm-up period needed.

This design eliminates the risk of "brownouts" — periods of degraded performance right after a restart while the cache slowly rebuilds.

## Aurora Service Highlights

- **Pay-as-you-go** — no upfront commitment required.
- Integrates with **[[AWS DMS]]** (Database Migration Service) and the **AWS Schema Conversion Tool** to help you migrate existing databases.
- Highly available by default, with continuous backups and multi-AZ replication built in.

# Choosing the Right AWS Database Service

> [!tip] Quick reference
> 
> |Service|Type|Best for|
> |---|---|---|
> |[[Amazon RDS]]|Relational (SQL)|Standard web/mobile apps, ecommerce, complex queries|
> |[[Amazon DynamoDB]]|NoSQL (key-value / document)|High-speed, massive scale, flexible schema|
> |[[Amazon Redshift]]|Data warehouse|Analytics, business intelligence, historical data|
> |[[Amazon Aurora]]|Relational (SQL)|High-availability workloads needing MySQL/PostgreSQL compatibility|


# Tags

#aws #databases #cloud #rds #dynamodb #redshift #aurora #nosql #sql #datawarehouse #managed-services #high-availability #cloud-computing #infrastructure #cloud-computing 
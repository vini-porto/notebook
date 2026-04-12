# Why Cloud Storage Matters

Cloud storage is more reliable, scalable, and secure than keeping storage on your own physical machines. Nearly everything in the cloud depends on it — databases, big data analytics, IoT devices, backups, and applications all need a place to store their data.

> [!note] The five AWS storage categories
> AWS groups its storage services into five broad types. Understanding when to use each one is a core cloud skill.
> 
> - **Instance store** — Temporary storage attached to an EC2 instance. If the instance stops, the data is gone.
> - **[[Amazon EBS]]** (Elastic Block Store) — Persistent storage you attach to a single EC2 instance, like an external hard drive.
> - **[[Amazon EFS]]** (Elastic File System) — A shared file system that many EC2 instances can use at the same time.
> - **[[Amazon S3]]** (Simple Storage Service) — Object storage accessible from anywhere via a URL. Very durable and scalable.
> - **Amazon S3 Glacier** — Very cheap storage for data you rarely need, like long-term archives.

# [[Amazon EBS]] (Elastic Block Store)

![[Amazon EBS#Why Block Storage?]]

> [!important] Block vs. Object storage This is one of the most tested concepts in AWS:
> 
> - **Block storage** (like EBS): If you change one character in a 1 GB file, only the changed block is updated. Fast, low-latency.
> - **Object storage** (like S3): If you change anything in a file, the entire file must be re-uploaded. More flexible, but slower for frequent edits.
> - 
## Key characteristics of EBS

- **Persistent** — Data survives even when the EC2 instance is turned off.
- **Block-level** — Works like an external hard drive attached to your instance.
- **Single instance** — Only one EC2 instance can mount an EBS volume at a time.
- **Same Availability Zone** — An EBS volume can only be attached to an EC2 instance _in the same AZ_. You cannot attach a volume from us-east-1a to an instance in us-east-1b.
- **Automatically replicated** — Within its AZ, EBS data is replicated to protect against hardware failure.
- **Low-latency** — Because EBS is directly attached, data access is very fast — suitable for running databases.

> [!example] Common EBS use cases
> 
> - Boot volumes for EC2 instances
> - File system storage
> - Database hosts (e.g. running MySQL or PostgreSQL on EC2)
> - Enterprise applications that need persistent, fast storage

![[Amazon EBS#EBS Volume Types]]

There are different EBS volume types depending on how much performance you need:

|Type|Description|Use case|
|---|---|---|
|**General Purpose SSD (gp2/gp3)**|Balanced performance and cost|Most workloads, boot volumes|
|**Provisioned IOPS SSD (io1/io2)**|Highest performance|Databases, latency-sensitive apps|
|**Throughput Optimized HDD (st1)**|High throughput, lower cost|Big data, log processing|
|**Cold HDD (sc1)**|Lowest cost|Infrequently accessed data|

> [!tip] 
> Only SSDs can be used as boot volumes for EC2 instances. HDDs are for additional storage only.

## EBS Snapshots

A _snapshot_ is a point-in-time backup of an EBS volume, stored in [[Amazon S3]].

- The **first snapshot** is called the _baseline_ — it captures everything.
- **Subsequent snapshots** are _incremental_ — they only store what changed since the last snapshot.
- You can copy snapshots to other [[AWS]] Regions for disaster recovery.
- Snapshots can be encrypted and shared.
- You can _restore_ a snapshot by creating a new EBS volume from it.

> [!note] Snapshots and AMIs 
> When you create an Amazon Machine Image ([[AMI]]) from an EC2 instance, it includes the EBS volume snapshot. This lets you launch new EC2 instances that are identical to the original.

![[Amazon EBS#Pricing Model]]

# [[Amazon S3]] (Simple Storage Service)

## What is object storage?

Instead of blocks, S3 stores data as _objects_ inside _buckets_. An object is any file — image, video, document, log file — plus its metadata and a URL.

> [!note] Key S3 concepts
> 
> - **Object** — A file plus metadata. Can be up to **5 TB** in size.
> - **Bucket** — A container for objects. Think of it like a top-level folder.
> - **Bucket names** — Must be globally unique across all AWS accounts, everywhere. They must also be DNS-compliant.
> - **Durability** — S3 is designed for **11 nines (99.999999999%)** of durability. Data is redundantly stored across multiple facilities and devices within a Region.

## Accessing S3

You can reach S3 data through:

- The **AWS Management Console**
- The **AWS [[CLI]]**
- **REST APIs** over HTTP or HTTPS
- **AWS SDKs** (Python, Java, .NET, etc.)
- A **VPC endpoint** for private access without going through the public internet

## S3 Bucket URL formats

S3 supports two URL styles to access objects:

> [!example] URL styles
> 
> - **Path style**: `https://s3.ap-northeast-1.amazonaws.com/my-bucket/preview.mp4`
> - **Virtual-hosted style**: `https://my-bucket.s3.ap-northeast-1.amazonaws.com/preview.mp4`

## S3 Storage Classes

S3 offers several storage classes, optimized for different access patterns and cost requirements:

|Storage class|Best for|Availability|
|---|---|---|
|**S3 Standard**|Frequently accessed data, websites, apps|4 nines (99.99%)|
|**S3 Intelligent-Tiering**|Unknown or unpredictable access patterns|Automatic tier switching|
|**S3 Standard-IA**|Infrequent access, but fast when needed|3 nines (99.9%)|
|**S3 One Zone-IA**|Infrequent access, lower cost, single AZ|Lower resilience|
|**S3 Glacier**|Archiving, minutes-to-hours retrieval|Very low cost|
|**S3 Glacier Deep Archive**|Long-term compliance archives, once-a-year access|Lowest cost of all|

> [!important] Intelligent-Tiering
> After 30 days of no access, S3 Intelligent-Tiering automatically moves objects to the cheaper infrequent access tier. If you access the object again, it moves back to frequent access — no retrieval fee.

> [!note] S3 Glacier 
> Deep Archive Designed for industries like finance and healthcare that must retain records for 7–10+ years. Objects can be restored within **12 hours** and are stored across at least three AZs.

## S3 Common Use Cases

- **Application data storage** — Any EC2 instance or external server can read/write S3 objects.
- **Static website hosting** — Serve HTML, CSS, and JavaScript files directly from a bucket.
- **Backup and disaster recovery** — S3 supports cross-Region replication for high availability.
- **Media hosting** — Scalable storage for videos, photos, and audio.
- **Software delivery** — Distribute downloadable software packages.

## S3 Lifecycle Policies

You can automate how objects move between storage classes over time:

> [!example] Lifecycle policy example A user uploads a video thumbnail. Your policy automatically:
> 
> 1. Stores it in **S3 Standard** (day 0 — frequently accessed)
> 2. Moves to **S3 Standard-IA** after 30 days (less accessed)
> 3. Moves to **S3 Glacier** after 60 days (rarely accessed)
> 4. **Deletes** it after 1 year

Lifecycle rules can be set per bucket, prefix, or object tag.

## S3 Pricing

|Cost component|Details|
|---|---|
|**Storage**|Per GB stored per month. Rate varies by storage class.|
|**Requests**|GET, PUT, COPY, POST, LIST all have per-request charges.|
|**Data transfer**|Data _into_ S3 is free. Data _out_ of S3 (to other Regions) is charged. Transfer to CloudFront in the same Region is free.|

# Amazon EFS (Elastic File System)

## What is EFS?

[[Amazon EFS]] is a _shared file system_ — meaning multiple EC2 instances can read and write to it simultaneously, like a shared network drive at an office.

> [!note] Key EFS characteristics
> 
> - Uses the **NFS (Network File System)** protocol (NFSv4.0 and 4.1)
> - Automatically grows and shrinks as you add or remove files — no manual provisioning
> - Supports **thousands of EC2 instances** accessing it at the same time
> - Highly durable and highly available
> - **No minimum fee** — you pay only for storage used
> - Scales from gigabytes to petabytes automatically

## EFS vs. EBS

> [!important] The key difference
> 
> - **EBS** can only be mounted by **one EC2 instance** at a time, in the **same AZ**.
> - **EFS** can be mounted by **many EC2 instances** across **multiple AZs** within the same Region.

## EFS Architecture

- You create an EFS file system in your [[Amazon VPC]].
- You create **mount targets** in each Availability Zone that needs access. A mount target has its own IP address.
- EC2 instances in different AZs connect to the mount target in their own AZ.
- Best practice: always access EFS from the mount target in the same AZ as the EC2 instance.

## EFS Resources

EFS has two main resource types:

**File system** — The primary resource. Has properties like ID, creation token, size, and state.

**Mount target** — A connection point in a subnet. Has:

- A mount target ID
- The subnet ID where it was created
- The file system ID it belongs to
- An IP address (or [[DNS]] name) you use in your mount command

**Tags** — Key-value metadata you can attach to file systems to help organize them.

## How to Set Up EFS (5 Steps)

1. Create and launch your EC2 instance (with a key pair for SSH access).
2. Create the Amazon EFS file system.
3. Create mount targets in the appropriate subnets.
4. Connect to your EC2 instance and mount the EFS file system.
5. Clean up resources when done.

## EFS Use Cases

- **Big data and analytics**
- **Media processing workflows**
- **Content management systems**
- **Web serving**
- **Home directories** (shared user storage)

# Amazon S3 Glacier

## What is S3 Glacier?

S3 Glacier is designed for data you need to keep for a long time but almost never access — think legal records, old medical files, or archived video footage. It is extremely cheap but retrieval takes time (minutes to hours).

> [!warning] Glacier is NOT instant access 
> Unlike S3 Standard where you get your data in milliseconds, Glacier retrieval takes from a few minutes to several hours. Plan for this when designing your archiving strategy.

## Glacier Key Terms

> [!note] Three terms to know
> 
> - **Archive** — A single stored object (photo, video, document). Each archive has a unique ID and optional description. This is the base unit of storage.
> - **Vault** — A container for archives. You specify a name and Region when creating one.
> - **Vault access policy** — Controls who can access the vault and what they can do. Each vault can have one access policy and one vault lock policy.

## Retrieval Options

|Option|Speed|Cost|
|---|---|---|
|**Expedited**|1–5 minutes|Highest|
|**Standard**|3–5 hours|Medium|
|**Bulk**|5–12 hours|Lowest|

Think of it like shipping: same-day delivery costs more than standard ground shipping.

## S3 Glacier Use Cases

- **Media asset archiving** — Old news footage or video that might be needed later
- **Healthcare records** — Patient data that must be retained for decades
- **Regulatory compliance** — Financial and legal records (e.g. SEC Rule 17a-4(f))
- **Scientific data** — Large research datasets not actively being analyzed
- **Digital preservation** — Libraries and government agencies preserving records

## Glacier vs. S3: Key Differences

|Feature|Amazon S3|Amazon S3 Glacier|
|---|---|---|
|Access speed|Milliseconds|Minutes to hours|
|Max object size|5 TB|40 TB|
|Storage cost|Higher|Lower|
|Request charges|PUT, GET, COPY, LIST|UPLOAD and retrieval|
|Retrieval cost|Lower|Higher|
|Default encryption|No (must configure)|Yes (always encrypted)|

## How to Use Glacier

- Basic operations (create/delete vaults, manage policies) are available in the **AWS Management Console**.
- Most operations require the **REST API**, **AWS SDK** ([[Java]] or [[.NET]]), or the **[[AWS CLI]]**.
- You can also move data into Glacier automatically using **S3 Lifecycle Policies**.

## Server-Side Encryption

Both S3 and S3 Glacier protect data in transit using HTTPS. For data _at rest_, encryption works differently:

> [!important] Encryption differences
> 
> - **S3 Glacier**: Encrypted **by default** — always on, no configuration needed.
> - **S3**: Encryption must be enabled by the application. Three methods are available:

|Method|How it works|
|---|---|
|**SSE-S3**|Amazon manages the keys. Uses AES-256 encryption. Each object gets a unique key, which is itself encrypted by a rotating master key.|
|**SSE-C**|You provide your own encryption keys. Amazon handles encryption/decryption using the key you supply in each request.|
|**SSE-KMS**|Uses **[[AWS KMS]]** (Key Management Service). You create and manage keys through IAM. Provides auditing of key usage.|

> [!note] What is AES-256? 
> AES-256 (Advanced Encryption Standard with 256-bit keys) is one of the strongest encryption algorithms available. It is used by governments and financial institutions worldwide.

# Security and Access Control

> [!important] Default security in S3
> By default, **none of your S3 data is public**. You must explicitly grant access.

You control access using:

- **[[IAM]] policies** — Attached to users or roles; define what actions they can perform on S3.
- **Bucket policies** — Attached directly to an S3 bucket; can grant access to other AWS accounts.
- **Per-object ACLs** — Fine-grained control at the individual object level.

For Glacier, access is controlled exclusively through **IAM policies** attached to the vault.

# All Storage Services

|Service|Type|Multi-instance?|Access speed|Best for|
|---|---|---|---|---|
|Instance store|Block|No (ephemeral)|Fastest|Temp cache, scratch space|
|[[Amazon EBS]]|Block|No (1 EC2)|Very fast|Databases, boot volumes|
|[[Amazon EFS]]|File|Yes (many EC2)|Fast|Shared workloads|
|[[Amazon S3]]|Object|Any (via URL)|Fast (ms)|Web, backups, media|
|S3 Glacier|Object (archive)|Any (via API)|Slow (min–hrs)|Long-term archive|

# Tags

#aws #cloud #storage #ebs #s3 #efs #glacier #cloud-computing #infrastructure #aws-storage #object-storage #block-storage #cloud-computing 
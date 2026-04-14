# Part 1 — Compute

## [[Amazon EC2]] (Elastic Compute Cloud)

EC2 provides resizable virtual machines in the cloud. The name breaks down as: **Elastic** (easy to scale), **Compute** (CPU and RAM), **Cloud** (hosted in AWS data centers).

### EC2 Instance Lifecycle

Understanding the lifecycle is essential for the test. An instance moves through these states:

|State|Meaning|
|---|---|
|**Pending**|Booting up — not yet accessible|
|**Running**|Fully operational; you are billed here|
|**Rebooting**|Restarting — stays on same host, keeps IP and data|
|**Stopping**|Transition state (EBS-backed instances only)|
|**Stopped**|Shut down — no compute charge, but EBS storage charges still apply|
|**Shutting down**|Transition before permanent deletion|
|**Terminated**|Permanently deleted — cannot be recovered|

> [!warning] Key distinction
> A **stopped** instance can be restarted (and may move to a different physical host). A **terminated** instance is gone forever.

### Security Groups

Security groups are **virtual firewalls** that control network traffic to and from an EC2 instance.

- **Default behaviour**: All outbound traffic is allowed; all inbound traffic is blocked unless explicitly permitted.
- Rules specify a protocol (TCP/UDP), a port number, and a source IP or range.
- Multiple security groups can be attached to one instance; rules are cumulative (most permissive wins).
- Changes apply **immediately** to all associated instances.

> [!example] Assignment 4 — Security Group In Assignment 4, you created a security group named `xpix-app-server` that allowed **SSH (port 22) from anywhere**. This was needed so you could connect to the EC2 instance via SSH from your devcontainer. In Assignment 5, this security group was imported into Terraform as an `aws_security_group` resource.

### User Data Scripts

User data is a script that runs **once, automatically, at first launch** of an EC2 instance. It is commonly used to:

- Install software packages
- Clone a code repository
- Configure and start application services

```bash
#!/bin/bash
yum update -y
yum install wget -y
# Clone and set up the app...
git clone https://github.com/youruser/yourrepo.git xpix
```

> [!example] Assignment 4 — User Data In Assignment 4, your `user_data.sh` script cloned your GitHub repository onto the EC2 instance, installed Python dependencies, and configured a **systemd service** to automatically start your Flask app on boot. In Assignment 5, this same file was referenced in Terraform using `file(path.module, "user_data.sh")`.

### Connecting via SSH and Port Forwarding

**Basic SSH connection:**

```bash
ssh -i path/to/key.pem ec2-user@YOUR_INSTANCE_IP
```

**SSH with port forwarding** (used in Assignment 4 to work around Cognito's localhost-only callback URL restriction):

```bash
ssh -i path/to/key.pem -L 5000:localhost:8000 ec2-user@YOUR_INSTANCE_IP
```

This listens on port 5000 on your local machine and forwards all traffic to port 8000 on the remote EC2 instance. This allowed you to open `http://localhost:5000` in your browser while the app was actually running on AWS.

> [!tip] Why port forwarding? Cognito only allows `localhost` as a callback URL for insecure (HTTP) apps. Without port forwarding, you would have to use the instance's public IP, which Cognito would reject.

> [!warning] Key file permissions AWS requires private key files to have narrow permissions. If SSH warns you, fix it with: `chmod 400 path/to/key.pem`

### Systemd Services

Linux uses **systemd** to manage long-running background processes (called services or daemons). In Assignment 4, your Flask app was registered as a systemd service named `xpix`.

Common `systemctl` commands:

```bash
systemctl status xpix      # Check if the service is running
sudo systemctl start xpix  # Start the service
sudo systemctl stop xpix   # Stop the service
sudo systemctl restart xpix # Restart the service
```

> [!note] Why use a systemd service? It ensures your app starts automatically when the EC2 instance boots and restarts automatically if it crashes — critical for production reliability.

### EC2 Pricing Models

|Model|Commitment|Discount|Best For|
|---|---|---|---|
|**On-Demand**|None|None (baseline)|Short-term, testing, unpredictable workloads|
|**Reserved**|1 or 3 years|Up to 75%|Steady, always-on workloads (e.g., databases)|
|**Scheduled Reserved**|1 year (specific windows)|Moderate|Predictable periodic batch jobs|
|**Spot**|None|Up to 90%|Fault-tolerant, interruptible workloads|
|**Dedicated Host**|Per-host billing|Varies|Compliance, bring-your-own-license|
|**Dedicated Instance**|Per-instance|Varies|Hardware isolation required|

> [!important] Stopped instances still cost money When an EC2 instance is **stopped**, you are not billed for compute, but you **are still billed for EBS storage** attached to it.

---

## [[AWS Lambda]]

Lambda is a **serverless** compute service. You upload code (a "function"), and Lambda runs it in response to events — no servers to manage, no patching, no capacity planning.

Key characteristics:

- You pay only for **actual execution time** (measured in milliseconds).
- Maximum timeout: **15 minutes** per invocation.
- Memory: 128 MB to 10,240 MB (more memory = more CPU).
- Triggered by events: S3 uploads, API Gateway requests, scheduled CloudWatch events, DynamoDB streams, etc.

> [!example] Common Lambda use case A user uploads a photo to S3 → S3 triggers a Lambda function → Lambda creates a thumbnail → Lambda saves the thumbnail to another S3 bucket. No server running 24/7 is needed.

> [!warning] Lambda hard limits The 15-minute timeout is a **hard limit** — it cannot be increased. If your workload takes longer, Lambda is not the right tool.

---

## Container-Based Services (ECS, EKS, Fargate)

Containers package your application code, runtime, libraries, and config into an isolated, portable unit. They are smaller and faster to start than full virtual machines because they share the host OS kernel.

|Service|What it is|
|---|---|
|**[[Amazon ECS]]**|AWS-managed container orchestration using Docker|
|**[[Amazon EKS]]**|AWS-managed Kubernetes — for teams already using Kubernetes|
|**[[AWS Fargate]]**|Serverless containers — run containers without managing the underlying EC2 instances|
|**[[Amazon ECR]]**|Registry for storing Docker container images|

> [!note] The key idea for the test Containers let you run **multiple isolated applications on a single EC2 instance**, dramatically improving resource utilization. ECS/EKS manage _where_ and _how_ containers run. Fargate removes the need to manage the host entirely.

---

# Part 2 — Infrastructure as Code ([[Terraform]])

## Why IaC Exists

Manual cloud setup (clicking through the AWS Console or writing CLI scripts) has serious problems: environments drift apart, there is no automatic state tracking, dependencies must be managed by hand, and changes are hard to audit. [[Infrastructure as Code]] solves all of these.

> [!note] IaC is declarative You describe **what** you want your infrastructure to look like. Terraform figures out **how** to make it happen, including the correct order to create resources.

## The Plan-Apply Cycle

This is the core workflow you used in Assignment 5:

1. **`terraform init`** — Download the required providers. Run once per project, or after adding new providers.
2. **`terraform plan`** — Compare your `.tf` files against the real infrastructure. Shows what will be created, updated, or deleted. **Nothing is changed yet.**
3. **`terraform apply`** — Shows the plan again, asks for confirmation, then executes it and updates the state file.

> [!important] Assignment 5 rule: import-only plans In Assignment 5, you were told: **only apply if your plan shows ONLY import actions**. If the plan showed create/update/delete actions, you had to fix your configuration to match the real AWS resources first. This is the essence of importing existing infrastructure.

## Terraform Configuration Syntax (HCL)

All Terraform files end in `.tf` and use **HCL (HashiCorp Configuration Language)**. A block has a type, optional labels, and attributes inside curly braces:

```hcl
blocktype "label" "label" {
  attribute_name = value_expression
}
```

### Key Block Types

|Block|Purpose|
|---|---|
|`terraform`|Sets Terraform version and required providers|
|`provider`|Configures a cloud platform (e.g., AWS)|
|`resource`|Defines a cloud resource to create or manage|
|`variable`|Stores a reusable value|
|`data`|Fetches a live value from the provider|
|`output`|Displays a value after apply runs|
|`import`|Brings existing infrastructure under Terraform management|

### The `terraform` Block

```hcl
terraform {
  required_version = ">= 1.14.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 6.0"
    }
  }
}
```

### The `provider` Block

```hcl
provider "aws" {
  region = var.aws_region
}
```

Terraform uses the same AWS credentials as the CLI (your `.aws/credentials` file).

### The `resource` Block

The most common block. Each one configures a specific cloud resource.

```hcl
resource "aws_vpc" "myvpc" {
  cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "az1_public" {
  vpc_id     = aws_vpc.myvpc.id   # <-- reference, not hard-coded ID
  cidr_block = "10.0.0.0/24"
}
```

### The `variable` Block

```hcl
variable "aws_region" {
  type    = string
  default = "us-east-1"
}

provider "aws" {
  region = var.aws_region   # accessed with var. prefix
}
```

### The `data` Block

Used to fetch dynamic values you cannot hard-code (e.g., the latest Amazon Linux AMI ID):

```hcl
data "aws_ami" "example" {
  most_recent = true
  owners      = ["amazon"]
}

resource "aws_instance" "web" {
  ami = data.aws_ami.example.id
}
```

### The `output` Block

Displays useful values after `terraform apply` completes (e.g., the public IP of your EC2 instance):

```hcl
output "web_server_public_ip" {
  value = aws_instance.web.public_ip
}
```

> [!example] Assignment 5 You added an output block to display the public IP of your XPix EC2 instance. After running `terraform apply`, the IP appeared in the terminal output.

## References and Dependency Management

Instead of hard-coding resource IDs, you use **references**. The format is:

```
resource_type.resource_label.attribute
```

For example: `aws_vpc.myvpc.id`

References do two things:

1. Avoid hard-coding values that might change.
2. Automatically tell Terraform the dependency order (if subnet references VPC, Terraform creates the VPC first).

> [!important] Assignment 5 instruction "Use references when appropriate. You should NOT refer directly to ANY resource IDs in your resource blocks." This is why references matter — they keep your config portable and correct.

## Importing Existing Infrastructure vs. Provisioning New Resources

||Importing|Provisioning New|
|---|---|---|
|**Scenario**|Infrastructure already exists in AWS|Starting from scratch|
|**Steps**|Add `import` block + matching `resource` block|Just add a `resource` block|
|**Plan output**|Shows only "import" actions|Shows "create" actions|
|**Goal**|Bring existing infra under Terraform's management|Create brand new infra|

### The `import` Block

```hcl
import {
  to = aws_vpc.main
  id = "vpc-1a747d829fd983"   # the actual AWS resource ID
}

resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
}
```

> [!warning] Imports should ONLY import Before applying, confirm your plan shows zero creates, updates, or deletes. Do not import and modify at the same time.

### Auto-generating Config with `--generate-config-out`

When a resource has many attributes (like Cognito), you can let Terraform generate the resource block:

```bash
terraform plan --generate-config-out=cognito.tf
```

Terraform writes a `.tf` file with all the attributes filled in. You then clean it up — remove defaults, replace hard-coded IDs with references, and move blocks to appropriate files.

> [!example] Assignment 5 — Cognito import You used `--generate-config-out=cognito.tf` to import your Cognito User Pool and App Client. Terraform generated the resource blocks automatically, and you then replaced the hard-coded User Pool ID in `aws_cognito_user_pool_client` with a reference to `aws_cognito_user_pool.xpix.id`.

## Terraform State

Terraform tracks what actually exists in AWS in a file called `terraform.tfstate`. This is what allows it to compute an accurate plan — it compares your desired configuration against the recorded real state.

> [!warning] Never edit `terraform.tfstate` manually and never commit it to Git (it may contain sensitive values).

## What Terraform Does NOT Manage

Terraform handles **infrastructure design**. It is not for: managing application data, starting/stopping VMs, managing user accounts, runtime configuration, or storing secrets.

> [!example] Assignment 5 — Why secrets were excluded You did NOT import the `/app/flask/secret_key` or `/app/cognito/client_secret` parameters into Terraform. Terraform state files can be read by anyone with access — storing secrets there creates a security risk. You only managed the non-secret parameters (User Pool ID, Client ID, bucket name, table name) via Terraform.

---

# Part 3 — Storage

## The Five AWS Storage Types

|Service|Type|Attached to|Access Speed|Best For|
|---|---|---|---|---|
|**Instance Store**|Block|1 EC2 (ephemeral)|Fastest|Temp cache; data lost on stop|
|**[[Amazon EBS]]**|Block|1 EC2 at a time|Very fast|Databases, boot volumes|
|**[[Amazon EFS]]**|File|Many EC2 (shared)|Fast|Shared workloads, home dirs|
|**[[Amazon S3]]**|Object|Any (via URL/API)|Fast (ms)|Web, backups, media, apps|
|**S3 Glacier**|Object (archive)|Any (via API)|Minutes–hours|Long-term archives|

## Block Storage vs. Object Storage

> [!important] This is one of the most commonly tested concepts
> 
> - **Block storage** (EBS): When you change part of a file, only the changed block is updated on disk. Very fast and efficient for frequent writes — ideal for databases.
> - **Object storage** (S3): When you change anything in an object, the entire object must be re-uploaded. Great for files that are stored and retrieved whole (images, videos, backups), but not efficient for frequent partial edits.

## [[Amazon EBS]] (Elastic Block Store)

EBS works like an external hard drive attached to your EC2 instance.

Key facts:

- **Persistent** — data survives instance stop/start.
- **Single instance** — only one EC2 instance can mount an EBS volume at a time.
- **Same AZ only** — a volume in `us-east-1a` cannot be attached to an instance in `us-east-1b`.
- **Automatically replicated** within its AZ (protects against hardware failure).
- **Low latency** — suitable for running databases.

### EBS Volume Types

|Type|Use Case|
|---|---|
|General Purpose SSD (gp2/gp3)|Most workloads, boot volumes|
|Provisioned IOPS SSD (io1/io2)|High-performance databases|
|Throughput Optimized HDD (st1)|Big data, log processing|
|Cold HDD (sc1)|Infrequently accessed data|

> [!tip] Only SSDs can be boot volumes. HDDs are for additional data volumes only.

### EBS Snapshots

- Point-in-time backup stored in S3.
- First snapshot = full copy (baseline). Subsequent snapshots are **incremental** (only what changed).
- Can be copied to other AWS Regions for disaster recovery.
- You restore a snapshot by creating a new EBS volume from it.
- When you create an AMI from an EC2 instance, it captures the EBS volume snapshot.

## [[Amazon S3]] (Simple Storage Service)

S3 stores data as **objects** inside **buckets**. An object is a file plus metadata plus a URL.

|Concept|Detail|
|---|---|
|Max object size|5 TB|
|Bucket names|Globally unique across all AWS accounts; DNS-compliant|
|Durability|11 nines (99.999999999%) — data replicated across multiple facilities|

### S3 Storage Classes (Tiered Storage)

|Class|Best For|Notes|
|---|---|---|
|**S3 Standard**|Frequently accessed data|99.99% availability|
|**S3 Intelligent-Tiering**|Unknown access patterns|Auto-moves to cheaper tier after 30 days of inactivity; no retrieval fee|
|**S3 Standard-IA**|Infrequent access, fast retrieval|99.9% availability|
|**S3 One Zone-IA**|Infrequent access, lower cost|Single AZ — less resilient|
|**S3 Glacier**|Archiving|Minutes-to-hours retrieval|
|**S3 Glacier Deep Archive**|Long-term compliance (7–10+ years)|Retrieval within 12 hours; lowest cost|

### S3 Lifecycle Policies

You can automate transitions between storage classes:

> [!example] Lifecycle policy Day 0: Upload to S3 Standard → Day 30: Move to Standard-IA → Day 60: Move to Glacier → Day 365: Delete automatically.

### S3 Pricing

|Component|Detail|
|---|---|
|Storage|Per GB/month; rate varies by storage class|
|Requests|GET, PUT, COPY, LIST all have per-request charges|
|Data transfer in|Free|
|Data transfer out|Charged (to other Regions); free to CloudFront in same Region|

### S3 Access and Security

- By default, **all S3 data is private**. You must explicitly grant access.
- Access controlled via IAM policies, bucket policies, and per-object ACLs.
- Two URL styles: path style (`s3.amazonaws.com/bucket/object`) and virtual-hosted style (`bucket.s3.amazonaws.com/object`).

> [!example] Assignment 6 — Pre-signed URLs In Assignment 6, your XPix app allowed users to upload and view photos in a private S3 bucket without making the bucket public. It did this using **pre-signed URLs** — temporary URLs generated server-side that grant time-limited access to a specific object. No bucket policy changes were needed.

### S3 Encryption

|Method|How|
|---|---|
|**SSE-S3**|Amazon manages keys; AES-256; transparent to user|
|**SSE-C**|You supply your own key with each request|
|**SSE-KMS**|Uses AWS Key Management Service; auditable key usage|

## [[Amazon EFS]] (Elastic File System)

EFS is a **shared file system** — multiple EC2 instances can read and write to it simultaneously, like a network drive.

- Uses the **NFS protocol** (NFSv4.0 and 4.1).
- Automatically grows and shrinks — no manual capacity provisioning.
- Supports thousands of EC2 instances across multiple AZs in the same Region.
- You create **mount targets** in each AZ that needs access; each mount target has its own IP.
- No minimum fee — you pay only for storage used.

> [!important] EBS vs. EFS
> 
> - EBS: one EC2 instance, one AZ.
> - EFS: many EC2 instances, many AZs (within same Region).

## S3 Glacier

For data you must retain for years but almost never access.

|Retrieval Option|Speed|Cost|
|---|---|---|
|Expedited|1–5 minutes|Highest|
|Standard|3–5 hours|Medium|
|Bulk|5–12 hours|Lowest|

Key terms: an **archive** is a single stored object; a **vault** is a container for archives (like a bucket in S3). Glacier **always encrypts** data at rest by default.

> [!warning] Glacier is NOT instant access. Plan retrieval time into your architecture.

---

# Part 4 — Databases

## Managed vs. Unmanaged

||Unmanaged (e.g., DB on EC2)|Managed (e.g., RDS)|
|---|---|---|
|You manage|OS, DB software, backups, scaling, patching|Application and data only|
|AWS manages|Hardware only|OS, DB software, backups, scaling, availability|

## [[Amazon RDS]] (Relational Database Service)

RDS is a managed **relational (SQL)** database service. It supports six engines: MySQL, PostgreSQL, MariaDB, Oracle, Microsoft SQL Server, and IBM DB2.

### Multi-AZ Deployment

RDS automatically creates a **standby copy** in a different Availability Zone. Transactions are **synchronously replicated** — both copies are always in sync. If the primary fails, AWS automatically promotes the standby (failover is transparent because your app connects via a **DNS endpoint name**, not an IP address).

> [!important] Multi-AZ vs. Read Replicas — a very commonly tested distinction
> 
> - **Multi-AZ** = high availability and automatic failover. Protects against failure.
> - **Read Replicas** = performance scaling. Offloads read queries from the primary. Updates are replicated **asynchronously** (slight delay). Can be in a different Region. They do NOT automatically become primary if the primary fails.

### When to Use and Avoid RDS

Use RDS for: complex SQL queries, transactions, medium-high query rates (up to ~30,000 IOPS), high durability.

Avoid RDS for: extremely high write rates, massive horizontal sharding needs, simple key-value lookups (use DynamoDB instead).

### RDS Pricing Factors

- Clock hours (billed from start to stop)
- Instance class size
- On-Demand vs. Reserved (1 or 3 year) — Reserved gives significant discount
- Multi-AZ costs more (two copies running)
- Storage (free backup storage up to 100% of provisioned size while instance is active)
- Data transfer (inbound free; outbound tiered)

## [[Amazon DynamoDB]]

DynamoDB is a fully managed **NoSQL** database designed for single-digit-millisecond latency at massive scale.

- **Serverless** — no infrastructure to manage.
- **SSD storage** for all data.
- **Global tables** — replicate across multiple Regions automatically.
- **Encryption at rest** by default.
- **Time-to-Live (TTL)** — automatically delete items after a specified time.

### Core Concepts

|Concept|SQL Equivalent|Notes|
|---|---|---|
|Table|Table|Schema-flexible — items in same table can have different attributes|
|Item|Row|Uniquely identifiable|
|Attribute|Column|Items can have different sets of attributes|

### Primary Keys

- **Partition key only** (simple primary key): a single attribute that uniquely identifies the item. DynamoDB uses it to determine physical storage location.
- **Partition key + Sort key** (composite): groups related items (same partition key) and sorts them (by sort key). Enables powerful grouped queries.

> [!example] Assignment 6 — DynamoDB table design Your `xpix-photos` table used `photo_id` as the hash (partition) key. Two Global Secondary Indexes (GSIs) were also created:
> 
> - `user-photos-index`: hash key = `user_id`, range key = `uploaded_at` — allows querying all photos for a specific user, sorted by time.
> - `feed-index`: hash key = `feed_key`, range key = `uploaded_at` — allows querying public feed photos sorted by time. GSIs are needed because DynamoDB only efficiently queries by primary key — for other access patterns, you need a secondary index.

### Query vs. Scan

- **Query** — uses the primary key (or GSI key). Fast and efficient — DynamoDB knows exactly where to look.
- **Scan** — reads every item in the table. Flexible but slow and expensive for large tables.

> [!warning] Always prefer Query over Scan. Design your primary keys and GSIs to match your app's most common access patterns.

## [[Amazon Redshift]]

Redshift is a fully managed **data warehouse** — optimized for **analytical** queries over massive historical datasets, not for day-to-day transactions.

- Uses **massively parallel processing (MPP)**: a leader node distributes query work to multiple compute nodes running in parallel.
- **Columnar storage** — stores data column-by-column instead of row-by-row; much faster for analytical queries that only need a few columns from billions of rows.
- **Redshift Spectrum** — can query data directly in S3 without loading it into Redshift first.
- Compatible with standard SQL and popular BI tools (Tableau, Power BI) via JDBC/ODBC.

> [!note] RDS vs. Redshift RDS handles **transactions** (live app data: "add this order"). Redshift handles **analytics** ("what were total sales by region for the past 3 years?").

## [[Amazon Aurora]]

Aurora is a **relational database** (MySQL and PostgreSQL compatible) built from the ground up for the cloud. It offers the performance of commercial databases at open-source pricing.

- Multiple copies of data stored across multiple AZs automatically (not an optional add-on).
- Continuously backs up to S3.
- Supports up to **15 read replicas** (vs 5 for standard RDS).
- **Crash recovery in under 60 seconds** — Aurora distributes redo log recovery work across all read operations continuously, so it does not need to replay a full log on restart.
- The **buffer cache** (memory that speeds up reads) survives database restarts — no warm-up period.
- Fully managed: automated provisioning, patching, backups, failure detection.

### Quick Comparison: Choose the Right Database

|Service|Type|Best For|
|---|---|---|
|RDS|Relational (SQL)|Standard web apps, ecommerce, complex queries|
|DynamoDB|NoSQL|High-speed, massive scale, flexible schema|
|Redshift|Data warehouse|Analytics, BI, historical data|
|Aurora|Relational (SQL)|High-availability MySQL/PostgreSQL workloads|

---

# Part 5 — CI/CD and GitHub Actions

## What is CI/CD?

CI/CD is a set of practices that automate the process of building, testing, and releasing software. Think of it as an automated assembly line for code.

|Term|Full Name|What It Does|
|---|---|---|
|**CI**|Continuous Integration|Automatically builds and tests code on every push|
|**CD**|Continuous Delivery|Prepares code for release; a human approves the final push to production|
|**CD**|Continuous Deployment|Deploys to production automatically if all tests pass — no human needed|

> [!important] Delivery vs. Deployment Continuous **Delivery** requires human approval before going live. Continuous **Deployment** is fully automatic. Most teams start with Delivery.

## The CI/CD Pipeline Flow

```
Plan → Code → Build → Test → Create Artifact → Staging → Production
```

1. **Plan** — decide what to build
2. **Code** — developer pushes to a shared Git repository
3. **Build** — CI tool compiles/packages the code
4. **Test** — automated tests run (unit, integration, etc.)
5. **Artifact** — a deployable package is created (Docker image, `.jar` file, etc.)
6. **Staging** — artifact deployed to a test environment mirroring production
7. **Production** — if everything passes, code goes live

## [[GitHub Actions]]

GitHub Actions is GitHub's built-in CI/CD platform. Workflows are defined in YAML files stored at `.github/workflows/` in your repository.

### The Five Core Components

```
Event
  └── triggers Workflow
        └── contains Jobs
              └── runs on a Runner
                    └── executes Steps
                          └── each Step is a Script or an Action
```

**1. Workflow** — a configurable automated process defined in a `.yml` file. A repository can have multiple workflows.

**2. Event** — an activity that triggers a workflow. Common events: `push`, `pull_request`, `schedule` (cron), `workflow_dispatch` (manual).

**3. Job** — a collection of steps that run on the same runner. By default, jobs run **in parallel**. You can add `needs:` to create dependencies.

**4. Step** — a single task in a job. Either a shell script (`run:`) or a pre-built action (`uses:`). Steps within a job run **sequentially** and share the same file system.

**5. Action** — a reusable, pre-built automation unit. Examples: `actions/checkout@v3` (checks out your code), `actions/setup-node@v3` (sets up Node.js). Found in the GitHub Marketplace.

**Runner** — the virtual machine that executes the job. GitHub provides `ubuntu-latest`, `windows-latest`, `macos-latest`. You can also use self-hosted runners.

> [!important] Actions vs. Scripts
> 
> - A **script** (`run: npm test`) runs a shell command you write directly.
> - An **action** (`uses: actions/checkout@v3`) runs pre-packaged, reusable automation written by someone else.

## [[YAML]] — The Language of CI/CD Pipelines

All GitHub Actions workflows are written in YAML. Key rules:

> [!warning] Common YAML mistakes
> 
> - YAML uses **spaces** for indentation, **never tabs**. Tabs will cause errors.
> - YAML is **case-sensitive** (`True` and `true` are different).
> - Strings containing `:` or `#` must be quoted.
> - Validate your YAML at [yamllint.com](http://www.yamllint.com/).

### YAML Quick Reference

```yaml
# Scalar types
count: 42              # integer
price: 9.99            # float
name: Alice            # string
is_active: true        # boolean
birthday: 2000-01-15   # date
middle_name: ~         # null

# List (sequence)
languages:
  - Python
  - JavaScript

# Dictionary (mapping)
person:
  name: Alice
  age: 30

# List of dictionaries (very common in GitHub Actions steps)
steps:
  - name: Checkout code
    uses: actions/checkout@v3
  - name: Run tests
    run: npm test

# Multiline string — literal (| keeps newlines)
message: |
  Hello
  World

# Anchors and aliases (reuse values)
defaults: &common
  timeout: 30

job_a:
  <<: *common
  name: Job A
```

### Complete Workflow Example

```yaml
name: Build and Test

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  test:
    runs-on: ubuntu-latest
    strategy:
      matrix:
        node-version: [18, 20]  # runs job twice, in parallel
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Set up Node
        uses: actions/setup-node@v3
        with:
          node-version: ${{ matrix.node-version }}
      - name: Install deps
        run: npm install
      - name: Run tests
        run: npm test

  deploy:
    runs-on: ubuntu-latest
    needs: test                              # only runs after test job passes
    if: github.ref == 'refs/heads/main'     # only on main branch, not PRs
    steps:
      - name: Deploy
        run: echo "Deploying..."
```

> [!example] What this workflow does
> 
> 1. Triggers on push or PR to `main`.
> 2. Runs the `test` job twice in parallel (Node 18 and Node 20) using a matrix.
> 3. Only if all tests pass, runs the `deploy` job — and only on actual pushes to `main`, not on PRs.

---

# Part 6 — Assignment-Specific Lab Summary

## Assignment 4 — Flask + Cognito on EC2

**What you did:**

- Stored secrets and config in **AWS Systems Manager Parameter Store** (Flask secret key, Cognito client secret as `SecureString`; User Pool ID and Client ID as `String`).
- Ran the Flask app locally in your devcontainer; configured Cognito **allowed callback URLs** (`http://localhost:5000/` and `http://localhost:5000/authorize`) and a sign-out URL.
- Created a **security group** allowing SSH inbound.
- Wrote a **user data script** to clone your repo and configure a **systemd service** on EC2.
- Created an **IAM policy** scoped to specific Cognito actions on your specific User Pool.
- Used **SSH port forwarding** (`-L 5000:localhost:8000`) to access the app running on EC2 through your local browser.

**Key concepts tested:** security groups, user data, SSH, port forwarding, systemd, IAM least privilege, Parameter Store.

## Assignment 5 — Terraform IaC

**What you did:**

- Created `variables.tf`, `main.tf`, `vpc.tf`, `compute.tf`, and `cognito.tf`.
- Wrote `resource` blocks for: VPC, 4 subnets, internet gateway, 3 route tables, route table associations, security group, ingress rule, EC2 instance, Cognito user pool and client, SSM parameters.
- Wrote `import` blocks with real AWS resource IDs to bring existing infrastructure under Terraform control.
- Used `--generate-config-out=cognito.tf` to auto-generate Cognito resource blocks.
- Replaced hard-coded IDs with **references** (e.g., `aws_vpc.myvpc.id`).
- Only applied plans that showed **import-only** actions.
- Explained why secrets were NOT managed by Terraform (state file security risk).

**Key concepts tested:** HCL blocks, references, variables, plan-apply cycle, import vs. provision, state file, why not to store secrets in Terraform.

## Assignment 6 — S3 + DynamoDB

**What you did:**

- Added `storage.tf` to your infra folder with: an S3 bucket, a DynamoDB table (`xpix-photos`), and two SSM parameters pointing to their names.
- DynamoDB table design: hash key `photo_id`, billing mode `PAY_PER_REQUEST`, stream enabled, two GSIs for efficient querying by user and by feed.
- Ran `terraform plan` → confirmed creates → ran `terraform apply`.
- Updated app code (git pull + systemctl restart) on EC2.
- Used **pre-signed URLs** to allow private S3 uploads and downloads without making the bucket public.

**Key concepts tested:** S3 provisioning via Terraform, DynamoDB table design, GSIs, PAY_PER_REQUEST billing, pre-signed URLs, how Terraform provisions (not imports) new resources.

---

# Tags

#aws #cloud-computing #ec2 #lambda #terraform #iac #s3 #ebs #efs #glacier #rds #dynamodb #redshift #aurora #cicd #githubactions #yaml #devops #storage #databases #compute #infrastructure #cloud
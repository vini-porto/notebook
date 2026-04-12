# Amazon S3 — Simple Storage Service

> [!note] What is Amazon S3?
> **Amazon S3 (Simple Storage Service)** is a fully managed, object-based [[Cloud Storage]] service provided by [[AWS]]. It allows you to store and retrieve any amount of data from anywhere on the internet. S3 is one of the oldest and most widely used AWS services, designed for **high durability, availability, and scalability**.

## Core Concepts

### Objects and Buckets

S3 is an **object storage** service — this means data is stored as *objects*, not as files in a traditional file system hierarchy.

- **Object**: The fundamental unit of storage in S3. An object consists of:
  - **Data**: The actual content (a file, image, video, JSON, etc.)
  - **Key**: A unique identifier (the "name" of the object within a bucket)
  - **Metadata**: Key-value pairs describing the object (content type, creation date, custom tags, etc.)
  - **Version ID** (if versioning is enabled)

- **Bucket**: A container for objects. Think of it like a top-level folder in the cloud. All objects must live inside a bucket.

> [!important]
> Bucket names must be **globally unique** across all AWS accounts and all regions. Once a bucket is created with a name, no other account in the world can use that same name.

---

### Keys and the "Folder Illusion"

S3 is a **flat storage system** — there are no real folders. However, you can use slashes (`/`) in object keys to simulate a folder-like structure.

> [!example]
> If you store an object with the key `photos/2024/vacation.jpg`, the AWS Console will display it as if it's inside a `photos/2024/` folder — but underneath, it is just a single object with a long key name.

---

## Storage Classes

S3 offers multiple **storage classes** designed for different access patterns and cost requirements.

| Storage Class | Use Case | Retrieval | Cost |
|---|---|---|---|
| **S3 Standard** | Frequently accessed data | Immediate | Higher |
| **S3 Intelligent-Tiering** | Unknown or changing access patterns | Immediate | Automatic optimization |
| **S3 Standard-IA** | Infrequent Access, but needs fast retrieval | Immediate | Lower storage, retrieval fee |
| **S3 One Zone-IA** | Infrequent access, non-critical data | Immediate | Cheapest IA option |
| **S3 Glacier Instant Retrieval** | Archive with millisecond access | Milliseconds | Very low |
| **S3 Glacier Flexible Retrieval** | Long-term archive | Minutes to hours | Very low |
| **S3 Glacier Deep Archive** | Regulatory/long-term retention | Up to 12 hours | Lowest |

> [!tip]
> Use **S3 Intelligent-Tiering** when you are unsure about how often data will be accessed. AWS automatically moves objects between access tiers to optimize cost — no manual intervention needed.

---

## Durability and Availability

> [!important]
> S3 Standard provides **99.999999999% durability** (often written as *11 nines*). This means that if you store 10 million objects, you can expect to lose at most one object every 10,000 years. Data is automatically replicated across **multiple [[Availability Zone]]s** within a [[Region]].

- **Availability**: S3 Standard guarantees **99.99% availability** per year.
- Data is stored redundantly across at least **3 AZs** (except One Zone-IA, which uses only one).

---

## Key Features

### Versioning

[[S3]] supports **bucket versioning**, which keeps multiple versions of an object over time. This protects against accidental deletions and overwrites.

- When versioning is enabled, every `PUT` creates a new version with a unique **Version ID**.
- A `DELETE` does not permanently remove the object — it adds a *delete marker*.
- You can restore any previous version at any time.

```bash
# Enable versioning on a bucket
aws s3api put-bucket-versioning \
  --bucket my-bucket \
  --versioning-configuration Status=Enabled
```

---

### Lifecycle Policies

You can define **lifecycle rules** to automatically transition objects between storage classes or expire (delete) them after a certain period.

> [!example]
> A common pattern for log storage:
> - Days 0–30: **S3 Standard** (frequent access)
> - Days 31–90: **S3 Standard-IA** (less frequent)
> - Days 91–365: **S3 Glacier Flexible Retrieval** (archive)
> - After 365 days: **Permanently deleted**

This saves significant cost over time without manual intervention.

---

### Static Website Hosting

S3 can serve a static website (HTML, CSS, JS, images) directly — no server required.

```bash
# Enable static website hosting
aws s3 website s3://my-bucket/ \
  --index-document index.html \
  --error-document error.html
```

> [!note]
> For production static sites, it is common to pair S3 with [[CloudFront]] (AWS's [[CDN]]) to add HTTPS, caching, and global edge delivery.

---

### Presigned URLs

A **presigned URL** allows temporary, time-limited access to a private S3 object without requiring AWS credentials. Useful for sharing a private file with a user for a limited time.

```bash
# Generate a presigned URL valid for 1 hour
aws s3 presign s3://my-bucket/my-file.pdf --expires-in 3600
```

---

### Event Notifications

S3 can trigger events when objects are created, deleted, or restored. These events can invoke:
- [[Lambda]] functions
- [[SNS]] topics
- [[SQS]] queues

> [!example]
> A common serverless pattern: a user uploads an image to S3 → S3 triggers a [[Lambda]] function → Lambda resizes the image and stores the thumbnail back in S3.

---

## Access Control and Security

### Bucket Policies

**Bucket policies** are JSON-based resource policies attached directly to a bucket. They define who can access the bucket and what actions they can perform.

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::my-public-bucket/*"
    }
  ]
}
```

> [!warning]
> Making a bucket public via bucket policy exposes **all objects** in it to the internet. Always verify this is intentional. AWS has a **Block Public Access** setting that can override all public access settings — it is enabled by default and should remain enabled unless you explicitly need public access.

---

### IAM Policies

[[IAM]] policies attached to users, groups, or roles can grant or restrict access to S3 actions and specific buckets/objects. This is the preferred method for controlling access from within your AWS account.

---

### Server-Side Encryption (SSE)

S3 supports encrypting data *at rest* automatically:

| Option | Key Management |
|---|---|
| **SSE-S3** | AWS manages the keys (simplest) |
| **SSE-KMS** | Keys managed via [[AWS KMS]] (more control, audit trail) |
| **SSE-C** | You provide and manage your own keys |

> [!tip]
> **SSE-KMS** is recommended for sensitive data because it provides key rotation, access logging, and integration with [[CloudTrail]] for full auditability.

---

### Transfer Encryption

All data in transit is protected using **TLS/HTTPS**. You can enforce HTTPS-only access through a bucket policy condition:

```json
{
  "Effect": "Deny",
  "Principal": "*",
  "Action": "s3:*",
  "Resource": "arn:aws:s3:::my-bucket/*",
  "Condition": {
    "Bool": { "aws:SecureTransport": "false" }
  }
}
```

---

## S3 and Other AWS Services

S3 integrates deeply with the broader AWS ecosystem:

- **[[EC2]]**: Store application data, backups, and AMI snapshots.
- **[[CloudFront]]**: Use S3 as the origin for a global [[CDN]].
- **[[Lambda]]**: Trigger serverless functions on object events.
- **[[Athena]]**: Query data stored in S3 directly using SQL (no ETL needed).
- **[[Glue]]**: Catalog and transform data in S3 for analytics pipelines.
- **[[ECS]] / [[Kubernetes]]**: Store container images via [[ECR]], which is backed by S3 internally.
- **[[Terraform]]**: Commonly used to store [[Terraform]] remote state files.

---

## Common CLI Commands

```bash
# List all buckets
aws s3 ls

# List objects in a bucket
aws s3 ls s3://my-bucket/

# Upload a file
aws s3 cp myfile.txt s3://my-bucket/myfile.txt

# Download a file
aws s3 cp s3://my-bucket/myfile.txt ./myfile.txt

# Sync a local directory to S3
aws s3 sync ./local-folder s3://my-bucket/remote-folder/

# Delete an object
aws s3 rm s3://my-bucket/myfile.txt

# Create a bucket
aws s3 mb s3://my-new-bucket
```

---

## Pricing Model

S3 pricing is based on several dimensions:

- **Storage used**: Charged per GB per month (varies by storage class).
- **Requests**: Charged per number of GET, PUT, LIST, DELETE operations.
- **Data transfer OUT**: Charged for data leaving S3 to the internet or other regions (data transfer *in* is free).
- **Replication**: Cross-region replication incurs transfer and storage costs in the destination region.

> [!warning]
> A common mistake for beginners is to overlook **data transfer costs**. If you have a high-traffic application serving large files directly from S3, the egress costs can add up quickly. Use [[CloudFront]] in front of S3 to cache content at edge locations and reduce data transfer charges.

---

## S3 vs Other Storage Types

> [!note]
> It is important to understand that S3 is **object storage**, which is different from other storage types in AWS:
> - **[[EBS]] (Elastic Block Store)**: Block storage attached to a single [[EC2]] instance — like a hard drive. Not accessible from multiple instances simultaneously.
> - **[[EFS]] (Elastic File System)**: Network file storage (NFS) that can be mounted by multiple EC2 instances at the same time.
> - **S3**: Object storage accessible over HTTP from anywhere. No need to attach to a compute resource.

Use S3 when you need to store files, backups, media, logs, or static assets that are accessed via URLs or AWS SDKs.

---

## External Resources

- [AWS S3 Official Documentation](https://docs.aws.amazon.com/s3/)
- [S3 Storage Classes Overview](https://aws.amazon.com/s3/storage-classes/)
- [S3 Security Best Practices — AWS](https://docs.aws.amazon.com/AmazonS3/latest/userguide/security-best-practices.html)
- [GeeksForGeeks — AWS S3](https://www.geeksforgeeks.org/amazon-s3/)
- [AWS S3 Pricing](https://aws.amazon.com/s3/pricing/)

---

## Tags

#cloud #aws #storage #s3 #object-storage #cloud-computing #security #serverless #iac #devops
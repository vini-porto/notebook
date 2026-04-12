
> [!note] Definition 
> **Amazon EBS (Elastic Block Store)** is a high-performance, persistent **block storage** service designed for use with [[Amazon EC2]] (Elastic Compute Cloud) instances. Think of it as a virtual hard drive that you attach to your cloud server — it stores data independently of the instance's lifecycle, meaning your data survives even if the EC2 instance is stopped or terminated.

# Why Block Storage?

In cloud computing, storage comes in different forms. EBS is specifically **block storage**, which means it divides data into fixed-size blocks and stores them individually — similar to how a traditional SSD or HDD ([[Storage]]) works on a physical computer.

| Storage Type   | Examples           | Best For                                 |
| -------------- | ------------------ | ---------------------------------------- |
| Block Storage  | EBS, Azure Disk    | Databases, OS volumes, low-latency apps  |
| Object Storage | [[Amazon S3]], GCS | Images, backups, large unstructured data |
| File Storage   | EFS, Azure Files   | Shared file systems across instances     |

> [!important] 
> EBS volumes are **network-attached**, not physically plugged in. They communicate with [[Amazon EC2]] over AWS's internal network, which is optimized to be extremely fast and reliable.

# Core Characteristics

- **Persistent**: Data is retained even after the EC2 instance stops or reboots.
- **Attachable / Detachable**: You can detach a volume from one instance and attach it to another (in the same [[Availability Zones|Availability Zone]]).
- **Single-instance attachment** _(by default)_: One EBS volume attaches to one EC2 instance at a time (with the exception of **EBS Multi-Attach** for specific volume types).
- **[[Availability Zones|Availability Zone]]-bound**: An EBS volume exists within a specific AZ. To use it in another AZ, you must create a snapshot and restore it there.
- **Independently scalable**: You can resize volumes or change their type without recreating the instance.

# EBS Volume Types

AWS offers several volume types, each optimized for different workloads. They fall into two main categories: **SSD-backed** and **HDD-backed**.

## SSD-Backed Volumes (optimized for IOPS)

|Volume Type|Full Name|Use Case|
|---|---|---|
|`gp3`|General Purpose SSD v3|Most workloads — default choice|
|`gp2`|General Purpose SSD v2|Legacy general workloads|
|`io2` / `io2 Block Express`|Provisioned IOPS SSD|Databases requiring high, consistent IOPS|
|`io1`|Provisioned IOPS SSD (legacy)|High-performance databases|

## HDD-Backed Volumes (optimized for throughput)

|Volume Type|Full Name|Use Case|
|---|---|---|
|`st1`|Throughput Optimized HDD|Big data, log processing, streaming|
|`sc1`|Cold HDD|Infrequently accessed data, lowest cost|

> [!tip] 
> For most new workloads, start with **gp3**. It offers better performance than gp2 at the same or lower cost, and lets you configure IOPS and throughput independently.

> [!warning] 
> HDD-backed volumes (`st1`, `sc1`) **cannot be used as boot volumes**. Only SSD-backed volumes can serve as the root volume of an EC2 instance.

# EBS Snapshots

A **snapshot** is a point-in-time backup of an EBS volume, stored durably in [[S3]] (managed by AWS, not visible in your S3 bucket).

> [!note] How Snapshots Work 
> Snapshots are **incremental** — the first snapshot copies all data, but subsequent snapshots only save the **blocks that changed** since the last snapshot. This saves storage cost and reduces backup time.

## Common Snapshot Operations

```bash
# Create a snapshot via AWS CLI
aws ec2 create-snapshot \
  --volume-id vol-0abc123def456 \
  --description "My backup snapshot"

# List all snapshots owned by you
aws ec2 describe-snapshots --owner-ids self

# Create a volume from a snapshot
aws ec2 create-volume \
  --snapshot-id snap-0abc123def456 \
  --availability-zone us-east-1a \
  --volume-type gp3
```

> [!example] Suppose you have a production database on an EBS volume in `us-east-1a`. You want to spin up a testing environment in `us-west-2`. You would:
> 
> 1. Create a snapshot of the volume.
> 2. Copy the snapshot to `us-west-2` using `aws ec2 copy-snapshot`.
> 3. Create a new EBS volume from the copied snapshot in the new region.
> 4. Attach it to an EC2 instance there.

## Amazon Data Lifecycle Manager (DLM)

AWS provides **DLM** to automate snapshot creation, retention, and deletion on a schedule — eliminating manual backup work.

# EBS Encryption

EBS supports **encryption at rest and in transit** using [[AWS KMS]] (Key Management Service).

- Encrypted volumes use AES-256 encryption.
- Encryption covers: data at rest, data in transit between the volume and the instance, snapshots, and volumes created from those snapshots.
- Enabling encryption is a **one-time setting per volume** — you cannot encrypt an existing unencrypted volume directly.

> [!tip] How to Encrypt an Existing Unencrypted Volume
> 
> 1. Create a snapshot of the unencrypted volume.
> 2. Copy the snapshot and **enable encryption** during the copy.
> 3. Create a new volume from the encrypted snapshot.
> 4. Swap the old volume for the new one on your instance.

```bash
# Copy snapshot with encryption enabled
aws ec2 copy-snapshot \
  --source-region us-east-1 \
  --source-snapshot-id snap-0abc123 \
  --destination-region us-east-1 \
  --encrypted \
  --kms-key-id alias/my-key
```

# EBS Multi-Attach

By default, one EBS volume attaches to **one [[Amazon EC2]] instance**. However, **Multi-Attach** allows a single `io1` or `io2` volume to be attached to **up to 16 EC2 instances** in the same [[Availability Zones|Availability Zone]] simultaneously.

> [!warning] 
> Multi-Attach requires your application to handle concurrent write access carefully. It is designed for clustered databases or applications that manage their own storage coordination (e.g., using a cluster-aware file system). Using it with a standard file system like ext4 can cause **data corruption**.

# EBS vs Instance Store

A common point of confusion is the difference between EBS and **Instance Store** (also called ephemeral storage).

|Feature|EBS|Instance Store|
|---|---|---|
|Persistence|Persistent (survives stop/reboot)|Ephemeral (lost on stop/terminate)|
|Performance|High (network-attached)|Very high (physically attached)|
|Snapshots|Yes|No|
|Use case|Databases, OS, general storage|Temporary cache, buffers, scratch space|
|Cost|Charged separately|Included in instance price|

> [!important] 
> If your EC2 instance is **stopped**, instance store data is **permanently lost**. EBS data remains intact. This is one of the most tested distinctions in [[AWS]] certifications.

## EBS in Practice: Attaching and Mounting a Volume

Here is a typical workflow for attaching a new EBS volume to a Linux EC2 instance:

```bash
# 1. After attaching the volume in the AWS Console or CLI, list block devices
lsblk

# 2. Format the new volume (only for new, empty volumes)
sudo mkfs -t ext4 /dev/xvdf

# 3. Create a mount point
sudo mkdir /mnt/mydata

# 4. Mount the volume
sudo mount /dev/xvdf /mnt/mydata

# 5. Make the mount persistent across reboots (edit /etc/fstab)
echo "/dev/xvdf /mnt/mydata ext4 defaults,nofail 0 2" | sudo tee -a /etc/fstab
```

> [!warning] 
> Skipping the `nofail` option in `/etc/fstab` can cause the instance to **fail to boot** if the EBS volume is not attached at startup. Always include it for non-root volumes.

# Pricing Model

EBS pricing is based on:

- **Volume type and size** (per GB-month provisioned)
- **Provisioned IOPS** (for `io1`/`io2` volumes, charged per IOPS-month)
- **Snapshot storage** (per GB-month stored in S3)
- **Data transfer** for snapshot copies across regions

> [!tip] 
> You are billed for the **provisioned size** of the volume, not the amount of data actually written. If you provision a 500 GB volume and only use 10 GB, you pay for 500 GB. Size your volumes carefully.

# Key Integrations

- **[[Amazon EC2]]**: Primary consumer of EBS volumes.
- **[[Auto Scaling]]**: New instances can be launched with EBS volumes automatically from a launch template.
- **[[AWS KMS]]**: Manages encryption keys for EBS.
- **[[Amazon S3]]**: Stores EBS snapshot data durably behind the scenes.
- **[[CloudWatch]]**: Monitors EBS metrics like `VolumeReadOps`, `VolumeWriteOps`, `BurstBalance`, and latency.
- **[[IAM]]**: Controls who can create, attach, delete, or snapshot volumes via policies.
- **[[Terraform]]**: Commonly used to provision EBS volumes as infrastructure as code.

# External References

- [AWS EBS Official Documentation](https://docs.aws.amazon.com/ebs/latest/userguide/what-is-ebs.html)
- [EBS Volume Types Deep Dive](https://docs.aws.amazon.com/ebs/latest/userguide/ebs-volume-types.html)
- [EBS Snapshots Guide](https://docs.aws.amazon.com/ebs/latest/userguide/EBSSnapshots.html)
- [EBS Encryption](https://docs.aws.amazon.com/ebs/latest/userguide/EBSEncryption.html)
- [GeeksforGeeks - Amazon EBS Overview](https://www.geeksforgeeks.org/amazon-elastic-block-store-ebs/)

# Tags

#cloud #aws #storage #ebs #ec2 #block-storage #cloud-computing #snapshots #encryption #virtualization
# Amazon EC2 - Elastic Compute Cloud

> [!note] Definition 
> **Amazon EC2 (Elastic Compute Cloud)** is a web service provided by [[AWS]] that offers resizable virtual computing capacity in the cloud. In simpler terms, EC2 lets you rent virtual computers (called _instances_) on which you can run your own applications — without needing to buy or manage physical hardware.

EC2 is one of the foundational services of [[AWS]] and is central to understanding cloud infrastructure. Almost every real-world cloud architecture relies on EC2 in some form.

![[AWS#Key Characteristics of AWS]]

# How EC2 Works

When you launch an EC2 instance, you are essentially booting a virtual machine that runs on AWS's physical servers inside one of their [[Region|Regions]] and [[Availability Zone|Availability Zones]]. You choose:

- What **operating system** to run (via an AMI)
- How much **CPU, RAM, and storage** you need (via instance types)
- What **network** it lives in (via a [[VPC]])
- Who can **access** it (via security groups and [[IAM]])

You pay only for the time the instance is running (or reserved, depending on the pricing model).

## Core Concepts

### Amazon Machine Image (AMI)

> [!note] 
> An **AMI** is a pre-configured template used to launch an EC2 instance. It includes the operating system, any pre-installed software, and configurations.

Think of an AMI like a _snapshot or blueprint_ of a machine. When you launch an instance, you pick an AMI as the starting point.

- AWS provides official AMIs: Amazon Linux 2, Ubuntu, Windows Server, etc.
- You can create **custom AMIs** from your own configured instances.
- The [[AWS]] Marketplace also offers third-party AMIs (e.g., pre-configured with databases or firewalls).

> [!example]
>  You configure an EC2 instance with your web application and all dependencies installed. You then create a **custom AMI** from it. Later, when you need to scale, you use that AMI to launch 10 identical instances instantly.

### Instance Types

> [!note] 
> An **instance type** defines the hardware profile of your virtual machine — how many virtual CPUs (vCPUs) it has, how much RAM, network bandwidth, and storage performance.

Instance types are grouped into **families** based on their purpose:

|Family|Focus|Example Use Case|
|---|---|---|
|**General Purpose**|Balanced CPU/RAM|Web servers, dev environments|
|**Compute Optimized**|High CPU performance|Batch processing, gaming servers|
|**Memory Optimized**|High RAM|In-memory databases, big data|
|**Storage Optimized**|High disk I/O|Data warehouses, NoSQL databases|
|**Accelerated Computing**|GPUs/FPGAs|Machine learning, video rendering|

Instance type names follow a pattern like `t3.medium` or `m6i.large`:

- First letter(s) = family (`t` = general purpose burstable, `m` = general purpose, `c` = compute, etc.)
- Number = generation (`6` means 6th generation)
- Size = resource amount (`micro`, `small`, `medium`, `large`, `xlarge`, `2xlarge`, ...)

> [!tip] 
> For most beginners and small workloads, `t3.micro` or `t3.small` instances are a great starting point. The `t3.micro` tier is also included in the **AWS Free Tier**.

### Storage Options

EC2 instances can use several types of storage:

#### Elastic Block Store (EBS)

> [!note] 
> **[[EBS]] (Elastic Block Store)** is the primary block-level storage attached to EC2 instances. It behaves like a virtual hard drive — persistent, durable, and detachable.

- Data **persists** even after the instance is stopped or terminated (if configured).
- Can be **detached** from one instance and **re-attached** to another.
- Types include: `gp3` (general purpose SSD), `io2` (high-performance SSD), `st1` (throughput HDD), `sc1` (cold HDD).

#### Instance Store

> [!warning] 
> **Instance Store** is _ephemeral_ (temporary) storage physically attached to the host. **All data is lost** when the instance is stopped or terminated. Never use it for important data you need to keep.

#### S3 (Object Storage)

You can also connect your instance to [[S3]] for object storage (files, backups, images). This is not a "drive" — it's accessed via API or CLI, not mounted like a disk.

### Security Groups

> [!note] 
> A **Security Group** acts as a virtual firewall for your EC2 instance. It controls **inbound** (incoming) and **outbound** (outgoing) traffic using rules based on protocol, port, and source/destination IP.

- Security groups are **stateful**: if you allow inbound traffic on port 80, the response is automatically allowed out.
- You can assign multiple security groups to one instance.
- By default, all **inbound traffic is denied** and all **outbound traffic is allowed**.

> [!example] 
> To run a web server on EC2, you would add an inbound rule to allow TCP traffic on **port 80** (HTTP) and **port 443** (HTTPS) from anywhere (`0.0.0.0/0`). To allow SSH access, allow **port 22** from your own IP only.

### Key Pairs

> [!note] 
> A **Key Pair** is an SSH authentication mechanism. AWS stores the _public key_, and you keep the _private key_ (`.pem` file) locally. You use it to securely connect to your Linux instance via SSH.

```bash
# Connect to a Linux EC2 instance via SSH
ssh -i "my-key.pem" ec2-user@<public-ip-address>
```

> [!warning] 
> **Never share your `.pem` private key file.** If lost, AWS cannot recover it. You would need to create a new key pair and re-configure access.

### Elastic IP Addresses

By default, when you **stop and restart** an EC2 instance, its **public IP changes**. An **Elastic IP** is a static, persistent public IPv4 address you can attach to your instance so it never changes.

> [!tip] 
> Elastic IPs are free **as long as they are attached to a running instance**. You are charged if you allocate one but leave it unattached (AWS discourages IP hoarding).

# Networking

Every EC2 instance lives inside a [[VPC]] (Virtual Private Cloud). Within the VPC, you place instances in **subnets**:

- **Public subnet**: Instance is reachable from the internet (has a public IP + Internet Gateway).
- **Private subnet**: Instance is isolated from the internet (used for databases, backend services).

> [!important] 
> Understanding subnets is critical for security. You should never place a database server in a public subnet. Place it in a private subnet and only allow access from your application servers.

## Purchasing Options — How You Pay for EC2

This is one of the most important topics in EC2 for both real-world use and cloud exams.

|Option|Description|Best For|Savings vs On-Demand|
|---|---|---|---|
|**On-Demand**|Pay per second/hour, no commitment|Unpredictable workloads, testing|— (baseline)|
|**Reserved Instances**|1 or 3-year commitment|Steady, predictable workloads|Up to ~72%|
|**Savings Plans**|Flexible commitment ($/hour)|Flexible long-term usage|Up to ~66%|
|**Spot Instances**|Use spare AWS capacity at discount|Fault-tolerant, interruptible jobs|Up to ~90%|
|**Dedicated Hosts**|Physical server fully reserved for you|Compliance, licensing requirements|Varies|

> [!important] 
> **Spot Instances** can be **terminated by AWS with 2 minutes notice** when they need the capacity back. They are ideal for batch jobs, data processing, or CI/CD pipelines — not for production web servers.

## EC2 Auto Scaling

> [!note] 
> **[[Auto Scaling** automatically adjusts the number of EC2 instances in response to demand. It can add instances when traffic spikes (_scale out_) and remove them when traffic drops (_scale in_).

- **Launch Template**: Defines what kind of instance to launch (AMI, type, security group, etc.)
- **Auto Scaling Group (ASG)**: The logical group of instances managed together, with minimum, maximum, and desired capacity settings.
- **Scaling Policies**: Rules that trigger scaling (e.g., add 2 instances when CPU > 70%).

> [!tip] 
> Auto Scaling works best when combined with a [[Load Balancer]] ([[Elastic Load Balancer|ELB]]). The load balancer distributes traffic across all instances, and Auto Scaling ensures there are always enough healthy instances to handle the load.

# EC2 [[Load Balancer|Load Balancing]] — ELB

[[AWS]] offers **Elastic Load Balancing (ELB)** to distribute incoming traffic across multiple EC2 instances:

- **Application Load Balancer (ALB)**: Best for HTTP/HTTPS traffic; works at Layer 7; can route by URL path or hostname.
- **Network Load Balancer (NLB)**: Extremely high performance; works at Layer 4 (TCP/UDP).
- **Gateway Load Balancer (GWLB)**: Used with third-party network appliances (firewalls, etc.).

# Connecting to EC2 — Access Methods

|Method|When to Use|
|---|---|
|**SSH** (Linux)|Standard terminal access using `.pem` key|
|**RDP** (Windows)|Remote Desktop for Windows instances|
|**EC2 Instance Connect**|Browser-based SSH, no key file needed|
|**AWS Systems Manager Session Manager**|No open ports or SSH keys required; most secure|

> [!tip] 
> **Session Manager** (via [[AWS]] Systems Manager) is the most secure option — it requires no inbound security group rules and no SSH key pairs. Highly recommended for production environments.

# EC2 Lifecycle

An EC2 instance goes through several states:

```
Pending → Running → Stopping → Stopped → Terminated
                 ↘ Shutting-down → Terminated
```

- **Pending**: Instance is booting up.
- **Running**: Instance is active and you are being billed.
- **Stopped**: Instance is off; you are **not** billed for compute (but still billed for EBS storage).
- **Terminated**: Instance is permanently deleted; cannot be recovered.

> [!warning] 
> **Stopping vs Terminating** is a critical distinction. Stopping is like turning off a computer — you can restart it. Terminating is like destroying the computer permanently. Be careful with the `terminate` command.

# Useful AWS CLI Commands for EC2

```bash
# List all running instances
aws ec2 describe-instances --filters "Name=instance-state-name,Values=running"

# Start an instance
aws ec2 start-instances --instance-ids i-0abcd1234efgh5678

# Stop an instance
aws ec2 stop-instances --instance-ids i-0abcd1234efgh5678

# Terminate an instance
aws ec2 terminate-instances --instance-ids i-0abcd1234efgh5678

# Launch a new instance
aws ec2 run-instances \
  --image-id ami-0abcdef1234567890 \
  --instance-type t3.micro \
  --key-name my-key-pair \
  --security-group-ids sg-0abc123 \
  --subnet-id subnet-0abc123
```

## EC2 and High Availability

> [!important] 
> For high availability, always deploy EC2 instances across **multiple [[Availability Zone|Availability Zones]]**. If one AZ goes down (due to a power outage or disaster), instances in other AZs keep running.

A typical highly available architecture looks like:

- [[Load Balancer]] (spans multiple AZs) receives traffic
- Distributes to an [[Auto Scaling]] Group with instances in **AZ-A** and **AZ-B**
- Instances connect to a database in a private subnet (also multi-AZ with [[RDS]])

# IAM and EC2

> [!note] 
> An **IAM Instance Role** allows an EC2 instance to interact with other AWS services (like [[S3]] or [[DynamoDB]]) **without embedding credentials in your code**.

> [!warning] 
> Never hardcode AWS `Access Key ID` and `Secret Access Key` directly in your EC2 instance or application code. Always use **IAM Roles** attached to the instance. This is a fundamental security best practice.

```bash
# On an EC2 instance with an IAM role attached, this just works — no credentials needed:
aws s3 ls s3://my-bucket
```

## External References

- [AWS EC2 Official Documentation](https://docs.aws.amazon.com/ec2/)
- [EC2 Instance Types](https://aws.amazon.com/ec2/instance-types/)
- [EC2 Pricing](https://aws.amazon.com/ec2/pricing/)
- [AWS Free Tier](https://aws.amazon.com/free/)
- [GeeksForGeeks — Amazon EC2](https://www.geeksforgeeks.org/amazon-ec2/)
- [AWS Security Best Practices for EC2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-security.html)

---

# Tags

#cloud #aws #ec2 #compute #virtualization #cloud-computing #networking #storage #security #iam #auto-scaling #load-balancer #devops
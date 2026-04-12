
> [!note] Definition
> An **AWS Region** is a physical geographic area in the world where [[AWS]] operates a cluster of data centers. Each Region is a separate, independent location designed to provide cloud services to customers in that part of the world.

Think of it like this: AWS has built "cloud hubs" around the globe. When you deploy an application, you choose *which hub* hosts your resources — and that choice has real consequences for performance, cost, compliance, and availability.

# Why Regions Exist

AWS Regions solve several fundamental problems at once:

| Problem | How Regions Help |
|---|---|
| **Latency** | Resources closer to your users respond faster |
| **Data sovereignty** | Laws may require data to stay within a country |
| **Fault isolation** | A disaster in one Region does not affect others |
| **Compliance** | Some industries require geographic data boundaries |
| **Redundancy** | You can architect multi-region systems for resilience |

> [!important]
> **Data does not move between Regions automatically.** If you store data in `us-east-1`, it stays there unless you explicitly replicate it. This is a core AWS design principle called *Region isolation*.

# Region Naming Convention

AWS Regions follow a consistent naming pattern:

```
<geographic-area>-<direction>-<number>
```

| Region Code | Location |
|---|---|
| `us-east-1` | Northern Virginia, USA |
| `us-west-2` | Oregon, USA |
| `eu-west-1` | Ireland |
| `ap-southeast-1` | Singapore |
| `sa-east-1` | São Paulo, Brazil |
| `ca-central-1` | Canada (Central) |
| `me-south-1` | Bahrain |

> [!tip]
> `us-east-1` (N. Virginia) is AWS's oldest and largest Region. New services are almost always launched there first. If a service is not available in your Region, check `us-east-1` — it is likely there already.

# Inside a Region: Availability Zones

Each Region is made up of multiple **[[Availability Zone]]s** (AZs). This is one of the most important concepts to understand alongside Regions.

```
AWS Region (e.g., us-east-1)
│
├── Availability Zone A  (us-east-1a)  ← one or more physical data centers
├── Availability Zone B  (us-east-1b)
└── Availability Zone C  (us-east-1c)
```

- Each AZ is one or more **physically separate data centers** with independent power, cooling, and networking.
- AZs within a Region are connected by **low-latency, high-bandwidth private fiber links**.
- A failure in one AZ (fire, flood, power loss) does not bring down other AZs in the same Region.

> [!important]
> **Always deploy across multiple AZs** for high availability. A single-AZ deployment is a single point of failure. Services like [[Load Balancer]], [[Auto Scaling]], and [[RDS]] make multi-AZ deployment straightforward.

# How to Choose a Region

When launching any AWS resource, you must select a Region. Consider these four factors:

## 1. Latency / Proximity to Users

Deploy close to your end users. A user in Australia will experience significantly higher latency connecting to `us-east-1` than to `ap-southeast-2` (Sydney).

## 2. Compliance and Data Residency

- GDPR (Europe) may require data to stay within EU Regions.
- Financial institutions may have country-specific regulations.
- Healthcare data may be subject to local laws.

## 3. Service Availability

Not all AWS services are available in every Region. Always check the [AWS Regional Services List](https://aws.amazon.com/about-aws/global-infrastructure/regional-product-services/) before designing your architecture.

## 4. Pricing

Prices vary by Region. `us-east-1` is often one of the cheapest. Running the same [[EC2]] instance in `ap-southeast-1` (Singapore) may cost more.

> [!example]
> A Brazilian e-commerce company serving Brazilian customers should use `sa-east-1` (São Paulo) to minimize latency and comply with Brazil's LGPD data protection law, which requires personal data of Brazilian citizens to be processed locally.

# Regions and AWS Services

Not all AWS services are Region-scoped. Understanding this distinction matters when building architectures.

| Scope | Examples | What it means |
|---|---|---|
| **Global** | [[IAM]], [[Route 53]], [[CloudFront]] | Not tied to any single Region |
| **Regional** | [[EC2]], [[S3]], [[VPC]], [[Lambda]] | Resources exist within a specific Region |
| **AZ-specific** | EC2 instances, EBS volumes | Tied to a single Availability Zone |

> [!warning]
> [[S3]] bucket names are globally unique, but the bucket itself lives in a specific Region. Beginners often assume S3 is fully global — it is not. A bucket created in `eu-west-1` stores data in Ireland unless you configure [[Cross-Region Replication]].

## Multi-Region Architectures

For critical applications, you may deploy across **multiple Regions** simultaneously. This is more complex but offers the highest level of resilience.

```
Users (Global)
      │
  [Route 53] ← DNS-based routing
      │
  ┌───┴────┐
  │        │
us-east-1  eu-west-1   ← Active-Active or Active-Passive
  │        │
 App      App
  │        │
  DB       DB  ← Replicated across Regions
```

Common patterns:
- **Active-Passive**: One Region handles traffic; the other is on standby for disaster recovery.
- **Active-Active**: Both Regions serve traffic simultaneously, improving performance and resilience.

> [!tip]
> Multi-region setups are expensive and complex. Start with multi-AZ within a single Region. Only graduate to multi-region when your availability requirements (e.g., 99.99%+ uptime) genuinely demand it.

# Working with Regions via CLI

When using the [[AWS CLI]], you specify a Region using the `--region` flag or by setting a default in your configuration.

```bash
# List all available AWS Regions
aws ec2 describe-regions --output table

# Run a command in a specific Region
aws s3 ls --region eu-west-1

# Set a default Region in your AWS CLI config
aws configure set region us-east-1

# Check your current default Region
aws configure get region
```

> [!example]
> To list all EC2 instances running in the Singapore Region:
> ```bash
> aws ec2 describe-instances --region ap-southeast-1 \
>   --query 'Reservations[*].Instances[*].[InstanceId,State.Name]' \
>   --output table
> ```

# Regions in Infrastructure as Code

When using [[Terraform]] to deploy AWS resources, you declare the Region in the provider block:

```hcl
provider "aws" {
  region = "us-east-1"
}
```

To deploy the same infrastructure to multiple Regions, you can use provider aliases:

```hcl
provider "aws" {
  alias  = "primary"
  region = "us-east-1"
}

provider "aws" {
  alias  = "secondary"
  region = "eu-west-1"
}
```

# Current AWS Global Infrastructure

As of 2025, AWS operates:
- **34+ Regions** worldwide
- **108+ Availability Zones**
- With announced expansion into more countries

For the most current map and list, always refer to the official source:
[https://aws.amazon.com/about-aws/global-infrastructure/](https://aws.amazon.com/about-aws/global-infrastructure/)

# Other Resources

- [AWS Global Infrastructure Overview](https://aws.amazon.com/about-aws/global-infrastructure/)
- [AWS Regional Services List](https://aws.amazon.com/about-aws/global-infrastructure/regional-product-services/)
- [AWS Documentation — Regions and AZs](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/using-regions-availability-zones.html)
- [GeeksforGeeks — AWS Regions and Availability Zones](https://www.geeksforgeeks.org/aws-regions-and-availability-zones/)
- [AWS CLI Region Configuration](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-options.html)

# Tags

#cloud #aws #cloud-computing #networking #infrastructure #regions #availability-zones #architecture #high-availability #disaster-recovery #compliance #iac
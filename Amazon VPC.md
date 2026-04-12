
> [!note] What is Amazon VPC? 
> **Amazon Virtual Private Cloud (VPC)** is a service provided by [[AWS]] that lets you launch AWS resources inside a **logically isolated virtual network** that you define. Think of it as your own private section of the AWS cloud — like having your own data center, but hosted by Amazon.

You have full control over your virtual networking environment, including:

- Selecting your own IP address range
- Creating subnets
- Configuring route tables and network gateways
- Setting up security rules

# Why Does VPC Exist?

By default, cloud resources are reachable over the internet. This is fine for some services, but most real-world applications need **network isolation** — you don't want your database server exposed to the public internet. Amazon VPC solves this by giving you a private, customizable network.

> [!important] Core Idea 
> A VPC is a **software-defined network** inside AWS. It replicates what you'd do with physical networking hardware (routers, firewalls, switches) but entirely in software, giving you flexibility and control without managing hardware.

# Key Components of a VPC

## 1. CIDR Block (IP Address Range)

When you create a VPC, you assign it a **CIDR block** — a range of IP addresses your resources will use.

> [!example] CIDR Example 
> `10.0.0.0/16` — This gives you **65,536** IP addresses (from `10.0.0.0` to `10.0.255.255`).

- AWS supports both **IPv4** and **IPv6** CIDR blocks.
- The CIDR block cannot be changed after creation, so plan it carefully.

## 2. Subnets

A **subnet** is a subdivision of your VPC's IP range. You place AWS resources (like [[EC2]] instances) inside subnets.

There are two types:

|Type|Description|Internet Access?|
|---|---|---|
|**Public Subnet**|Has a route to the internet via an [[Internet Gateway]]|Yes|
|**Private Subnet**|No direct route to the internet|No (by default)|

> [!tip] Best Practice
> Place your **web servers** in public subnets and your **databases** in private subnets. This follows the principle of least privilege for network exposure.

Each subnet lives inside a specific **[[Availability Zones|Availability Zone]]** (AZ). Distributing subnets across multiple AZs improves fault tolerance.

## 3. Internet Gateway (IGW)

An **[[Internet Gateway]]** is a horizontally scaled, redundant VPC component that allows communication between your VPC and the internet.

- You **attach one IGW per VPC**.
- For a subnet to be "public," its route table must have a route pointing to the IGW.

```
Route Table (Public Subnet):
  Destination: 0.0.0.0/0  →  Target: igw-xxxxxxxx
```

## 4. Route Tables

A **route table** contains a set of rules (_routes_) that determine where network traffic is directed.

- Every subnet must be associated with a route table.
- By default, a VPC has a **main route table** that allows local traffic within the VPC.

> [!example] Route Table Example
> 
> |Destination|Target|Purpose|
> |---|---|---|
> |`10.0.0.0/16`|local|Traffic within the VPC|
> |`0.0.0.0/0`|igw-xxxxxxxx|All other traffic goes to Internet|

## 5. Security Groups

A **Security Group** acts as a **virtual firewall** for your EC2 instances, controlling inbound and outbound traffic at the **instance level**.

Key characteristics:

- **Stateful**: If you allow inbound traffic, the response is automatically allowed outbound.
- Rules are **allow-only** — you cannot create explicit deny rules.
- Multiple instances can share the same security group.

> [!example] Security Group Rule Example Allow inbound HTTP from anywhere:
> 
> - Type: HTTP
> - Protocol: TCP
> - Port: 80
> - Source: `0.0.0.0/0`

## 6. Network Access Control Lists (NACLs)

A **[[NACL]]** is a firewall at the **subnet level**, controlling traffic going in and out of a subnet.

> [!important] Security Group vs NACL
> 
> |Feature|Security Group|NACL|
> |---|---|---|
> |Level|Instance|Subnet|
> |State|Stateful|Stateless|
> |Rules|Allow only|Allow and Deny|
> |Evaluation|All rules evaluated|Rules evaluated in order|

> [!warning] Common Misconception NACLs are **stateless** — if you allow inbound traffic on port 80, you must _also_ explicitly allow the outbound response. Forgetting this is a classic networking mistake.

## 7. NAT Gateway

A **[[NAT Gateway]]** (Network Address Translation) allows instances in a **private subnet** to access the internet (for updates, API calls, etc.) without being directly reachable from the internet.

- Traffic flows: Private Subnet → NAT Gateway (in public subnet) → Internet Gateway → Internet
- The NAT Gateway must live in a **public subnet**.
- AWS manages it for you (no server to maintain).

```
Private EC2 instance → NAT Gateway → IGW → Internet
                        (public subnet)
```

> [!note] NAT Instance vs NAT Gateway
> AWS originally used **NAT Instances** (a manually managed EC2 instance). **NAT Gateway** is the modern, fully managed alternative — prefer it over NAT Instances.

## 8. VPC Peering

**[[VPC Peering]]** lets you connect two VPCs (in the same or different AWS accounts/regions) so resources in each can communicate as if they are on the same network.

- Traffic stays within the AWS backbone — it does not traverse the public internet.
- CIDR blocks of peered VPCs **must not overlap**.
- Peering is **non-transitive**: if VPC A peers with B, and B peers with C, A cannot reach C through B.

## 9. VPC Endpoints

**[[VPC Endpoint]]** allows you to connect your VPC to AWS services (like [[S3]] or [[DynamoDB]]) **privately**, without sending traffic over the internet.

Two types:

|Type|For|Example|
|---|---|---|
|**Gateway Endpoint**|S3, DynamoDB|Free|
|**Interface Endpoint**|Most other AWS services|Paid (PrivateLink)|

> [!tip] 
> Use VPC Endpoints when your private EC2 instances need to access [[Amazon S3]] without going through a NAT Gateway — it's more secure and often cheaper.

## 10. Elastic IP Addresses

An **Elastic IP (EIP)** is a **static public IPv4 address** that you can allocate to your AWS account and associate with an EC2 instance or NAT Gateway. Unlike default public IPs, it doesn't change if you stop/start an instance.

# Default VPC

AWS creates a **default VPC** in every [[Region]] for your account. It comes pre-configured with:

- A `/16` IPv4 CIDR block (`172.31.0.0/16`)
- A public subnet in each Availability Zone
- An attached Internet Gateway
- A default route table and security group

> [!warning] 
> The default VPC is convenient for quick testing, but for production workloads, always create a **custom VPC** with a well-thought-out network design.


# Typical VPC Architecture (3-Tier Web App)

```
                        Internet
                           |
                    [Internet Gateway]
                           |
         +-----------------+-----------------+
         |                                   |
   [Public Subnet AZ-1]             [Public Subnet AZ-2]
   Web Server / ALB                 Web Server / ALB
         |                                   |
   [Private Subnet AZ-1]            [Private Subnet AZ-2]
   App Servers                       App Servers
         |                                   |
   [Private Subnet AZ-1]            [Private Subnet AZ-2]
   Database (RDS)                    Database (RDS replica)
```

This pattern:

- Exposes only the Load Balancer / web tier to the internet
- Keeps app logic and databases private
- Distributes across multiple AZs for high availability

# Creating a VPC with AWS CLI

```bash
# Create a VPC with CIDR block
aws ec2 create-vpc --cidr-block 10.0.0.0/16

# Create a public subnet
aws ec2 create-subnet \
  --vpc-id vpc-xxxxxxxx \
  --cidr-block 10.0.1.0/24 \
  --availability-zone us-east-1a

# Create and attach an Internet Gateway
aws ec2 create-internet-gateway
aws ec2 attach-internet-gateway \
  --vpc-id vpc-xxxxxxxx \
  --internet-gateway-id igw-xxxxxxxx

# Create a route table and add public route
aws ec2 create-route-table --vpc-id vpc-xxxxxxxx
aws ec2 create-route \
  --route-table-id rtb-xxxxxxxx \
  --destination-cidr-block 0.0.0.0/0 \
  --gateway-id igw-xxxxxxxx
```

# VPC Flow Logs

**VPC Flow Logs** capture information about the IP traffic going to and from network interfaces in your VPC. They are essential for:

- Security auditing
- Troubleshooting connectivity issues
- Monitoring unusual traffic patterns

Logs can be published to **[[CloudWatch]] Logs** or **[[Amazon S3]]**.

> [!tip] 
> Flow Logs do not capture the actual packet content — only metadata (source IP, destination IP, port, protocol, action: ACCEPT/REJECT).

# Integration with Other AWS Services

| Service                  | VPC Role                                   |
| ------------------------ | ------------------------------------------ |
| [[Amazon EC2]]           | Instances run inside VPC subnets           |
| [[Amazon RDS]]           | Databases placed in private subnets        |
| [[AWS Lambda]]           | Can be configured to run inside a VPC      |
| [[EKS]] / [[Kubernetes]] | Node groups live in VPC subnets            |
| [[Auto Scaling]]         | Launches instances across subnets/AZs      |
| [[Load Balancer]]        | ALB/NLB distributes traffic across subnets |
| [[IAM]]                  | Controls who can manage VPC resources      |

# External Resources

- [AWS VPC Official Documentation](https://docs.aws.amazon.com/vpc/latest/userguide/what-is-amazon-vpc.html)
- [AWS VPC Pricing](https://aws.amazon.com/vpc/pricing/)
- [GeeksForGeeks - Amazon VPC](https://www.geeksforgeeks.org/amazon-vpc/)
- [AWS re:Post - VPC Best Practices](https://repost.aws/knowledge-center/vpc-best-practices)


# Tags

#cloud #aws #networking #vpc #security #cloud-computing #subnets #firewall #nat #infrastructure
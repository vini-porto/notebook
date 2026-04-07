# AWS Global Infrastructure

**[[AWS Global Infrastructure]]**: Designed and built to deliver a flexible, reliable, scalable, and secure cloud computing environment with high-quality global network performance.

# AWS Regions

## What is an AWS Region?

**[[AWS Region]]**: A physical geographical location with one or more [[Availability Zones]].

**Current scale**: AWS has 22 Regions worldwide (as of the lecture date).

> [!Tip] **Infrastructure hierarchy**: 
> AWS Cloud infrastructure is built around Regions, which contain Availability Zones, which in turn consist of one or more [[Data Centers]].

## Region Isolation and Data Residency

**Key characteristics**:

- Each Region is completely independent
- Resources in one Region are **not automatically replicated** to other Regions
- Regions are isolated from one another for **[[Fault Tolerance]]** and stability

**[[Data Residency]]**: When you store data in a specific Region, it is **not replicated outside that Region** unless you explicitly configure it.

> [!Warning] Your responsibility 
>You must replicate data across Regions if your business needs require it.

## Region Availability

**Default-enabled Regions**: Regions introduced before March 20, 2019 are enabled by default.

**[[Opt-in Regions]]**: Regions introduced after March 20, 2019 are disabled by default.

> [!example] **Examples of opt-in Regions**:
> 
> - Asia Pacific (Hong Kong)
> - Middle East (Bahrain)

**Enabling Regions**: Use the [[AWS Management Console]] to enable or disable Regions as needed.

# Selecting a Region

When choosing the optimal Region(s) for your workloads, consider these factors:

## 1. Data Governance and Legal Requirements

**[[Data Governance]]**: Local laws might require certain information be kept within geographical boundaries.

**[[Compliance Requirements]]**: Laws may restrict where you can offer content or services.

> [!example]
> [[European Union Data Protection Directive]] requires certain data to remain within EU boundaries.

>[!tip] **Principle**: 
Always verify legal and regulatory requirements before selecting a Region.

## 2. Latency (Proximity to Users)

**Goal**: Run applications and store data in a Region as close as possible to users and systems that access them.

**Benefit**: Reduces **[[Latency]]**—the delay between request and response.

**Testing tool**: [[CloudPing]] is a website to test latency between your location and all AWS Regions.

## 3. Service Availability

**Consideration**: Some AWS services and features are not available in all Regions.

**Action**: Verify that the services you need are available in your chosen Region before deployment.

## 4. Cost Variation

**[[Regional Pricing]]**: The cost of running services varies by Region.

> [!example] **Example comparison** (On-Demand t3.medium [[Amazon EC2]] Linux instance):
> 
> - **US East (Ohio)**: $0.0416 per hour
> - **Asia Pacific (Tokyo)**: $0.0544 per hour
> 

**30% price difference** for the same resource in different Regions.

**Strategy**: Balance cost savings with performance and compliance requirements.

# Availability Zones

## What is an Availability Zone?

**[[Availability Zone]]** (AZ): Multiple isolated locations within each AWS Region.

**Purpose**: Operate applications and databases that are more highly available, fault-tolerant, and scalable than single data center deployments.

## Availability Zone Characteristics

### Physical composition:

- Each AZ can include multiple data centers (typically **three**)
- At full scale, hundreds of thousands of servers
- Fully isolated partitions of AWS Global Infrastructure

### Infrastructure independence:

- Own **power infrastructure**
- Physically separated by **many kilometers** from other AZs
- All AZs within **100 km** of each other in the same Region

### Connectivity between AZs:

- Interconnected with **high-bandwidth, low-latency networking**
- Fully redundant, dedicated fiber connections
- Provides **high-throughput** between AZs
- Enables **[[Synchronous Replication]]** between Availability Zones

## Building Highly Available Applications

**[[High Availability]]**: When applications are partitioned across Availability Zones, companies are better isolated and protected from:

- Lightning strikes
- Tornadoes
- Earthquakes
- Power failures
- Natural disasters
- Infrastructure failures

> [!warning] Your responsibility:
> 
> - Select which Availability Zones your systems will reside in
> - Systems can span multiple Availability Zones

>[!tip] 
>**[[AWS Recommendation]]**: Replicate across Availability Zones for **[[Resiliency]]**.

**Design principle**: Design systems to survive the temporary or prolonged failure of an Availability Zone if a disaster occurs.

# AWS Data Centers

## Foundation of AWS Infrastructure

**[[Data Centers]]**: The physical locations where actual data resides and servers operate.

**Customer control**: Customers do **not** specify a data center for resource deployment—Availability Zone is the most granular level of specification.

**Scale**: Amazon operates state-of-the-art, highly available data centers worldwide.

## Data Center Design and Operations

**Risk mitigation**: Each location is carefully evaluated to mitigate environmental risk.

**[[Redundant Design]]**: Data centers anticipate and tolerate failure while maintaining service levels.

> [!NOTE] Key operational principles:
> 
> **Availability**: Critical system components are backed up across multiple Availability Zones.
> 
> **Capacity management**: AWS continuously monitors service usage to deploy infrastructure supporting availability commitments.

**Security**:

- Data center locations are **not disclosed**
- All access to them is **restricted**

> [!warning]
> **Failure recovery**: Automated processes move data traffic away from affected areas in case of failure.

## Network Equipment

**Custom infrastructure**: AWS uses custom network equipment sourced from multiple [[Original Device Manufacturers]] (ODMs).

**[[ODM Model]]**: ODMs design and manufacture products based on specifications from AWS. AWS then rebrands the products for deployment.

**Benefit**: Custom-designed hardware optimized for AWS workloads and requirements.

# Points of Presence

## Edge Locations and Content Delivery

**[[Points of Presence]]**: Locations in major cities around the world for content delivery and DNS services.

> [!example] Primary services using Points of Presence:
> 
> - [[Amazon CloudFront]] - Content Delivery Network (CDN)
> - [[Amazon Route 53]] - Domain Name System (DNS) service
> - [[AWS Shield]] - DDoS protection
> - [[AWS WAF]] - Web Application Firewall

## How Points of Presence Work

**[[Amazon CloudFront]]**: CDN used to distribute content to end users to reduce latency.

**[[Amazon Route 53]]**: DNS service that translates domain names to IP addresses.

**Automatic routing**: Requests to these services are routed to the **nearest edge location** automatically to lower latency.

**Continuous optimization**: By continuously measuring internet connectivity, performance, and computing, Points of Presence find the best way to route requests.

**Result**: Better **near real-time user experience**.

## Regional Edge Caches

**[[Regional Edge Caches]]**: Used by default with Amazon CloudFront.

**Purpose**: For content that is not accessed frequently enough to remain in an edge location.

**Function**:

- Absorb less-frequently accessed content
- Provide an alternative to fetching content from the origin server
- Sit between edge locations and origin servers

**Benefit**: Improved performance without requiring content to travel all the way back to the origin.

# AWS Infrastructure Features

## Key Characteristics

The AWS Global Infrastructure has three valuable features:

## 1. Elastic and Scalable

**[[Elasticity]]**: Resources can dynamically adjust to increases or decreases in capacity requirements.

**[[Scalability]]**: Infrastructure can rapidly adjust to accommodate growth.

**Benefit**: Handle variable workloads without manual intervention or over-provisioning.

## 2. Fault Tolerant

**[[Fault Tolerance]]**: Built-in component redundancy enables continued operations despite failed components.

**How it works**: Multiple redundant systems ensure that if one component fails, others take over seamlessly.

**Result**: Applications remain available even during hardware or software failures.

## 3. High Availability with Minimal Human Intervention

**[[High Availability]]**: Minimal downtime through automated systems.

**Automation**: Requires minimal to no human intervention for routine operations and failover.

**Benefit**: Reduces operational overhead and human error while maintaining uptime.

# AWS Services and Service Categories

## AWS Foundational Services

The AWS Global Infrastructure provides the platform for a broad set of services:

**Three infrastructure elements**:

1. **Regions**
2. **Availability Zones**
3. **Points of Presence** (including edge locations)

**Service delivery model**:

- On-demand utility
- Available in seconds
- Pay-as-you-go pricing

**Service types**: Networking, storage, compute services, databases, and many more.

## AWS Service Categories

**Total categories**: 23 different product or service categories.

**Structure**: Each category consists of one or more services.

**Focus areas** (most widely used and exam-relevant):

- **Compute**
- **Cost Management**
- **Database**
- **Management and Governance**
- **Networking and Content Delivery**
- **Security, Identity, and Compliance**
- **Storage**

# Storage Service Category

## Amazon Simple Storage Service (Amazon S3)

**[[Amazon S3]]**: Object storage service offering scalability, data availability, security, and performance.

**Use cases**:

- Websites
- Mobile apps
- Backup and restore
- Archive
- Enterprise applications
- [[IoT]] (Internet of Things) devices
- Big data analytics

**Key feature**: Store and protect any amount of data.

## Amazon Elastic Block Store (Amazon EBS)

**[[Amazon EBS]]**: High-performance **[[Block Storage]]** designed for use with Amazon EC2.

**Optimized for**: Both throughput and transaction-intensive workloads.

**Use cases**:

- Relational and non-relational databases
- Enterprise applications
- Containerized applications
- Big data analytics engines
- File systems
- Media workflows

**Characteristic**: Provides persistent block-level storage volumes.

## Amazon Elastic File System (Amazon EFS)

**[[Amazon EFS]]**: Scalable, fully managed elastic **[[Network File System]]** (NFS).

**Compatibility**: Works with AWS Cloud services and on-premises resources.

**Key features**:

- Built to scale on demand to petabytes
- Grows and shrinks automatically as you add/remove files
- Reduces need to provision and manage capacity

**Benefit**: Pay only for storage actually used—no capacity planning needed.

## Amazon S3 Glacier

**[[Amazon S3 Glacier]]**: Secure, durable, extremely low-cost storage class for data archiving and long-term backup.

**Durability**: Designed to deliver **11 nines** (99.999999999%) of durability.

**Compliance**: Comprehensive security and compliance capabilities to meet stringent regulatory requirements.

**Use case**: Long-term archival where immediate access is not required.

# Compute Service Category

## Amazon Elastic Compute Cloud (Amazon EC2)

**[[Amazon EC2]]**: Provides resizable compute capacity as virtual machines in the cloud.

**Flexibility**: Choose instance types based on CPU, memory, storage, and networking capacity needs.

## Amazon EC2 Auto Scaling

**[[Amazon EC2 Auto Scaling]]**: Automatically add or remove EC2 instances according to conditions you define.

**Benefit**: Maintain performance during demand spikes, reduce costs during low demand.

## Amazon Elastic Container Service (Amazon ECS)

**[[Amazon ECS]]**: Highly scalable, high-performance **[[Container Orchestration]]** service.

**Container support**: Supports [[Docker]] containers.

**Use case**: Deploy and manage containerized applications at scale.

## Amazon Elastic Container Registry (Amazon ECR)

**[[Amazon ECR]]**: Fully managed [[Docker]] container registry.

**Purpose**: Makes it easy to store, manage, and deploy Docker container images.

**Integration**: Seamlessly integrates with Amazon ECS.

## AWS Elastic Beanstalk

**[[AWS Elastic Beanstalk]]**: Service for deploying and scaling web applications.

**Supported servers**: Apache, Microsoft IIS, and other familiar servers.

**Benefit**: Deploy applications without managing underlying infrastructure.

## AWS Lambda

**[[AWS Lambda]]**: Run code without provisioning or managing servers (**[[Serverless Computing]]**).

**Pricing model**: Pay only for compute time consumed—no charge when code isn't running.

**Use case**: Event-driven applications, microservices, data processing.

### Amazon Elastic Kubernetes Service (Amazon EKS)

**[[Amazon EKS]]**: Makes it easy to deploy, manage, and scale containerized applications using [[Kubernetes]].

**Benefit**: Managed Kubernetes service—AWS handles control plane management.

### AWS Fargate

**[[AWS Fargate]]**: Compute engine for Amazon ECS and EKS.

**Key feature**: Run containers **without managing servers or clusters**.

**Benefit**: True serverless container experience.

# Database Service Category

## Amazon Relational Database Service (Amazon RDS)

**[[Amazon RDS]]**: Makes it easy to set up, operate, and scale **[[Relational Databases]]** in the cloud.

**Automation**: Handles hardware provisioning, database setup, patching, and backups.

**Benefit**: Resizable capacity with reduced administrative burden.

## Amazon Aurora

**[[Amazon Aurora]]**: MySQL and PostgreSQL-compatible relational database.

**Performance**:

- Up to **5x faster** than standard MySQL databases
- Up to **3x faster** than standard PostgreSQL databases

**Use case**: High-performance relational database workloads.

## Amazon Redshift

**[[Amazon Redshift]]**: Data warehousing service for analytic queries.

**Scale**:

- Run queries against petabytes of data in Redshift
- Query exabytes of data stored in Amazon S3

**Performance**: Fast performance at any scale.

**Use case**: Business intelligence, analytics, data warehousing.

## Amazon DynamoDB

**[[Amazon DynamoDB]]**: **[[Key-Value Database]]** and **[[Document Database]]**.

**Performance**: Single-digit millisecond performance at any scale.

**Features**: Built-in security, backup and restore, in-memory caching.

**Use case**: High-performance NoSQL database needs.

# Networking and Content Delivery Service Category

## Amazon Virtual Private Cloud (Amazon VPC)

**[[Amazon VPC]]**: Provision logically isolated sections of the AWS Cloud.

**Purpose**: Create your own virtual network within AWS with complete control over networking.

## Elastic Load Balancing

**[[Elastic Load Balancing]]**: Automatically distributes incoming application traffic across multiple targets.

**Targets supported**:

- Amazon EC2 instances
- Containers
- IP addresses
- Lambda functions

**Benefit**: Improves application availability and fault tolerance.

## Amazon CloudFront

**[[Amazon CloudFront]]**: Fast **[[Content Delivery Network]]** (CDN) service.

**Capability**: Securely delivers data, videos, applications, and APIs to customers globally.

**Characteristics**: Low latency and high transfer speeds.

**Use case**: Accelerate website content delivery worldwide.

## AWS Transit Gateway

**[[AWS Transit Gateway]]**: Connects Amazon VPCs and on-premises networks to a single gateway.

**Benefit**: Simplifies network architecture and reduces operational complexity.

**Use case**: Hub-and-spoke network topology for multiple VPCs.

## Amazon Route 53

**[[Amazon Route 53]]**: Scalable cloud **[[Domain Name System]]** (DNS) web service.

**Function**: Routes end users to internet applications by translating names (like www.example.com) to IP addresses (like 192.0.2.1).

**Reliability**: Designed to give reliable routing to applications.

## AWS Direct Connect

**[[AWS Direct Connect]]**: Establishes dedicated private network connection from your data center or office to AWS.

**Benefits**:

- Reduce network costs
- Increase bandwidth throughput
- More consistent network experience than internet-based connections

**Use case**: Hybrid cloud architectures requiring stable, high-bandwidth connections.

# Security, Identity, and Compliance Service Category

## AWS Identity and Access Management (IAM)

**[[AWS IAM]]**: Manage access to AWS services and resources securely.

**Capabilities**:

- Create and manage AWS users and groups
- Use permissions to allow/deny access to AWS resources

**Benefit**: Fine-grained access control for security.

## AWS Organizations

**[[AWS Organizations]]**: Restrict what services and actions are allowed in your accounts.

**Use case**: Centrally manage policies across multiple AWS accounts.

## Amazon Cognito

**[[Amazon Cognito]]**: Add user sign-up, sign-in, and access control to web and mobile apps.

**Benefit**: User authentication and authorization without building from scratch.

## AWS Artifact

**[[AWS Artifact]]**: Provides on-demand access to AWS security and compliance reports.

**Content**: Select online agreements and compliance documentation.

**Use case**: Meeting audit and compliance requirements.

## AWS Key Management Service (AWS KMS)

**[[AWS KMS]]**: Create and manage **[[Encryption Keys]]**.

**Control**: Manage use of encryption across AWS services and in your applications.

**Benefit**: Centralized key management for data encryption.

## AWS Shield

**[[AWS Shield]]**: Managed **[[DDoS Protection]]** (Distributed Denial of Service) service.

**Purpose**: Safeguards applications running on AWS from DDoS attacks.

**Availability**: Standard protection included free, advanced protection available.

# AWS Cost Management Service Category

## AWS Cost and Usage Report

**[[AWS Cost and Usage Report]]**: Most comprehensive set of AWS cost and usage data available.

**Includes**: Additional metadata about AWS services, pricing, and reservations.

**Use case**: Detailed cost analysis and chargeback.

## AWS Budgets

**[[AWS Budgets]]**: Set custom budgets that alert you when costs or usage exceed thresholds.

**Alerts**: Notifications when actual or forecasted costs exceed budgeted amounts.

**Benefit**: Proactive cost management.

## AWS Cost Explorer

**[[AWS Cost Explorer]]**: Easy-to-use interface to visualize, understand, and manage AWS costs and usage over time.

**Features**:

- View historical cost data
- Forecast future costs
- Identify cost trends and anomalies

# Management and Governance Service Category

## AWS Management Console

**[[AWS Management Console]]**: Web-based user interface for accessing your AWS account.

**Purpose**: Primary interface for managing AWS resources through a browser.

## AWS Config

**[[AWS Config]]**: Helps track resource inventory and changes.

**Benefit**: Audit resource configurations and compliance.

## Amazon CloudWatch

**[[Amazon CloudWatch]]**: Monitor resources and applications.

**Capabilities**: Collect metrics, logs, and set alarms for AWS resources.

**Use case**: Performance monitoring and operational insights.

## AWS Auto Scaling

**[[AWS Auto Scaling]]**: Scale multiple resources to meet demand.

**Benefit**: Automatically adjust capacity for optimal performance and cost.

## AWS Command Line Interface

**[[AWS CLI]]**: Unified tool to manage AWS services from the command line.

**Use case**: Automation, scripting, and programmatic access.

## AWS Trusted Advisor

**[[AWS Trusted Advisor]]**: Helps optimize performance and security.

**Checks**: Analyzes your environment for cost optimization, security, performance, and fault tolerance.

**Benefit**: Best practice recommendations.

## AWS Well-Architected Tool

**[[AWS Well-Architected Tool]]**: Helps review and improve your workloads.

**Framework**: Based on [[AWS Well-Architected Framework]] pillars.

**Use case**: Architectural best practices assessment.

## AWS CloudTrail

**[[AWS CloudTrail]]**: Tracks user activity and API usage.

**Capability**: Records API calls for auditing and compliance.

**Benefit**: Security analysis, resource change tracking, and troubleshooting.

---

# Review Questions

1. What are the three main elements of AWS Global Infrastructure? Describe each.
    
2. What is an AWS Region? How many Regions does AWS currently have worldwide?
    
3. Are resources in one AWS Region automatically replicated to other Regions? Who is responsible for cross-Region replication?
    
4. What are opt-in Regions? Give two examples. How do you enable them?
    
5. List and explain the four factors to consider when selecting an AWS Region for your workloads.
    
6. Calculate: If an EC2 t3.medium instance costs $0.0416/hour in Ohio and $0.0544/hour in Tokyo, how much would you save per year (8,760 hours) by choosing Ohio?
    
7. What is an Availability Zone? How many data centers typically make up an Availability Zone?
    
8. How are Availability Zones connected to each other? What type of replication does this enable?
    
9. Why should you deploy applications across multiple Availability Zones? What types of disasters can this protect against?
    
10. What is the most granular level at which customers can specify resource placement? Can customers directly choose specific data centers?
    
11. List three security measures AWS implements for data centers.
    
12. What are Points of Presence? Name four AWS services that use them.
    
13. What is the difference between edge locations and Regional edge caches? When are Regional edge caches used?
    
14. Explain the three key features of AWS Global Infrastructure (elastic/scalable, fault tolerant, high availability).
    
15. What is Amazon S3 used for? Give five example use cases.
    
16. Compare Amazon EBS and Amazon EFS. What type of storage is each, and when would you use one vs. the other?
    
17. What is Amazon S3 Glacier designed for? What is its durability rating?
    
18. What does Amazon EC2 provide? What is EC2 Auto Scaling?
    
19. Explain the difference between Amazon ECS and Amazon EKS. What container technologies does each support?
    
20. What is AWS Lambda? How is it billed differently from traditional EC2 instances?
    
21. What is the key difference between AWS Elastic Beanstalk and AWS Lambda in terms of server management?
    
22. Compare Amazon RDS and Amazon DynamoDB. When would you use a relational database vs. a NoSQL database?
    
23. How much faster is Amazon Aurora compared to standard MySQL and PostgreSQL databases?
    
24. What is Amazon Redshift used for? What scale of data can it analyze?
    
25. What does Amazon VPC enable you to do? Why is network isolation important?
    
26. What is the purpose of Elastic Load Balancing? What types of targets can it distribute traffic to?
    
27. Explain how Amazon CloudFront works as a CDN. How does it improve user experience?
    
28. What is the purpose of AWS Direct Connect? What are two benefits it provides?
    
29. What is AWS IAM used for? What can you create and manage with IAM?
    
30. Compare AWS Shield and AWS WAF. What types of attacks does each protect against?
    
31. What does AWS KMS enable you to do? Why is centralized key management important?
    
32. What is AWS CloudTrail used for? How does it help with security and compliance?
    
33. What is the difference between AWS Cost Explorer and AWS Budgets? How do they complement each other?
    
34. What does AWS Trusted Advisor check for? Name at least three categories it analyzes.
    
35. You're deploying a global web application that must comply with EU data protection laws, serve users in Europe and Asia with low latency, and minimize costs. Which Regions should you choose and why? Consider all four selection factors.
    

---

## Practical Scenarios

### Scenario 1: Multi-Region Architecture Design

Your company is launching a video streaming service. Users are in North America (60%), Europe (30%), and Asia (10%). Design a multi-Region architecture explaining:

- Which Regions you'd select and why
- How you'd handle content delivery
- Data replication strategy
- Compliance considerations

### Scenario 2: High Availability Design

Design a highly available web application architecture that can survive:

- Single server failure
- Availability Zone failure
- Regional disaster

Specify which AWS services you'd use and how you'd configure them across AZs and Regions.

### Scenario 3: Service Selection

Choose the appropriate AWS service for each use case: a) Host a WordPress blog with minimal management b) Store 500TB of security camera footage for 7 years c) Run a containerized microservices application d) Provide user authentication for a mobile app e) Cache website content closer to global users f) Run Python code in response to S3 upload events

### Scenario 4: Cost Optimization Across Regions

You're running identical EC2 workloads in 3 Regions:

- US East (Ohio): $0.0416/hour
- Europe (Frankfurt): $0.0504/hour
- Asia Pacific (Tokyo): $0.0544/hour

Each workload runs 24/7 (8,760 hours/year) on 10 instances. Calculate annual costs for each Region and total potential savings if you consolidated to Ohio (assuming latency and compliance allow).

### Scenario 5: Disaster Recovery Planning

Your company's primary infrastructure is in US East (N. Virginia). Design a disaster recovery strategy that:

- Uses multiple Availability Zones
- Has backup in another Region
- Includes database replication
- Maintains compliance with data residency

Specify which AWS services you'd use and the architecture pattern (pilot light, warm standby, or active-active).

---

## Architecture Diagrams Exercise

Draw or describe architectures for:

1. **Three-Tier Web Application**: Show web servers, application servers, and databases across multiple AZs with load balancing.
    
2. **Global Content Delivery**: Show origin servers, CloudFront distributions, edge locations, and user requests.
    
3. **Hybrid Cloud Architecture**: Show on-premises data center connected to AWS via Direct Connect, with resources in VPC.
    

---

## Matching Exercise

Match each AWS service to its category and primary use case:

**Services**: Lambda, DynamoDB, CloudFront, IAM, S3, EC2, Route 53, RDS, CloudWatch, VPC

**Categories**: Compute, Storage, Database, Networking, Security, Management

**Use Cases**: Serverless functions, Object storage, Virtual machines, NoSQL database, CDN, User access management, DNS routing, Relational database, Resource monitoring, Network isolation

---

## True/False Questions

1. Resources in one AWS Region are automatically replicated to other Regions.
2. All AWS Regions are enabled by default.
3. Availability Zones within a Region are typically separated by hundreds of kilometers.
4. You can specify which specific data center your EC2 instance runs in.
5. AWS Lambda charges you for compute time even when your code isn't running.
6. Amazon Aurora is compatible with both MySQL and PostgreSQL.
7. AWS Trusted Advisor only checks for security issues.
8. Amazon S3 Glacier is designed for frequently accessed data.
9. Elastic Load Balancing can distribute traffic to Lambda functions.
10. AWS CloudTrail monitors resource performance metrics.

---

## Practical Tags for Obsidian

#AWS #CloudInfrastructure #AWSRegions #AvailabilityZones #HighAvailability #AWSServices #CloudArchitecture #AWSCompute #AWSStorage #AWSDatabase #AWSNetworking #AWSSecurity #DisasterRecovery #FaultTolerance #CloudDesign #AWSCertification #CloudPractitioner #GlobalInfrastructure #EdgeLocations #AWSBestPractices #CloudResilience #MultiRegion #DataCenters #CloudScale #AWSFoundations
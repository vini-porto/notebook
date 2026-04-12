# AWS Compute Services

## AWS Compute Options

[[Amazon Web Services]] (AWS) provides multiple compute services, each designed for specific use cases. Think of compute services as different ways to run your applications in the cloud - some give you full control over virtual machines, others handle everything automatically, and some fall in between.

### The Four Main Categories

AWS compute services fall into four broad categories:

1. **[[Virtual Machines]] ([[Infrastructure as a Service]])** - You manage the operating system and applications
2. **[[Serverless Computing]]** - AWS manages everything; you just provide code
3. **[[Container-Based Services]]** - Run multiple isolated applications on shared infrastructure
4. **[[Platform as a Service]]** - AWS manages infrastructure; you focus on your application

# Core AWS Compute Services

## Virtual Machine Services

**[[Amazon EC2]]** (Elastic Compute Cloud) provides resizable virtual machines where you have full control. You choose the operating system, configure the server size, and manage the software.

**[[Amazon EC2#EC2 Auto Scaling|EC2 Auto Scaling]]** automatically adjusts the number of EC2 instances based on conditions you define. This ensures your application remains available during traffic spikes while minimizing costs during quiet periods.

**VMware Cloud on AWS** enables hybrid cloud deployments without requiring custom hardware, useful for organizations with existing VMware investments.

## Serverless Services

**[[AWS Lambda]]** is a serverless compute solution where you run code without managing servers. You only pay for the actual compute time your code uses - measured in milliseconds.

**AWS Serverless Application Repository** provides a catalog where you can discover, deploy, and publish serverless applications.

## Container Services

**[[Amazon ECS]]** (Elastic Container Service) orchestrates [[Docker]] containers, managing where and how containers run across your infrastructure.

**[[Amazon EKS]]** (Elastic Kubernetes Service) runs managed [[Kubernetes]] clusters, enabling you to use Kubernetes without operating the control plane yourself.

**[[Amazon ECR]]** (Elastic Container Registry) stores and retrieves Docker container images, integrating seamlessly with ECS and EKS.

**[[AWS Fargate]]** runs containers without requiring you to manage the underlying servers or clusters.

## Platform Services

**[[AWS Elastic Beanstalk]]** provides a simple platform for deploying web applications. You upload your code, and Beanstalk handles deployment, scaling, and infrastructure management.

**[[AWS Batch]]** runs batch computing jobs at any scale, automatically provisioning resources based on workload requirements.

**[[Amazon Lightsail]]** offers a simplified service for building applications or websites, ideal for simpler use cases.

## Hybrid and Edge Services

**[[AWS Outposts]]** enables you to run select AWS services in your on-premises data center, extending AWS infrastructure to your location.

# Choosing the Right Compute Service

## Key Considerations

The optimal compute service depends on your specific use case. Consider:

- **Legacy code requirements** - Does your application need specific operating systems or libraries?
- **Management overhead** - How much server management can your team handle?
- **Scalability needs** - Does your workload have predictable or variable traffic patterns?
- **Cost optimization** - What's the balance between control and operational efficiency?

## Best Practices

1. **Evaluate available options** - Don't default to familiar solutions; assess all possibilities
2. **Understand configuration options** - Each service offers different customization levels
3. **Collect metrics** - Use data to inform decisions, not assumptions
4. **Use elasticity** - Take advantage of cloud-native scaling capabilities
5. **Re-evaluate regularly** - As your application evolves, your compute needs may change

**Example**: You might start with [[Amazon EC2]] for familiarity, then migrate to containers with [[Amazon ECS]] for better resource utilization, and eventually move some functions to [[AWS Lambda]] for event-driven workloads.

# Amazon EC2 Deep Dive

## Understanding EC2

[[Amazon EC2]] (Elastic Compute Cloud) provides virtual machines in the cloud. The name breaks down as:

- **Elastic** - Easily increase or decrease server count and size
- **Compute** - Provides processing power (CPU) and memory (RAM)
- **Cloud** - Instances are hosted in AWS data centers

### Why EC2 Exists

Traditional on-premises servers have significant drawbacks:

- **High upfront costs** - Purchase hardware before knowing actual needs
- **Wasted capacity** - Servers must handle peak loads, sitting idle during normal times
- **Maintenance burden** - Buildings, staff, cooling, and power are expensive

EC2 solves these problems by providing virtual machines on-demand, where you pay only for what you use.

### Common Use Cases

EC2 instances can host virtually any server workload:

- Application servers and web servers
- Database servers
- Game servers and media servers
- File servers and mail servers
- Computing servers for data processing

## EC2 Instance Fundamentals

### Operating Systems

EC2 supports multiple operating systems:

- **Windows**: Windows Server 2008, 2012, 2016, 2019
- **Linux**: Red Hat, SUSE, Ubuntu, Amazon Linux

The operating system running on a virtual machine is called the **guest operating system**, distinguishing it from the **host operating system** that runs directly on physical hardware.

### Key Features

- Launch any number of instances of any size
- Deploy in any [[Availability Zones|Availability Zone]] worldwide
- Launch within minutes
- Control traffic with [[security groups]]
- Integrate with other AWS services

## Launching an EC2 Instance

### The Nine Essential Steps

When launching an EC2 instance, you make nine key decisions:

#### 1. Select an [[Amazon Machine Image]] (AMI)

An **AMI** is a template containing:

- **Root volume template** - Operating system and pre-installed software
- **Launch permissions** - Which AWS accounts can use it
- **Block device mapping** - Storage volumes to attach

**AMI Sources**:

- **Quick Start** - AWS-provided pre-built images (safest option)
- **My AMIs** - Custom AMIs you've created
- **[[AWS Marketplace]]** - Third-party software solutions
- **Community AMIs** - User-created (use cautiously; not verified by AWS)

**Creating Custom AMIs**: You can start with an existing AMI, customize an instance (your "golden instance"), then save it as a new AMI. This new AMI becomes your standardized starting point.

#### 2. Select an [[Instance Type]]

Instance types define the hardware resources (CPU, memory, storage, network) your instance receives.

**Naming Convention**:

- `t3.2xlarge` breaks down as:
    - `t` = family name
    - `3` = generation number (higher = newer, better value)
    - `2xlarge` = size (each step up typically doubles resources)

**Categories**:

- **General Purpose Instances** (T3) - Balanced resources, burstable performance
- **Compute Optimized Instances** (C5) - High-performance processors
- **Memory Optimized Instances** (R5) - Large amounts of RAM
- **Storage Optimized Instances** - High disk throughput
- **Accelerated Computing Instances** - GPU or specialized hardware

**Example**: A `t3.medium` provides baseline CPU performance with burst capability, ideal for web servers with variable traffic. A `c5.large` provides consistent high CPU, better for batch processing.

#### 3. Specify Network Settings

You must specify:

- **[[Amazon VPC]]** (Virtual Private Cloud) - Your isolated network
- **Subnet** - Which [[Availability Zones|Availability Zone]] to deploy in
- **Public IP** - Whether the instance needs internet accessibility

**Default VPC** instances automatically receive public IPs. **Non-default VPC** instances don't unless you specify otherwise or configure the subnet.

**Placement Groups** let you specify how instances are physically arranged to optimize for either high availability (spread apart) or low latency (placed close together).

#### 4. Attach [[IAM#IAM Roles|IAM Role]] (Optional but Recommended)

Never store AWS credentials on an EC2 instance. Instead, attach an **[[IAM#IAM Roles|IAM Role]]** that grants permissions.

**How it works**:

- Define what the application needs (e.g., read S3 buckets)
- Create an IAM role with those permissions
- Attach role to the instance
- Applications automatically receive temporary credentials

**Instance profiles** are containers for IAM roles - when using the console, AWS creates matching instance profiles automatically.

#### 5. User Data Script (Optional)

**User data** scripts automate instance configuration at launch. Use them to:

- Install software updates
- Download and install applications
- Configure settings
- Fetch license keys

**Example Linux script**:

```bash
#!/bin/bash
yum update -y
yum install wget -y
```

For Windows, use batch commands or [[PowerShell]] syntax.

#### 6. Specify Storage

Configure the volumes (disks) attached to your instance:

**[[Amazon EBS]]** (Elastic Block Store):

- Persistent block storage
- Independent of instance lifecycle
- Can change size or type without downtime
- Multiple volume types for different performance needs

**EC2 Instance Store**:

- Temporary storage physically attached to host
- High performance but data lost if instance stops
- Good for buffers, caches, temporary data

**[[Amazon EFS]]** (Elastic File System):

- Managed NFS file system
- Shared across multiple instances
- Scales automatically

**[[Amazon S3]]**:

- Object storage (not block storage)
- For files, backups, archives
- Access via API, not mounted as disk

#### 7. Add Tags

**[[Tags]]** are key-value pairs that add metadata to resources.

**Example tags**:

- `Name: Web Server 01`
- `Environment: Production`
- `Owner: Engineering Team`
- `Cost Center: Marketing`

**Benefits**:

- Organize resources
- Track costs by project or team
- Search and filter instances
- Automate management tasks

**Important**: Tag keys are case-sensitive. `Name` and `name` are different tags.

#### 8. Configure [[Security Group]] Settings

Security groups act as **virtual firewalls** controlling network traffic.

**How they work**:

- Define rules allowing specific traffic
- Rules specify: protocol (TCP/UDP), port, and source
- Multiple security groups can be attached to one instance
- Rules evaluated together (most permissive wins)
- Changes apply immediately to associated instances

**Example rule**: Allow SSH (port 22) from "My IP" only

- Protocol: TCP
- Port: 22
- Source: Your current IP address

**Default behavior**: All outbound traffic allowed, all inbound traffic blocked unless explicitly permitted.

#### 9. Create or Select Key Pair

**Key pairs** use public-key cryptography for secure login:

- **Public key** - Stored on the instance
- **Private key** - You download and keep secure (only chance to download!)

**For Windows**: Use private key to decrypt administrator password, then connect via [[RDP]] (Remote Desktop Protocol)

**For Linux**: Use private key to establish [[SSH]] connection

**Critical**: Store your private key securely. If lost, you cannot access the instance.

## Managing EC2 Instances

### Instance Lifecycle States

Understanding the **EC2 instance lifecycle** helps manage instances effectively:

- **Pending** - Instance is starting up, booting, deploying
- **Running** - Instance is fully operational and accessible
- **Rebooting** - Restarting (stays on same host, keeps IP and data)
- **Stopping** - Transition state before stopped (EBS-backed only)
- **Stopped** - Instance is shut down (no compute charges, but storage charges apply)
- **Shutting down** - Transition before termination
- **Terminated** - Instance is permanently deleted (brief visibility in console, then gone)

**Key insight**: Stopped instances can be restarted but may move to different physical hosts. Terminated instances cannot be recovered.

### IP Address Management

**Public IP addresses**:

- Assigned from AWS pool
- Changes when instance stops/starts
- Released back to pool when not in use

**Elastic IP addresses**:

- Static public IPs you can keep
- Remains with your account until released
- Can be reassociated to different instances
- Limited to 5 per Region (soft limit, can request increase)

**When to use Elastic IPs**: When you need a consistent public IP address (e.g., for DNS records, whitelisting).

### Instance Metadata

**EC2 instance metadata** provides information about the running instance, accessible from within the instance:

- Access URL: `http://169.254.169.254/latest/meta-data/`
- Available information: public/private IPs, instance ID, security groups, Region, Availability Zone
- User data accessible at: `http://169.254.169.254/latest/user-data`

**Use cases**: Configuration scripts can read metadata to dynamically configure applications based on their environment.

### Monitoring with CloudWatch

**[[Amazon CloudWatch]]** monitors [[Amazon EC2|EC2]] instances automatically:

- **Basic monitoring** (default): 5-minute intervals, free
- **Detailed monitoring** (optional): 1-minute intervals, additional cost
- **Available metrics**: CPU utilization, disk I/O, network traffic
- **Historical data**: Retained for 15 months

**Note**: RAM metrics require additional configuration (not provided by default).

## Launching Instances Programmatically

### Using the [[AWS CLI]]

You can launch instances using command-line tools:

```bash
aws ec2 run-instances \
  --image-id ami-1a2b3c4d \
  --count 1 \
  --instance-type c3.large \
  --key-name MyKeyPair \
  --security-groups MySecurityGroup \
  --region us-east-1
```

**Benefits**:

- Automation and scripting
- Integration with CI/CD pipelines
- Consistent, repeatable deployments

**Alternative approaches**: Use [[AWS SDKs]] in various programming languages (Python, Java, Node.js, etc.) for deeper integration.

# EC2 Cost Optimization

## Understanding EC2 Pricing Models

AWS offers multiple pricing models to optimize costs:

### [[Amazon EC2#Instance Types|On-Demand Instances]]

**Characteristics**:

- Pay by the hour or second (Linux/Ubuntu only)
- No upfront commitment
- No long-term contract

**Best for**:

- Short-term, irregular workloads
- Testing and development
- Applications with unpredictable traffic
- First-time workloads

**AWS Free Tier eligible**: Yes

### [[Amazon EC2#Instance Types|Reserved Instances]]


**Characteristics**:

- 1-year or 3-year commitment
- Significant discount (up to 75% vs On-Demand)
- Fixed pricing for the term

**Best for**:

- Steady-state workloads
- Predictable usage patterns
- Long-running applications (databases, always-on services)

**Example**: A database server running 24/7 for multiple years

### [[Amazon EC2#Instance Types|Scheduled Reserved Instances]]

**Characteristics**:

- Reserve capacity for specific time windows
- Daily, weekly, or monthly patterns
- 1-year term
- Pay for scheduled time whether used or not

**Best for**:

- Predictable periodic workloads
- Batch processing with known schedules

### Spot Instances

**Characteristics**:

- Bid on unused EC2 capacity
- Up to 90% discount vs On-Demand
- Can be interrupted with 2-minute warning
- Price fluctuates based on supply and demand

**Best for**:

- Fault-tolerant applications
- Flexible start/end times
- Big data processing
- CI/CD environments
- Applications that save state frequently

**Not suitable for**: Applications requiring guaranteed availability

### [[Amazon EC2#Purchasing Options — How You Pay for EC2|Dedicated Hosts]]

**Characteristics**:

- Physical server dedicated to your use
- Full visibility and control over physical resources
- Bring your own licenses (per-socket, per-core, per-VM)

**Best for**:

- Regulatory requirements
- Compliance needs
- Existing software licenses

### [[Amazon EC2#Purchasing Options — How You Pay for EC2|Dedicated Instances]]

**Characteristics**:

- Run in VPC on hardware dedicated to single customer
- Physically isolated from other AWS accounts
- May share hardware with other instances in same account

**Difference from Dedicated Hosts**: Less visibility into physical infrastructure, but still isolated at hardware level.

## The Four Pillars of Cost Optimization

### Pillar 1: Right-Sizing

**Right-sizing** means choosing the smallest instance that meets performance requirements.

**Approach**:

1. Start with an educated guess
2. Monitor CPU, RAM, storage, and network utilization using [[Amazon CloudWatch]]
3. Identify over-provisioned instances
4. Downsize where possible
5. Test to ensure performance requirements still met

**Example**: If monitoring shows a `t3.large` instance consistently uses only 20% CPU and 30% RAM, consider downsizing to `t3.medium` - you'll cut costs in half while maintaining performance.

**Tools**: Use CloudWatch metrics and custom metrics to gather detailed utilization data.

### Pillar 2: Increase Elasticity

**[[Elasticity]]** means using resources only when needed.

**Strategies**:

**For non-production environments**:

- Stop instances outside business hours
- Example: Development servers running 8 AM - 6 PM (10 hours) vs 24 hours = 58% cost reduction

**For production workloads**:

- Use Auto Scaling to match capacity to demand
- Scale horizontally (more instances) during peaks
- Scale down during quiet periods

**Target**: 20-30% of instances as On-Demand or Spot for maximum elasticity

**Analogy**: Like turning off lights when leaving a room - you only pay for electricity when lights are on.

### Pillar 3: Optimal Pricing Model

Combine multiple pricing models based on workload patterns:

**Recommended mix**:

- **Reserved Instances**: Baseline capacity (always-running workloads)
- **On-Demand Instances**: Variable capacity beyond baseline
- **Spot Instances**: Flexible, interruptible workloads

**Alternative architectures**: Consider [[AWS Lambda]] for event-driven workloads - no charges when code isn't running.

### Pillar 4: Optimize Storage Choices

**Strategies**:

1. **Resize volumes**: If you provisioned 500 GB but only use 20 GB, shrink the volume
2. **Choose appropriate volume types**:
    - EBS gp2 (General Purpose SSD): Default, balanced
    - EBS st1 (Throughput Optimized HDD): Half the cost, good for sequential access
3. **Delete unused snapshots**: Review and remove old backups
4. **Use appropriate storage service**:
    - EBS for block storage (databases)
    - [[Amazon S3]] for object storage (files, backups) - often cheaper
    - Implement lifecycle policies to move data to cheaper tiers

## Continuous Improvement

Cost optimization is ongoing, not one-time:

**Key practices**:

1. **Implement tagging**: Tag resources by project, team, environment
2. **Use [[AWS Cost Explorer]]**: Visualize spending patterns over time
3. **Review regularly**: Monthly cost reviews identify optimization opportunities
4. **Leverage AWS Trusted Advisor**: Automated recommendations for cost savings
5. **Assign ownership**: Designate individuals or teams responsible for cost optimization
6. **Architect for cost**: Make cost a design consideration from the start

# Container Services

## Understanding Containers

**[[Containers]]** provide operating system virtualization, running applications and dependencies in isolated processes.

### Key Characteristics

- **Smaller than VMs**: Don't include entire OS, share host OS kernel
- **Fast startup**: Launch in milliseconds vs minutes for VMs
- **Resource efficient**: Multiple containers share single OS
- **Consistent**: Same environment everywhere (development, testing, production)
- **Portable**: Run on laptops, VMs, EC2 instances, bare metal

### What Containers Include

Each container packages:

- Application code
- Runtime environment
- System libraries
- Dependencies
- Configuration files

**What they DON'T include**: Full operating system (they share the host OS kernel)


## Containers vs Virtual Machines

### Virtual Machine Approach

**Three separate EC2 instances**, each running:

- Own guest operating system
- One application per VM
- Complete isolation

**Resource usage**: Three full operating systems consuming memory and storage

### Container Approach

**One EC2 instance** running:

- Single guest operating system
- Docker engine
- Three containers (one per application)

**Resource usage**: One operating system supporting all three applications

**Key advantage**: Dramatically better resource utilization - one large EC2 instance running hundreds of containers vs hundreds of EC2 instances each running one application.


## Docker Overview

**[[Docker]]** is a software platform for building, shipping, and running containers.

### What Docker Provides

- **Container runtime**: Execute and manage containers
- **Simple commands**: build, start, stop containers
- **Image management**: Create and distribute container images
- **Portability**: Same container runs anywhere Docker is installed

### When to Use Docker

- Standardize environments across development, testing, production
- Reduce conflicts between language versions or dependencies
- Enable microservices architectures
- Improve portability of data processing workloads
- Efficiently utilize server resources

### Docker Components

- **Docker image**: Template defining container contents
- **Docker container**: Running instance of an image
- **Docker engine**: Software managing container lifecycle

## Amazon ECS (Elastic Container Service)

### Why Use ECS?

While you could manually install Docker on EC2 instances, [[Amazon ECS]] simplifies container management at scale.

### Core Concepts

**Task Definition**:

- JSON/text file describing your container configuration
- Specifies: which images, how many containers (1-10), ports, data volumes
- Think of it as a blueprint for your application

**ECS Task**:

- Running instantiation of a task definition
- One task might run 1-10 containers (as defined in task definition)
- You specify how many tasks to run

**ECS Cluster**:

- Logical grouping of EC2 instances (with EC2 launch type)
- Each instance runs the **ECS container agent**
- ECS scheduler places tasks across cluster based on resource availability

### Key Capabilities

- Launch tens of thousands of containers in seconds
- Monitor container deployment status
- Manage cluster state
- Schedule containers using built-in or third-party schedulers
- Support for Spot and Reserved Instances

## ECS Cluster Options

When creating an ECS cluster, choose from three options:

### 1. Networking Only Cluster ([[AWS Fargate]])

- **AWS manages infrastructure completely**
- You specify: container image, CPU/memory requirements, networking, IAM policies
- You DON'T manage: EC2 instances, scaling, cluster optimization
- **Best for**: Focus on application, not infrastructure

### 2. EC2 Linux + Networking

- **You manage EC2 instances**
- Choose: instance types, On-Demand vs Spot, scaling policies
- **More control** over underlying infrastructure
- ECS tracks resources and finds optimal placement

### 3. EC2 Windows + Networking

- Same as EC2 Linux but for Windows containers

**Decision factor**: Fargate removes infrastructure management burden; EC2 launch type provides more control and potentially lower costs for large, consistent workloads.

## Kubernetes and Amazon EKS

### What is [[Kubernetes]]?

**Kubernetes** (K8s) is open-source container orchestration software.

**Key features**:

- Works with multiple container technologies (not just Docker)
- Manages clusters of compute nodes
- Runs containers in logical groups called [[pods]]
- Provides service discovery and load balancing
- Portable across cloud and on-premises

### Kubernetes Architecture

- **Cluster**: Group of compute instances (nodes)
- **Node**: Individual compute instance running containers
- **Pod**: Smallest deployable unit (one or more containers)
- **Service**: Stable network endpoint for accessing pods

**Benefits**: Deploy anywhere using same tooling - on-premises, AWS, multi-cloud.

### Amazon EKS (Elastic Kubernetes Service)

**[[Amazon EKS]]** is a managed Kubernetes service.

**What EKS manages**:

- Kubernetes control plane
- Availability and scalability of control plane nodes
- Automatic detection and replacement of unhealthy control plane nodes
- Patching and updates

**What you manage**:

- Worker nodes (EC2 instances running your containers)
- Deploying applications
- Kubernetes configuration

**Integration**: Works with AWS services (Application Load Balancer, IAM, VPC)

### ECS vs EKS

Both orchestrate Docker containers - why two services?

- **ECS**: AWS-specific, simpler to learn, tighter AWS integration
- **EKS**: Industry-standard Kubernetes, portable across environments, larger ecosystem

**Choose based on**: Team expertise, portability requirements, existing investments.

## Amazon ECR (Elastic Container Registry)

**[[Amazon ECR]]** is a fully managed Docker registry.

### What It Does

- **Store** Docker container images securely
- **Manage** image versions and tags
- **Deploy** images to ECS, EKS, or anywhere

### Key Features

- Integrated with ECS and EKS (specify ECR repository in task definition)
- Compatible with Docker CLI and tools
- HTTPS transfer for security
- Automatic encryption at rest (using S3)
- Fine-grained access control using IAM

**Workflow**: Build image locally → Push to ECR → Deploy to ECS/EKS from ECR

# AWS Lambda

## Understanding Serverless Computing

**[[Serverless Computing]]** doesn't mean "no servers" - it means you don't think about servers.

### What is AWS Lambda?

**[[AWS Lambda]]** is an event-driven, serverless compute service.

**Key concept**: Upload code, configure trigger, Lambda runs code when triggered. You pay only for execution time (billed in 100ms increments).

### The Lambda Function

A **[[Lambda function]]** is your custom code packaged as an AWS resource.

**Components**:

- Your code (Python, Java, Node.js, C#, Go, PowerShell, Ruby)
- Any libraries or dependencies
- Configuration (memory, timeout, permissions)

## Benefits of Lambda

### 1. No Infrastructure Management

- No servers to provision, patch, or manage
- Automatic deployment
- Built-in fault tolerance across multiple Availability Zones
- No maintenance windows

### 2. Language Flexibility

Supports multiple languages without learning new frameworks:

- Use native or third-party libraries
- Leverage existing skills
- Bring your own code

### 3. Automatic Scaling

- Scales automatically from few requests per day to thousands per second
- No capacity planning needed
- Handles variable loads seamlessly

### 4. Cost Efficiency

Pay-per-use pricing:

- No charges when code isn't running
- Billed in 100ms increments
- Free tier: 1 million requests/month

### 5. Orchestration with [[AWS Step Functions]]

Build complex workflows:

- Chain multiple Lambda functions
- Implement error handling
- Create sequential, parallel, or branching logic
- Build long-running processes

## Lambda Event Sources

Lambda functions are triggered by **event sources**:

### Direct Invocation (Asynchronous)

Services that directly invoke Lambda:

- **[[Amazon S3]]**: Object upload/delete events
- **[[Amazon SNS]]**: Message publication
- **[[Amazon CloudWatch#Events]]**: Scheduled or rule-based triggers

### Polling (Lambda pulls events)

Lambda polls these services:

- **[[Amazon SQS]]**: Queue messages
- **[[Amazon DynamoDB]]**: Stream records
- **[[Amazon Kinesis]]**: Data streams

### Synchronous Invocation

Direct calls to Lambda:

- **[[Application Load Balancer]]**: HTTP(S) requests
- **[[Amazon API Gateway]]**: REST API calls
- **AWS SDK/CLI**: Manual invocation

### Custom Applications

Invoke Lambda directly from:

- Mobile apps
- Web applications
- IoT devices

## Configuring Lambda Functions

### Basic Configuration

1. **Function name**: Identifier for your function
2. **Runtime**: Language and version (e.g., Python 3.11, Node.js 18)
3. **Execution role**: [[IAM#Roles|IAM role]] granting permissions to AWS services

### Detailed Configuration

**Trigger**: Define what events invoke the function

**Code**:

- Use inline code editor for simple functions
- Upload .zip file for complex functions with dependencies
- Use container images (up to 10 GB) for large dependencies

**Memory allocation**: 128 MB to 10,240 MB

- More memory = more CPU power
- Affects pricing

**Timeout**: 1 second to 15 minutes maximum

- Function terminated if timeout exceeded

**Environment variables**: Pass configuration without hardcoding

**VPC settings**: Optionally run in specific VPC to access private resources

**Lambda layers**: Reusable code packages (libraries, dependencies)

- Share code between functions
- Keep deployment packages small
- Maximum 5 layers per function

## Lambda Use Cases

### Example 1: Scheduled Start/Stop EC2 Instances

**Problem**: Reduce costs by stopping EC2 instances outside business hours

**Solution**:

1. CloudWatch Event scheduled for 10 PM → Triggers Lambda → Stops EC2 instances
2. CloudWatch Event scheduled for 5 AM → Triggers Lambda → Starts EC2 instances

**Components**:

- Two CloudWatch Events (schedules)
- Two Lambda functions (start and stop logic)
- IAM role with EC2 permissions

### Example 2: Automatic Thumbnail Generation

**Problem**: Create thumbnails for images uploaded to S3

**Solution**:

1. User uploads image to S3 source bucket
2. S3 object-created event triggers Lambda
3. Lambda reads image from source bucket
4. Lambda creates thumbnail using image processing library
5. Lambda saves thumbnail to target bucket

**Components**:

- S3 source and target buckets
- Lambda function with image processing code
- IAM role with S3 read/write permissions

## Lambda Limitations

Understanding **Lambda quotas** helps avoid deployment issues:

### Compute Limits

- **Memory**: 128 MB to 10,240 MB
- **Timeout**: 1 second to 15 minutes (hard limit)
- **Concurrent executions**: 1,000 per Region (soft limit)

### Package Limits

- **Deployment package** (.zip): 250 MB
- **Container image**: 10 GB
- **Lambda layers**: Up to 5 per function

### Workarounds

- Use layers to share code and reduce package size
- Use container images for larger dependencies
- Request limit increases for soft limits

**Hard limits** cannot be increased. **Soft limits** can potentially be raised through support tickets with justification.

# AWS Elastic Beanstalk

## Understanding Platform as a Service

**[[AWS Elastic Beanstalk]]** is a [[Platform as a Service]] (PaaS) offering.

**Core concept**: You provide code, Beanstalk handles everything else.

### What Elastic Beanstalk Manages

- Capacity provisioning
- Load balancing
- Automatic scaling
- Application health monitoring
- Operating system updates
- Platform updates

### What You Control

- Select EC2 instance type
- Choose database options
- Configure scaling settings
- Enable HTTPS
- Access server logs
- Access underlying resources

**Key point**: Full control available when needed, but automation handles routine tasks.

## Deploying with Elastic Beanstalk

### Supported Platforms

**Languages and frameworks**:

- Java (Apache Tomcat)
- .NET (IIS)
- Node.js (NGINX or Apache)
- PHP (Apache)
- Python (Apache)
- Ruby (Passenger or Puma)
- Go
- Docker

### Deployment Methods

1. **AWS Management Console**: Visual, wizard-based
2. **[[AWS CLI]]**: Command-line automation
3. **IDE integration**: Visual Studio, Eclipse
4. **Git repository**: Direct deployment from version control

**Speed**: Simple deployment possible in as few as 6 clicks accepting defaults.

## Benefits of Elastic Beanstalk

### 1. Fast and Simple

- Quick to start using
- Upload application through console, Git, or IDE
- Automatic deployment and configuration
- Perfect for getting applications running quickly

### 2. Developer Productivity

**Focus on code**, not infrastructure:

- No server configuration
- No database setup
- No load balancer management
- No network configuration

**Platform maintenance**: AWS handles OS patches and updates automatically.

### 3. Scalability

- Automatic scaling based on demand
- Handle traffic spikes without over-provisioning
- Use CPU metrics to trigger scaling
- Minimize costs during low traffic

**Flexibility**: Scale application up or down based on actual needs.

### 4. Complete Control

**Freedom to customize**:

- Choose optimal EC2 instance types
- Access underlying AWS resources
- Take manual control when needed
- Seamless transition from automated to manual management

**Balance**: Convenience of automation with option for detailed control.

## Pricing

**Elastic Beanstalk itself is free** - no additional charge for the service.

**You pay for**:

- EC2 instances running your application
- S3 storage for application versions
- Any other AWS resources used (databases, load balancers, etc.)

**Pay-as-you-go**: Only pay for what you use, no upfront commitments.

# Tags

#aws #cloud-computing #compute-services #ec2 #lambda #containers #serverless #cost-optimization #ecs #elastic-beanstalk #cloud-computing 
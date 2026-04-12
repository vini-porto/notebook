
![[Cloud Computing#What is Cloud Computing]]
# Infrastructure as Software

Cloud computing fundamentally changes how we think about IT infrastructure.

## Traditional Computing Model

Traditional Computing Model treats infrastructure as hardware.

**Characteristics of hardware solutions**:

- Require physical space in data centers
- Require staff to manage and maintain
- Require physical security measures
- Require extensive planning
- Require significant **[[Capital Expenditure]]** (upfront costs)
- Have long **[[Hardware Procurement Cycles]]**—acquiring, provisioning, and maintaining takes weeks or months
- Require provisioning capacity by guessing theoretical maximum peaks

## Cloud Computing Model

![[Cloud Computing#Core Characteristics of Cloud Computing]]

## Service Models

![[Cloud Computing#Cloud Service Models]]

# Cloud Computing Deployment Models

## Public Cloud

![[Cloud Computing#Public Cloud]]

## Private Cloud

Deploys resources on-premises using virtualization and resource management tools.

![[Cloud Computing#Private Cloud]]

## Hybrid Cloud

**Most common method**: Between the cloud and existing on-premises infrastructure.

**Purpose**: Enables organizations to extend and grow their infrastructure into the cloud while connecting cloud resources to internal systems.

![[Cloud Computing#Hybrid Cloud]]

## Multi-Cloud

![[Cloud Computing#Multi-Cloud]]

# Six Advantages of Cloud Computing

1. **Capital Expenses** (CapEx): Funds used to acquire, upgrade, and maintain physical assets like property, buildings, or equipment. 
	- **Cloud solution**: **Variable Expense**—pay only when you consume resources and only for the amount you consume.

2. **[[Economies of Scale]]**: By aggregating usage from hundreds of thousands of customers, providers like AWS achieve higher economies of scale.

3. You either have expensive idle resources (over-provisioned) or insufficient capacity (under-provisioned). 
	- **Cloud solution**: Access as much or as little as you need and **[[Elastic Scaling]]**—scale up and down with only a few minutes' notice.

4. New IT resources are available in **minutes**. Dramatic increase in **[[Organizational Agility]]**—the cost and time to experiment and develop are significantly lower.

5. Focus on projects that **differentiate your business** instead of infrastructure management.

6. Deploy applications in multiple **[[AWS Regions]]** around the world with just a few clicks.

# Categories of AWS Services

- **Compute**: Processing power ([[Amazon EC2]], [[AWS Lambda]])
- **Storage**: Data storage ([[Amazon S3]], [[Amazon EBS]])
- **Database**: Data management ([[Amazon RDS]], [[Amazon DynamoDB]])
- **Networking**: Connectivity ([[Amazon VPC]], [[Amazon CloudFront]])
- **Security**: Access control ([[IAM]], [[AWS WAF]])
- **Management**: Monitoring and automation ([[Amazon CloudWatch]])

### Choosing the Right AWS Service

- **[[Amazon EC2]]**: Virtual servers in the cloud
- **[[AWS Lambda]]**: Serverless computing (run code without managing servers)
- **[[AWS Elastic Beanstalk]]**: Platform for deploying web applications
- **[[Amazon Lightsail]]**: Simple virtual private servers
- **[[AWS Batch]]**: Batch computing jobs
- **[[AWS Outposts]]**: AWS infrastructure on-premises
- **[[Amazon ECS]]**: Container orchestration service
- **[[Amazon EKS]]**: Managed Kubernetes service
- **[[AWS Fargate]]**: Serverless containers
- **[[VMware Cloud on AWS]]**: VMware workloads on AWS

## Three Ways to Interact with AWS

1. **[[AWS Management Console]]**: A web-based graphical interface.

2. **[[AWS CLI]]**: A suite of command-line utilities.

	- Can be launched from command scripts
	- Supported on Linux, macOS, and Microsoft Windows
	- Ideal for automation and scripting

3. Software Development Kits **[[AWS SDKs]]**: Packages for accessing AWS in popular programming languages.

	- Integrate AWS into existing applications
	- Create applications that deploy and monitor complex systems entirely through code
	- Programmatic access to AWS services

# AWS Cloud Adoption Framework (AWS CAF)

![[AWS Cloud Adoption Framework#What Is the AWS CAF]]

## Why AWS CAF Exist

![[AWS Cloud Adoption Framework#Why CAF Exists]]

## How it Works

![[AWS Cloud Adoption Framework#How AWS CAF Works]]

## The Six Perspectives Explained

### Business Perspective

**Business Perspective** stakeholders:

- Business managers
- Finance managers
- Budget owners
- Strategy stakeholders

**Purpose**: Create a strong **business case for cloud adoption** and prioritize cloud adoption initiatives.

### People Perspective

**People Perspective** stakeholders:

- Human resources
- Staffing managers
- People managers

**Purpose**: Evaluate organizational structures, roles, skill requirements, and process requirements.

**Actions**:

- Perform analysis of needs and gaps
- Prioritize training, staffing, and organizational changes
- Build an **Agile Organization**

### Governance Perspective

**Governance Perspective** stakeholders:

- Chief Information Officer (CIO)
- Program managers
- Enterprise architects
- Business analysts
- Portfolio managers

**Purpose**: Focus on skills and processes needed to align IT strategy with business strategy.

**Goal**: Maximize business value of IT investment and minimize business risks.

### Platform Perspective

**Platform Perspective** stakeholders:

- Chief Technology Officer (CTO)
- IT managers
- Solutions architects

**Purpose**: Understand and communicate the nature of IT systems and their relationships using architectural dimensions and models.

**Focus**:

- Describe target state environment architecture in detail
- Implement new solutions on the cloud
- Migrate on-premises workloads to the cloud

**Includes**: Principles and patterns for implementation and migration.

### Security Perspective

**Security Perspective** stakeholders:

- Chief Information Security Officer (CISO)
- IT security managers
- IT security analysts

**Purpose**: Ensure the organization meets security objectives.

**Focus areas**:

- **Visibility**: Monitor and understand security posture
- **Auditability**: Track and record security events
- **Control**: Enforce security policies
- **Agility**: Respond quickly to security threats

**Actions**: Structure selection and implementation of security controls that meet organizational needs.

### Operations Perspective

**Operations Perspective** stakeholders:

- IT operations managers
- IT support managers

**Purpose**: Define how business is conducted day-to-day, quarter-to-quarter, and year-to-year.

**Focus**: Align with and support business operations.

**Actions**:

- Define current operating procedures
- Identify process changes needed for successful cloud adoption
- Plan training requirements

# Tags

#cloud-computing #AWS #CloudAdoption #AWSServices #CloudEconomics #AWSCAF #CloudStrategy #CloudMigration #CostOptimization #CloudSecurity #AWSBestPractices


# AWS Pricing Model

There are three fundamental drivers of cost with AWS:

1. **[[Compute]]**: Processing power (CPU, memory)
2. **[[Storage]]**: Data storage (databases, file storage, backups)
3. **[[Outbound Data Transfer]]**: Data leaving AWS

>[!NOTE] Important notes
>- **No charge** for inbound data transfer in most cases
>- **No charge** for data transfer between AWS services within the same [[AWS Regions|AWS Region]]
>- **Outbound data transfer** is aggregated across services and billed as "AWS Data Transfer Out"
>- Always verify data transfer rates before using a service—there are exceptions

## Core AWS Pricing Philosophy

- Start or stop using a product at any time
- No long-term contracts required
- Pay at the end of each month for actual usage
- Utility-Style Pricing 

# The Four Pillars of AWS Pricing

## 1. Pay for What You Use

**Benefits**:

- Lower Variable Costs
- No need to build costly infrastructure
- No purchasing servers or software licenses
- No leasing facilities
- Quickly adapt to changing business needs
- Redirect focus to innovation and invention
- Pay only for what you use and for as long as you need it

**Availability**: All AWS services are available on-demand with no long-term contracts and no complex licensing dependencies.

## 2. Pay Less When You Reserve

For certain services like [[Amazon EC2]] and [[Amazon RDS]], you can invest in Reserved Capacity.

**Savings**: Up to **75% discount** over equivalent on-demand capacity.

### Three Reserved Instance Options:

**All Upfront Reserved Instance** (AURI):

- Pay everything upfront
- Receive the largest discount
- Best for cost minimizations

**Partial Upfront Reserved Instance** (PURI):

- Pay some upfront, rest over time
- Lower discount than AURI
- Balance between upfront cost and savings

**No Upfront Reserved Instance** (NURI):

- Pay nothing upfront
- Smallest discount
- Frees capital for other projects

>[!Tip] Benefits: 
>Minimize risks, predictably manage budgets, comply with policies requiring longer-term commitments.

## 3. Pay Less By Using More

**Volume-Based Discounts**: Get better pricing as your usage increases.

**Example**: [[Amazon S3]] uses Tiered Pricing:

- Pay less per GB when you use more
- Data transfer IN is always free
- Storage costs decrease based on your needs

>**Economy of scale benefit**: As your [[AWS]] usage increases, you benefit from economies of scale that help you:

- Increase adoption
- Keep costs under control
- Access better pricing tiers

>**Strategic advantage**: The AWS storage services portfolio offers options to lower pricing based on:

- How frequently you access data
- Performance requirements for retrieval

Choose the right combination of storage solutions to reduce costs while preserving performance, security, and durability.

## 4. Pay Less as AWS Grows

AWS constantly works to reduce costs:

- Lower data center hardware costs
- Improved operational efficiencies
- Reduced power consumption
- Lower cost of doing business

> [!NOTE]
> Substantial economies of scale = savings passed to customers as lower pricing.

## Custom Pricing

AWS recognizes that every customer has different needs.

Custom Pricing is available for:

- High-volume projects
- Unique requirements
- Enterprise-level deployments

# AWS Free Tier

**[[AWS Free Tier]]**: Helps new AWS customers get started in the cloud.

>[!NOTE] Availability: 
>New customers for up to 1 year

What's included:

- Free [[Amazon EC2]] T2 micro instance for a year
- Free usage tier for [[Amazon S3]]
- Free usage tier for [[Amazon EBS]]
- Elastic Load Balancing
- AWS data transfer
- Other AWS services

# Services with No Charge

## Amazon Virtual Private Cloud (VPC)

**[[Amazon VPC]]**: Provision a logically isolated section of the AWS Cloud where you launch AWS resources in a virtual network you define.
![[Amazon VPC#Why Does VPC Exist?]]

## AWS Identity and Access Management (IAM)

**[[IAM]]**: Controls users' access to AWS services and resources.

## Consolidated Billing

A billing feature in [[AWS Organizations]] to consolidate payment for multiple AWS accounts.

**Provides**:

- One bill for multiple accounts
- Easy tracking of each account's charges
- Decreased charges from volume pricing discounts from combined usage
- Tiered benefits across all consolidated accounts

## AWS Elastic Beanstalk

**[[AWS Elastic Beanstalk]]**: Quickly deploy and manage applications in the AWS Cloud.

Obs: you pay only for the AWS resources your applications use

## AWS CloudFormation

**[[AWS CloudFormation]]**: Create a collection of related AWS resources and provision them in an orderly and predictable fashion.

Obs: you pay only for the resources created

## Auto Scaling

**Auto Scaling**: Automatically adds or removes resources according to conditions you define.

**Benefits**:

- Resources increase seamlessly during demand spikes
- Resources decrease automatically during demand lulls
- Maintains performance while minimizing cost

**Cost**: Free (you pay for the scaled resources, like EC2 instances)

## AWS OpsWorks

**[[AWS OpsWorks]]**: Application management service for deploying and operating applications of all sizes.

> [!Warning]
> You pay for underlying resources

# Total Cost of Ownership (TCO)

## On-Premises Infrastructure

On-Premises Infrastructure: Installed locally on a company's computers and servers.

**Characteristics**:

- Several Fixed Costs (also called Capital Expenses)
- Includes: facilities, hardware, licenses, maintenance staff
- **Scaling up**: Expensive and time-consuming
- **Scaling down**: Does NOT reduce fixed costs

## Cloud Infrastructure

Purchased from a service provider who builds and maintains facilities, hardware, and staff.

**Characteristics**:

- Customer pays for what is used
- **Scaling up or down**: Simple and flexible
- **Costs**: Easy to estimate based on service use
- Consumption Based Costs

# What is Total Cost of Ownership?

Total Cost of Ownership (TCO): A financial estimate to help buyers and owners determine direct and indirect costs of a product or system.

**Includes**:

- Cost of the service itself
- ALL costs associated with owning the service

**Purpose**: Compare costs of running infrastructure in different environments:

- On-premises or colocation facility
- Cloud-based infrastructure

**Use cases**:

- Budgeting purposes
- Building business cases for optimal deployment decisions

## TCO Considerations

>[!NOTE] Server Costs
>- Hardware
>- Software
>- Facilities to house equipment

>[!NOTE] Storage Costs
>- Hardware
>- Administration
>- Facilities

>[!NOTE] Network Costs
>- Hardware
>- Administration
>- Facilities

>[!NOTE] IT Labor Costs
>- Required to administer the entire solution

## Cloud vs. On-Premises Cost Assessment

**Cloud costs**: Most costs are upfront and readily calculated.

- Transparent pricing based on usage metrics (RAM, storage, bandwidth)
- Pricing frequently fixed per unit of time
- Customers gain certainty and can calculate costs based on usage estimates

**On-premises costs**: Sometimes difficult to determine, must account for:

- **Direct costs**: Power, floor space, storage, IT operations
- **Indirect costs**: Network infrastructure, storage infrastructure

**Additional cost categories** (not exhaustive):

- Software: Database, management, middle-tier
- Facilities: Upgrades, maintenance, building security, taxes
- IT labor: Security administration, application administration

>[!important] The Difference
>
>**On-premises**: Predicted costs that continue whether capacity is used or not.
>
>**AWS**: Commissioned when needed, decommissioned when no longer in use = lower overall cost.

# AWS Pricing Calculator

**[[AWS Pricing Calculator]]**: Tool to estimate monthly AWS bills.

**Capabilities**:

- Explore AWS services
- Create cost estimates for use cases
- Model solutions before building
- Explore price points and calculations
- Find available instance types and contract terms
- Make informed decisions about AWS usage

**Use cases**:

- Plan AWS costs and usage
- Price out new instances and services
- Estimate monthly costs
- Identify cost reduction opportunities

**Organization features**:

- Name your estimates
- Create and name **Groups** (containers for services)
- Organize by cost-center, department, product architecture, etc.

# Additional Benefit Considerations

## Hard Benefits

Hard Benefits: Quantifiable, direct cost reductions.

**Examples**:

- Reduced spending on compute, storage, networking, security
- Reductions in hardware and software purchases
- Reduced operational costs
- Lower backup and disaster recovery costs
- Reduction in operations personnel

### Cloud TCO vs. ROI

**[[Cloud TCO]]**: What will be spent on technology after adoption—the cost to run the solution.

**Typical TCO analysis**: Compares as-is on-premises infrastructure with to-be cloud infrastructure.

**Limitation**: Provides only a narrow view of total financial impact.

Return on Investment (ROI): Determines value generated while considering spending and saving.

**ROI process**:

1. Identify hard benefits (direct cost reductions and efficiency improvements)
2. Identify soft savings (harder to quantify but potentially more valuable)

### Soft Benefits

Value points challenging to accurately quantify, but can be more valuable than hard savings.

**Examples**:

- **Reusability**: Reuse services and applications to define and redefine solutions
- **Increased developer productivity**: Faster development cycles
- **Improved customer satisfaction**: Better performance and reliability
- **Agile business processes**: Quickly respond to new opportunities
- **Increased global reach**: Deploy worldwide in minutes

>[!tip] Importance: 
>Understanding both hard and soft benefits reveals the full value of cloud migration.

# AWS Organizations

## Introduction to AWS Organizations

**[[AWS Organizations]]**: Free account management service to consolidate multiple AWS accounts into an organization you create and centrally manage.

**Capabilities**:

- Consolidated billing
- Account management
- Better budgetary control
- Enhanced security
- Compliance management

**Key features**:

- Centrally manage access policies across multiple AWS accounts
- Control access to AWS services
- Automate AWS account creation and management
- Consolidated billing across multiple AWS accounts

## AWS Organizations Terminology

### Organizational Structure

Root: The top-level organization containing all accounts and OUs.

Organizational Unit (OU): A container for accounts within a root.

- Can contain other OUs
- Creates a hierarchical structure
- Like an upside-down tree with root at top

Account: Standard AWS account containing your AWS resources.

**Structure visualization**:

```
Root (Organization)
├── OU 1
│   ├── Account A
│   └── Account B
├── OU 2
│   ├── OU 2a
│   │   └── Account C
│   └── Account D
└── Account E
```

>[!NOTE] Hierarchy rules
>
>- An OU can have only one parent
>- Each account can be a member of exactly one OU
>- Policies attached to nodes flow down to all branches and leaves

## Key Features and Benefits

**1. Service Control Policies**:

- Create **Service Control Policies (SCPs)** that centrally control AWS services
- Apply policies across multiple AWS accounts

**2. Group Management**:

- Create groups of accounts
- Attach policies to groups
- Ensure correct policies are applied across accounts

**3. Account Automation**:

- Use [[APIs]] to automate creation and management of new AWS accounts
- Simplify account management

**4. Consolidated Billing**:

- Set up single payment method for all accounts
- See combined view of charges across all accounts
- Take advantage of pricing benefits from aggregated usage
- Central location to manage billing
- Benefit from volume discounts

# Security with AWS Organizations

## IAM vs. Service Control Policies

**AWS Organizations does NOT replace [[IAM]] policies**.

IAM Policies:

- Allow or deny access to AWS services (e.g., Amazon S3)
- Can target individual AWS resources (e.g., specific S3 bucket)
- Can target individual API actions (e.g., `s3:CreateBucket`)
- Apply only to IAM users, groups, or roles
- **Cannot restrict** the AWS account root user

Service Control Policies (SCPs):

- Allow or deny access to AWS services for entire accounts or groups of accounts
- Apply to individual AWS accounts or OUs
- Affect ALL IAM users, groups, and roles in an account
- **Can restrict** the AWS account root user

**Key difference**: SCPs provide account-level guardrails, while IAM provides user/role-level permissions.

# Organizations Setup Process

**Step 1**: Create your organization

- Use current AWS account as Primary Account
- Invite one AWS account to join
- Create another account as Member Account

**Step 2**: Create organizational units

- Create two OUs in your organization
- Place member accounts in those OUs

**Step 3**: Create service control policies

- Apply restrictions on actions delegated to users and roles
- Control what member accounts can do

**Step 4**: Test your organization's policies

- Sign in as a user for each role
- See how SCPs impact account access
- Use IAM Policy Simulator to test and troubleshoot policies

# Limits of AWS Organizations

## Naming Restrictions

>[!warning] Names must be:
>
>- Composed of Unicode characters
>- Not exceed 250 characters in length

## Maximum and Minimum Values

**Key limits**:

- **Accounts**: Varies (contact AWS for limits)
- **Roots**: 1
- **OUs**: 1,000
- **Policies**: 1,000
- **Max size of SCP document**: 5,120 bytes
- **Max nesting of OUs**: 5 levels under a root
- **Invitations sent per day**: 20
- **Member accounts created concurrently**: 5
- **Entities to attach a policy**: Unlimited

# Accessing AWS Organizations

AWS Organizations can be managed through different interfaces:

## AWS Management Console

Browser-based interface to manage organization and AWS resources. Can perform any task using the console.

## AWS CLI

**[[AWS CLI]]**: Issue commands at system's command line to perform tasks.

- Faster and more convenient than console
- Good for automation

## AWS SDKs

**[[AWS SDKs]]**: Handle tasks like cryptographically signing requests, managing errors, retrying requests.

- Libraries and sample code for various programming languages
- Platforms: Java, Python, Ruby, .NET, iOS, Android

## AWS Organizations HTTPS Query API

Programmatic access via HTTPS requests directly to the service.

- Must include code to digitally sign requests using credentials

# AWS Billing and Cost Management

**AWS Billing and Cost Management:** Service to pay AWS bills, monitor usage, and budget costs.

**Capabilities**:

- Forecast future costs and usage
- Set custom time periods
- View data at monthly or daily granularity
- Filter and group data using various dimensions
- Identify optimization opportunities

**AWS Cost and Usage Report Tool:** Understand cost and usage data trends and how you're using AWS.

# AWS Billing Dashboard

**AWS Billing Dashboard:** View status of month-to-date AWS expenditure.

- Identify services accounting for majority of expenditure
- Understand how costs are trending

**Spend Summary**

- How much you spent last month
- Estimated costs for month-to-date
- Forecast for current month spending

**Month-to-Date Spend by Service**

- Top services you use most
- Proportion of costs attributed to each service

# Cost Management Tools

From the billing dashboard, access several tools:

**[[AWS Bills]]**: Lists costs incurred for each AWS service, broken down by AWS Region and linked account.

>[!NOTE] Purpose 
>Access most up-to-date information on costs and usage.

- Costs incurred over past month for each AWS service
- Breakdown by AWS Region
- Breakdown by linked account
- Monthly bill details


**[[AWS Cost Explorer]]**: Visualize, understand, and manage AWS costs and usage over time.

- View charts of your costs
- View cost data for past 13 months
- Forecast spending for next 3 months
- Discover spending patterns over time
- Identify cost problem areas
- Identify most-used services
- View metrics (Availability Zone traffic, linked account usage)


**[[AWS Budgets]]**: Set budget thresholds and receive alerts.

- Create notifications for when you go over budget
- Notifications when estimated costs exceed budget
- Track budgets at monthly, quarterly, or yearly levels
- Customize start and end dates
- Alert delivery via email or [[Amazon SNS]]

>[!NOTE] Proactive cost management
>Get alerted before overspending occurs.


**AWS Cost and Usage Reports:** Comprehensive reports on AWS costs and usage.

-  Lists usage for each service category
- Breaks down by account and users
- Hourly or daily line items
- Tax information for tax allocation

**Delivery**: Publish billing reports to [[Amazon S3]] bucket, updated once per day.

>[!NOTE] Use case 
>Detailed analysis and custom processing of cost data.

# AWS Support

## Introduction to AWS Support

**[[AWS Support]]**: Provides tools and expertise to help you succeed with AWS.

**Mission**: Support all customers regardless of their AWS usage level:

- Customers experimenting with AWS
- Customers using AWS for production
- Customers using AWS as business-critical resource

>[!tip] 
>**Support varies** based on customer needs and goals.

## AWS Support Resources

**Technical Account Manager** (TAM):

- Designated primary point of contact
- Provides proactive guidance
- Architectural review
- Continuous ongoing communication
- Helps you plan, deploy, and optimize solutions

**AWS Trusted Advisor**:

- Like a customized cloud expert
- Online resource checking for optimization opportunities
- Reduces monthly expenditures
- Increases productivity
- Follows best practices
- Increases performance and fault tolerance

**Support Concierge**:

- Billing and account expert
- Quick, efficient analysis on billing and account issues
- Addresses non-technical billing and account-level inquiries

## AWS Support Plans

AWS offers four support plans: **Basic**, **Developer**, **Business**, and **Enterprise**.

### Basic Support Plan

**Basic Support Plan**: Included free for all AWS customers.

>[!example] **Offers**:
>- 24/7 access to customer service
>- Documentation, whitepapers, support forums
>- Access to 6 core Trusted Advisor checks
>- Access to [[Personal Health Dashboard]]

**Limitations**: No direct technical support or case support.

## Developer Support Plan

**Developer Support Plan**: For customers testing or doing early development.

> [!example] **Best for customers who**:
> 
> - Want access to guidance and technical support
> - Are exploring how to quickly put AWS to work
> - Use AWS for non-production workloads or applications
> 

**Includes**: Email support during business hours, general guidance.

## Business Support Plan

**Business Support Plan**: For customers running production workloads.

> [!example] Best for customers who**:
> 
> - Run one or more applications in production environments
> - Have multiple services activated or use key services extensively
> - Depend on business solutions to be available, scalable, and secure

**Includes**: 24/7 phone, email, and chat support, faster response times, access to all Trusted Advisor checks.

## Enterprise Support Plan

**Enterprise Support Plan**: For business and mission-critical workloads.

> [!example] **Best for customers who want to**:
> 
> - Focus on proactive management to increase efficiency and availability
> - Build and operate workloads following AWS best practices
> - Use AWS expertise to support launches and migrations
> - Have a Technical Account Manager (TAM)

> [!tip] **TAM benefits**:
> 
> - Technical expertise for full range of AWS services
> - Detailed understanding of your use case and architecture
> - Primary point of contact for ongoing support needs
> - Proactive guidance and planning

# Case Severity and Response Times

**Case Severity**: Determines response time based on impact level.

## Five Severity Levels

1. **Critical**:

	- Business is at risk
	- Critical functions unavailable
	- Fastest response time

2. **Urgent**:

	- Business significantly impacted
	- Important functions unavailable

3. **High**:

	- Important functions impaired or degraded

4. **Normal**:

	- Non-critical functions behaving abnormally
	- Time-sensitive development question

5. **Low**:

	- General development question
	- Feature request

## Response Times by Plan

**Basic Support**: No case support available

**Developer Support**:

- General guidance: < 24 business hours
- System impaired: < 12 business hours

**Business Support**:

- General guidance: < 24 hours
- System impaired: < 12 hours
- Production system impaired: < 4 hours
- Production system down: < 1 hour

**Enterprise Support**:

- General guidance: < 24 hours
- System impaired: < 12 hours
- Production system impaired: < 4 hours
- Production system down: < 1 hour
- Business-critical system down: < 15 minutes

>[!NOTE] Important 
>Response times should be considered when determining which support plan is best for your organization.

# Tags 

#AWS #CloudPricing #CostOptimization #AWSOrganizations #AWSSupport #CostManagement #AWSPricing #EnterpriseCloud #AWSBestPractices #cloud-computing 
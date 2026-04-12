# What is AWS Cloud Security

**[[AWS Security]]**: Security is the highest priority at Amazon Web Services.

**Core mission**: AWS delivers a scalable cloud computing environment designed for high availability and dependability while providing tools to run a wide range of applications.

**Critical objectives**:

- Protect **Confidentiality** of systems and data
- Protect **Integrity** of systems and data
- Protect **Availability** of systems and data
- Maintain customer trust and confidence

> [!NOTE] **Dual approach**:
> AWS security includes both controls in the AWS environment and products/features customers can use to meet their security objectives.

# AWS Shared Responsibility Model

## Overview

**AWS Shared Responsibility Model**: Security and compliance are a shared responsibility between AWS and the customer.

**Purpose**:

- Help relieve the customer's operational burden
- Provide flexibility and customer control
- Enable deployment of customer solutions on AWS

> [!NOTE] **Key distinction**: 
> Security "**of**" the cloud vs. security "**in**" the cloud.

**Principle**: The customer remains responsible for some aspects of overall security despite AWS managing much of the infrastructure.

# AWS Responsibility: Security OF the Cloud

**Security of the Cloud**: AWS operates, manages, and controls components from the software virtualization layer down to the physical security of facilities.

**What AWS protects**: The infrastructure that runs all services offered in the AWS Cloud.

**Infrastructure components**:

- Hardware
- Software
- Networking
- Facilities that run AWS Cloud services

# Physical Infrastructure

**Physical Security of Data Centers**:

- Controlled, need-based access
- Located in nondescript facilities
- 24/7 security guards
- Two-factor authentication
- Access logging and review
- Video surveillance
- Disk degaussing and destruction

## Hardware Infrastructure

Servers, storage devices, and other appliances that AWS relies on.

**Management**: AWS maintains and upgrades all physical hardware.

## Software Infrastructure

Hosts operating systems, service applications, and virtualization software.

**Layer**: From bare metal host operating system and Hypervisor virtualization layer upward.

## Network Infrastructure

Routers, switches, load balancers, firewalls, and cabling.

**Monitoring**:

- Continuously monitors network at external boundaries
- Secures access points
- Provides redundant infrastructure
- Implements intrusion detection

## Global Infrastructure

AWS is responsible for protecting the global infrastructure including:

- [[AWS Regions]]
- [[Availability Zones]]
- [[Edge Locations]]

**Verification**: Third-party auditors verify AWS compliance with computer security standards and regulations.

**Priority**: Protecting this infrastructure is the top priority for AWS.

# Customer Responsibility: Security IN the Cloud

**Security in the Cloud**: Customers are responsible for security of everything they put in the cloud.

**What customers control**: What is implemented using AWS services and applications connected to AWS.

**Variability**: Security steps depend on:

- Services used
- Complexity of the system

## Customer Security Tasks

Customers are responsible for:

- Selecting and securing instance operating systems
- Securing applications launched on AWS resources
- Security Group configurations
- Firewall configurations
- Network configurations
- Secure account management

## Critical Content Security Requirements

Customers maintain complete control over their content and are responsible for:

**Content decisions**:

- What content to store on AWS
- Which AWS services are used with the content
- In what country content is stored
- Format and structure of content
- Whether content is masked, anonymized, or encrypted

**Access Control**:

- Who has access to content
- How access rights are granted
- How access rights are managed
- How access rights are revoked

**Retention of control**: Customers choose what security to implement for their own data, environment, applications, IAM configurations, and operating systems.

# Service Characteristics and Security Responsibility

## Infrastructure as a Service (IaaS)

**IaaS Security Model**: Services provide basic building blocks for cloud IT.

**Includes**: Configurable networking, computers (virtual or dedicated hardware), and data storage space.

**Customer control**: Highest level of flexibility and management control over IT resources.

**Most similar to**: Existing on-premises computing resources.

**Example: [[Amazon EC2]]**:

- Customers manage guest operating system (including updates and security patches)
- Customers manage application software installed on instances
- Customers configure security groups

**Responsibility**: Customer performs all necessary security configuration and management tasks.

## Platform as a Service (PaaS)

**PaaS Security Model**: Services remove the need to manage underlying infrastructure.

**Focus**: Customers deploy and manage applications only.

**No need to manage**:

- Hardware
- Operating systems
- Resource procurement
- Capacity planning
- Software maintenance
- Patching

**Examples: AWS Lambda and Amazon RDS**:

- AWS operates the infrastructure layer
- AWS manages operating system
- AWS manages platforms
- Customers access endpoints to store and retrieve data

**Customer responsibilities**:

- Managing their data
- Classifying their assets
- Applying appropriate permissions

**AWS handles**:

- Operating system patching
- Database patching
- Firewall configuration
- Disaster recovery

**Characterization**: More like [[Managed Services]] with AWS handling a larger portion of security requirements.

## Software as a Service (SaaS)

**SaaS Security Model**: Services provide centrally hosted software.

**Access methods**: Web browser, mobile app, or API.

**Licensing**: Typically subscription or pay-as-you-go.

**Customer management**: Customers do not manage infrastructure supporting the service.

**AWS SaaS examples**:

**AWS Trusted Advisor**:

- Online tool analyzing AWS environment
- Provides real-time guidance and recommendations
- Helps provision resources following AWS best practices
- Part of AWS Support plan
- Free features for all accounts
- Full features for Business and Enterprise Support customers

**[[AWS Shield]]**:

- Managed [[DDoS Protection]] service
- Safeguards applications running on AWS
- Always-on detection
- Automatic inline mitigations
- Minimizes application downtime and latency
- No need to engage AWS Support for basic protection
- Advanced version available to all customers
- DDoS Response Team available with Enterprise or Business Support

**Amazon Chime**:

- Communications service
- Meet, chat, and place business calls
- Single application for all communication
- Pay-as-you-go pricing
- No upfront fees, commitments, or long-term contracts

## Shared Responsibility Model Activity

## Scenario Analysis

**Deployment**: Customer uses [[Amazon S3]] to store data, configured a VPC, and runs EC2 instance and Oracle database in the VPC.

**Questions**: Who is responsible—AWS or the customer?

## Answers

**1. OS upgrades and patches on EC2 instance**

- **Answer**: Customer
- Customer manages guest OS including security patches

**2. Physical security of data center**

- **Answer**: AWS
- Part of infrastructure AWS manages

**3. Virtualization infrastructure**

- **Answer**: AWS
- Hypervisor layer is AWS responsibility

**4. EC2 security group settings**

- **Answer**: Customer
- Customer configures AWS firewalls

**5. Configuration of applications on EC2**

- **Answer**: Customer
- Customer manages all application software

**6. Oracle upgrades/patches if running as Amazon RDS**

- **Answer**: AWS
- RDS is managed service—AWS handles patching

**7. Oracle upgrades/patches if running on EC2**

- **Answer**: Customer
- Customer manages all software on EC2 instances

**8. [[Amazon S3]] bucket access configuration**

- **Answer**: Customer
- Customer controls access to their content

# Key Insight: RDS vs. EC2 Database

**Database on EC2**: Customer applies Oracle software upgrades and patches.

**Database on RDS**: AWS applies Oracle software upgrades and patches.

**Reason**: Amazon RDS is a Managed Database offering—AWS handles time-consuming database administration tasks:

- Provisioning
- Backups
- Software patching
- Monitoring
- Hardware scaling

# AWS Identity and Access Management (IAM)

## What is IAM?

**[[AWS IAM]]**: Allows you to control access to compute, storage, database, and application services in the AWS Cloud.

**Purpose**:

- Handle **[[Authentication]]** (proving identity)
- Specify and enforce **[[Authorization]]** policies (what actions are permitted)

**Centralized management**: Tool for managing access to launching, configuring, managing, and terminating resources in your AWS account.

## Granular Control

**[[Granular Access Control]]**: Ability to specify exactly which API calls users are authorized to make to each service.

**Universal access**: Whether using AWS Management Console, AWS CLI, or AWS SDKs, every call to an AWS service is an API call.

**What IAM manages**:

- Which resources can be accessed
- By whom
- How resources can be accessed

**Example scenarios**:

- Some users: Full access to EC2, [[Amazon S3]], DynamoDB, Redshift, and other services
- Other users: Read-only access to only a few S3 buckets
- Other users: Administer only specific EC2 instances
- Other users: Access only account billing information

**Cost**: IAM is a feature of your AWS account offered at **no additional charge**.

# IAM: Essential Components

Understanding the four IAM components is critical for securing your AWS account:

## IAM User

**[[IAM User]]**: A person or application defined in an AWS account that must make API calls to AWS products.

**Requirements**:

- Unique name within the AWS account (no spaces)
- Set of security credentials not shared with other users
- Credentials different from AWS account root user credentials
- Defined in one and only one AWS account

## IAM Group

**IAM Group**: A collection of IAM users.

**Purpose**: Simplify specifying and managing permissions for multiple users.

**Benefit**: Assign permissions to groups rather than individual users.

## IAM Policy

**IAM Policy**: A document that defines permissions to determine what users can do in the AWS account.

**Typically grants**: Access to specific resources and specifies what users can do with those resources.

**Can also**: Explicitly deny access.

## IAM Role

**IAM Role**: A tool for granting temporary access to specific AWS resources in an AWS account.

**Key characteristic**: Not uniquely associated with one person—assumable by anyone who needs it.

# Authentication: Proving Identity

**[[Authentication]]**: The process of proving identity.

**Analogy**: Like presenting identification at airport security to prove who you are before entering a restricted area.

**Concept**: A user or system must first prove their identity before gaining access to AWS resources.

## Types of Access

When defining an IAM user, select which type of access the user is permitted:

**1. Programmatic Access**:

- Requires **[[Access Key ID]]** and **[[Secret Access Key]]**
- Used when making AWS API calls via:
    - AWS CLI
    - AWS SDK
    - Other development tools

**2. AWS Management Console Access**:

- User fills in browser login fields:
    - 12-digit account ID or account alias
    - IAM user name
    - Password
    - MFA authentication code (if enabled)

**Flexibility**: Can assign programmatic access only, console access only, or both.

# Multi-Factor Authentication (MFA)

**[[Multi-Factor Authentication]]** (MFA): Additional security layer beyond username and password.

**Requirement**: Users and systems must provide an MFA token **in addition to** regular sign-in credentials before accessing AWS services.

**Strong recommendation**: Enable MFA for increased security.

## MFA Token Options

**Virtual MFA Applications**:

- Google Authenticator
- Authy 2-Factor Authentication

**[[U2F Security Key]]**: Physical USB device.

**[[Hardware MFA Device]]**: Key fob or display card.

# Authorization: What Actions Are Permitted

**Authorization:** The process of determining what permissions a user, service, or application should be granted.

**Process flow**: After a user is authenticated, they must be authorized to access AWS services.

## Default Permissions

**Default state**: IAM users have **no permissions** to access any resources or data in an AWS account by default.

**Requirement**: Must explicitly grant permissions to a user, group, or role by creating a policy.

**[[Policy Document]]**: Written in [[JSON]] ([[JavaScript]] Object Notation) format.

**Policy content**: Lists permissions that allow or deny access to resources in the AWS account.

# IAM Authorization Principles

## How Permissions Work

**No default permissions**: All actions in the account are denied by default (**Implicit Deny**) unless explicitly allowed.

**Explicit allow**: Actions must be explicitly allowed to be permitted.

**Explicit Deny**: Any actions explicitly denied are **always denied** (takes precedence over allow).

## Principle of Least Privilege

**Principle of Least Privilege**: Important security concept grant only the minimal user privileges needed based on user requirements.

**Best practice process**:

1. Determine what users need to do
2. Craft policies allowing users to perform only those tasks
3. Start with minimum set of permissions
4. Grant additional permissions as necessary

**Reasoning**: More secure than starting with broad permissions and later trying to restrict them.

## Global Scope

**Important note**: IAM service configurations are **global**.

**Not Region-specific**: Settings are not defined at an AWS Region level—IAM settings apply across all AWS Regions.

# IAM Policies

## What is an IAM Policy?

**IAM Policy**: A formal statement of permissions granted to an entity.

**Can be attached to**: Users, groups, roles, or resources.

**Example**: Attach a policy to AWS resources that blocks all requests not coming from an approved IP address range.

**Policy specifies**:

- What actions are allowed
- Which resources to allow actions on
- What effect will be when user requests access

## Policy Evaluation

**Order independence**: The order policies are evaluated has no effect on the outcome.

**Evaluation process**: All policies are evaluated.

**Result**: Request is either allowed or denied.

**Conflict resolution**: When there is a conflict, the most restrictive policy applies.

## Types of IAM Policies

**1. Identity-Based Policies**: Permissions policies attached to a principal (identity) such as:

- IAM user
- IAM role
- IAM group

**Controls**: What actions that identity can perform, on which resources, and under what conditions.

**Subtypes**:

**Managed Policies**:

- Standalone identity-based policies
- Can attach to multiple users, groups, and roles in AWS account
- Can be AWS-managed or customer-managed

**Inline Policies**:

- Policies you create and manage
- Embedded directly into a single user, group, or role
- Strict one-to-one relationship

**2. Resource-Based Policies**: [[JSON]] policy documents attached to a resource (such as [[Amazon S3]]).

**Controls**: What actions a specified principal can perform on that resource, and under what conditions.

**Characteristic**: Defined inline only on the resource itself.

# IAM Policy Example

**Format**: IAM policy documents are written in [[JSON]].

**Example policy grants**:

- Access only to specific DynamoDB table
- Access to specific S3 bucket and all objects it contains

**Key element**: **Explicit Deny** (`"Effect":"Deny"`)

**NotResource Element**: Ensures users cannot use any other DynamoDB or [[Amazon S3]] actions/resources except those specified—even if permissions granted in another policy.

**Precedence rule**: An explicit deny statement takes precedence over an allow statement.

## Resource-Based Policies

### Characteristics

**Attachment**: While identity-based policies attach to users/groups/roles, resource-based policies attach to resources (e.g., S3 buckets).

**Specify**: Who can access the resource and what actions they can perform.

**Definition**: Inline only—define the policy on the resource itself instead of creating separate IAM policy document.

### Examples

**S3 Bucket Policy**:

- Type of resource-based policy
- Define on S3 bucket via Permissions tab and Bucket Policy button
- JSON-formatted policy document

**Amazon S3 ACL** (Access Control List):

- Another example of resource-based policy

### Dual Access Methods

**Two ways to grant access** (example: user MaryMajor accessing S3 bucket "photos"):

**Method 1 - Identity-based policy**: IAM policy granting S3 access attached to MaryMajor user.

**Method 2 - Resource-based policy**: S3 bucket policy for "photos" bucket specifies MaryMajor is allowed to list and read objects.

**Important note**: Can define deny statement in bucket policy to restrict access to specific IAM users, even if granted access in separate identity-based policy. **Explicit deny always takes precedence**.

## IAM Permissions Evaluation

**IAM Permissions Evaluation**: Process IAM uses to determine if permission is allowed.

**Evaluation order**:

1. Check for any applicable **explicit denial** policy
2. If no explicit denial, check for any applicable **explicit allow** policy
3. If neither exists, revert to default: **implicit deny**

**Result**: User permitted to take action only if:

- Requested action is **not explicitly denied**, AND
- Requested action **is explicitly allowed**

**Challenge**: Can be difficult to determine whether access to a resource will be granted when developing IAM policies.

## IAM Groups

### What is an IAM Group?

**IAM Group**: A collection of IAM users.

**Purpose**: Convenient way to specify permissions for a collection of users, making it easier to manage permissions.

### Example Use Case

**Scenario**: Create IAM group called "Developers"

**Process**:

1. Create "Developers" group
2. Attach IAM policy/policies granting typical developer permissions
3. Add users to Developers group
4. Users automatically inherit group permissions

**Benefits**:

- No need to attach policies directly to users
- New developer: Simply add to Developers group
- Job change: Remove from old group, add to new group

### Important Characteristics

**Many-to-many**:

- A group can contain many users
- A user can belong to multiple groups

**No Nesting**:

- Groups cannot be nested
- A group can contain only users, not other groups

**No default group**:

- No automatic group including all account users
- Must create group and manually add each user

## IAM Roles

### What is an IAM Role?

**IAM Role**: An IAM identity you can create in your account with specific permissions.

**Similar to IAM user**: Also an AWS identity with permission policies attached.

**Key difference**:

- Not uniquely associated with one person
- **Assumable by anyone who needs it**
- No standard long-term credentials (password or access keys)
- Provides **Temporary Security Credentials** for role session

### Use Cases for IAM Roles

**Delegate access to**:

- Users, applications, or services without normal access to AWS resources
- Users needing temporary access to resources they don't usually have
- Users in one AWS account needing access to resources in another account
- Mobile apps using AWS resources (without embedding AWS keys)

**Identity federation**:

- Grant AWS access to users with identities defined outside AWS (e.g., corporate directory)

**Third-party access**:

- Grant access to third parties for auditing your resources

**Essential component**: IAM roles are critical for implementing cloud deployments in all these scenarios.

## Example IAM Role Use

**Scenario**: Developer runs application on EC2 instance requiring S3 bucket access.

**Implementation**:

1. Administrator creates IAM role
2. Administrator attaches role to EC2 instance
3. Role includes **Permissions Policy**: Grants read-only access to specified S3 bucket
4. Role includes **Trust Policy**: Allows EC2 instance to assume role and retrieve temporary credentials

**When application runs**:

- Application uses role's temporary credentials to access S3 bucket
- Administrator doesn't need to grant developer permission to access bucket
- Developer never needs to share or manage credentials

**Benefits**:

- Enhanced security (no long-term credentials)
- Automatic credential rotation
- No credential management burden

## Securing a New AWS Account

### AWS Account Root User vs. IAM Access

**AWS Account Root User**: The first sign-in identity created with an AWS account.

**Characteristics**:

- Complete access to all AWS services and resources
- Accessed by signing in with email address and password used to create account
- Has (and retains) full access to all resources

**AWS strong recommendation**: Do **not** use root user credentials for day-to-day interactions with the account.

**Instead**: Use IAM to create additional users and assign permissions following principle of least privilege.

### Recommended Approach

**For administrator access**:

1. Create IAM user
2. Grant that user full access
3. Use those credentials to interact with account
4. Can revoke or modify permissions by deleting/modifying policies

**For multiple users**:

- Create unique credentials for each user
- Define which user has access to which resources
- **Avoid sharing credentials** between multiple users

**Limited root user usage**: While root user shouldn't be used for routine tasks, some tasks can **only** be accomplished by logging in as root user.

## Securing a New AWS Account: Steps

### Step-by-Step Process

**Step 1**: While logged in as root user:

- Create IAM user for yourself
- Enable AWS Management Console access
- Do not attach permissions yet
- Save IAM user access keys if needed

**Step 2**: Create IAM group:

- Give it a name (e.g., "Full Access")
- Attach IAM policies granting full access to services you'll use
- Add IAM user to the group

**Step 3**: Disable and remove root user access keys (if they exist)

**Step 4**: Enable password policy for all users:

- Copy IAM users sign-in link from IAM Dashboard
- Sign out as root user

**Step 5**: Sign in with new IAM user:

- Browse to IAM users sign-in link
- Sign in using new IAM user credentials

**Step 6**: Store root user credentials in a secure place

### Multi-Factor Authentication (MFA)

**Recommendation**: Require MFA for:

- Account root user login
- All other IAM user logins

**Control**: Can also use MFA to control programmatic access.

**Options**: Virtual MFA apps, U2F security keys, hardware MFA devices.

## AWS CloudTrail

**[[AWS CloudTrail]]**: Service that logs all API requests to resources in your account.

**Purpose**: Enables **Operational Auditing** on your account.

### Default Configuration

**Enabled by default**: CloudTrail is enabled on account creation for all AWS accounts.

**Retention**: Keeps record of last **90 days** of account management event activity.

**Access**: Can view and download last 90 days of activity for create, modify, and delete operations without manually creating another trail.

### Extended Configuration

**For longer retention**: Create a new trail to:

- Enable CloudTrail log retention beyond 90 days
- Enable alerting when specified events occur

**Trail creation**: Involves specifying S3 bucket for log storage and configuring event types to log.

## Billing Reports

**Recommendation**: Enable billing reports for new AWS accounts.

**AWS Cost and Usage Report**: Provides information about:

- Use of AWS resources
- Estimated costs for that use

**Delivery**: AWS delivers reports to Amazon S3 bucket you specify.

**Update frequency**: AWS updates reports at least once per day.

**Tracking**: Tracks usage in AWS account and provides estimated charges by hour or by day.

**Purpose**: Helps monitor costs and identify unexpected spending.

# Tags

#AWS #Security #IAM #SharedResponsibility #CloudSecurity #AccessControl #Authentication #Authorization #SecurityPolicies #CloudTrail #IAMPolicies #AWSSecurity #DataProtection #cloud-computing 
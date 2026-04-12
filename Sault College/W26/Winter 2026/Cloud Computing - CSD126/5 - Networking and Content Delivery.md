# What is a Network?

**Computer Network**: Two or more client machines connected together to share resources.

**[[Subnets]]**: Networks can be logically partitioned into subnets.

**Networking requirements**: Requires a networking device (such as a [[Router]] or [[Switch]]) to connect all clients together and enable communication between them.

# IP Addresses

## What is an IP Address?

**[[IP Address]]**: A unique numerical label that identifies each client machine in a network.

**Format**: Decimal format that machines convert to binary.

**Example**: `192.0.2.0`

**Structure**: Each of the four dot-separated numbers represents **8 bits** in **[[Octal Number Format]]**.

**Range**: Each of the four numbers can be anything from **0 to 255**.

**Total size**: The combined total is **32 bits** in binary format.

# IPv4 and IPv6 Addresses

## IPv4 Addresses

**[[IPv4 Address]]**: A 32-bit IP address.

**Structure**: Four dot-separated numbers (e.g., `192.0.2.0`)

**Limitation**: Limited address space (approximately 4.3 billion addresses)

## IPv6 Addresses

**[[IPv6 Address]]**: A 128-bit IP address that can accommodate more user devices.

**Structure**: Eight groups of four letters and numbers separated by colons (`:`)

**Example**: `2600:1f18:22ba:8c00:ba86:a05e:a5ba:00FF`

**Format**: Each of the eight colon-separated groups represents **16 bits** in **[[Hexadecimal Number Format]]**.

**Range**: Each group can be anything from **0 to FFFF**.

**Total size**: The combined total is **128 bits** in binary format.

# Classless Inter-Domain Routing (CIDR)

**[[CIDR]]** (Classless Inter-Domain Routing): A common method to describe networks.

## CIDR Address Format

**Components**:

1. An IP address (the first address of the network)
2. A slash character (`/`)
3. A number telling you how many bits of the Routing Prefix must be fixed for the network identifier

**Purpose**: Expresses a group of IP addresses that are consecutive to each other.

## CIDR Examples

**Example 1**: `192.0.2.0/24`

- **Last number (24)**: First 24 bits must be fixed
- **Last 8 bits**: Flexible
- **Available addresses**: 2^8 = **256 IP addresses**
- **Range**: `192.0.2.0` to `192.0.2.255`
- **Fourth decimal digit**: Can change from 0 to 255

**Example 2**: `192.0.2.0/16`

- **Last number (16)**: First 16 bits must be fixed
- **Last 16 bits**: Flexible
- **Available addresses**: 2^16 = **65,536 IP addresses**
- **Range**: `192.0.0.0` to `192.0.255.255`
- **Third and fourth digits**: Can each change from 0 to 255

## Special CIDR Cases

**Fixed IP Address**: `192.0.2.0/32`

- Every bit is fixed
- Represents a single IP address
- Helpful for firewall rules to give access to a specific host

**[[The Internet]]**: `0.0.0.0/0`

- Every bit is flexible
- Represents all possible IP addresses

# OSI Model

**[[OSI Model]]** (Open Systems Interconnection): A conceptual model explaining how data travels over a network.

**Structure**: Consists of **seven layers**

**Purpose**: Shows common protocols and addresses used to send data at each layer.

### Layer Examples

**Layer 2** (Data Link Layer):

- Hubs and switches operate here

**Layer 3** (Network Layer):

- Routers operate here

**Application**: Can be used to understand communication in a **[[Amazon VPC|VPC]]**

**Note**: [[ICA]] (Independent Computing Architecture) was developed by Citrix Systems to facilitate efficient data transfer between a server and a client.

# Amazon VPC

## What is Amazon VPC?

**[[Amazon VPC]]** (Amazon Virtual Private Cloud): A service that lets you provision a logically isolated section of the AWS Cloud where you can launch AWS resources.

![[Amazon VPC#Why Does VPC Exist?]]

**Control provided**:

- Selection of your own IP address range
- Creation of subnets
- Configuration of route tables
- Configuration of network gateways

**Protocol support**: Both IPv4 and IPv6

## VPC Use Cases

**Public subnets**: Create public subnets for web servers with internet access

**Private subnets**: Place backend systems (databases, application servers) in private subnets with no public internet access

**Security layers**: Use multiple layers of security including:

- [[Security Groups]]
- [[Network ACLs]] (Access Control Lists)

**Purpose**: Control access to [[Amazon EC2]] instances in each subnet

# VPCs and Subnets

## Virtual Private Clouds

**[[VPC]]**: A virtual network logically isolated from other virtual networks in the AWS Cloud.

**Characteristics**:

- Dedicated to your account
- Belong to a single [[AWS Regions|AWS Region]]
- Can span multiple [[Availability Zones]]

## Subnets

**[[Subnet]]**: A range of IP addresses in a VPC.

**Characteristics**:

- Belong to a single Availability Zone
- Can create subnets in different AZs for **High Availability**

**Classification**:

**Public Subnet**: Has direct access to the internet

**Private Subnet**: Does not have direct access to the internet

# IP Addressing in VPCs

**Purpose**: Enable resources in your VPC to communicate with each other and with resources over the internet.

## IPv4 CIDR Blocks

**When creating a VPC**: You assign an **IPv4 CIDR Block** (a range of private IPv4 addresses).

**Important limitation**: After you create a VPC, you **cannot change** the address range—choose carefully!

**Size options**:

- **Largest**: `/16` (2^16 = 65,536 addresses)
- **Smallest**: `/28` (2^4 = 16 addresses)

## IPv6 Support

**Optional**: You can associate an **IPv6 CIDR Block** with your [[VPC]] and [[subnets]].

**Different limits**: IPv6 CIDR blocks have different block size limits than IPv4.

## Subnet CIDR Blocks

**Same size**: Subnet CIDR block can be the same as VPC CIDR block (single subnet in VPC)

**Subset**: Subnet CIDR block can be a subset of VPC CIDR block (enables multiple subnets)

**Important rule**: If creating multiple subnets, CIDR blocks **cannot overlap**—no duplicate IP addresses in the same VPC.

# Reserved IP Addresses

**AWS reservation**: For each CIDR block, AWS reserves **five IP addresses** within that block.

**Reserved for**:

1. **Network Address**
2. **VPC Local Router** (internal communications)
3. **DNS Resolution**
4. **Future use**
5. **Network Broadcast Address**

## Example Calculation

**Subnet**: `10.0.0.0/24`

- **Total IP addresses**: 256
- **Reserved addresses**: 5
- **Available addresses**: **251**

# Public IP Address Types

## Automatic Private IP

**Default**: Every instance in a VPC gets a **Private IP Address** automatically.

## Public IP Address

**Public IP Address**: Can be assigned when creating an instance by modifying the subnet's auto-assign public IP address properties.

## Elastic IP Address

**[[Elastic IP Address]]**: A static and public IPv4 address designed for dynamic cloud computing.

**Characteristics**:

- Can associate with any instance or network interface
- Works for any VPC in your account
- Can mask instance failure by rapidly remapping to another instance

**Advantage of network interface association**: Can move all attributes of the network interface from one instance to another in a single step.

**Cost consideration**: Additional costs may apply—release them when no longer needed.

# Elastic Network Interface

**Elastic Network Interface**: A virtual network interface you can attach or detach from an instance in a VPC.

**Attribute persistence**: Network interface's attributes follow it when reattached to another instance.

**Traffic redirection**: When moved from one instance to another, network traffic is redirected to the new instance.

## Primary Network Interface

**Default**: Each instance has a **Primary Network Interface** assigned a private IPv4 address from the VPC range.

**Cannot detach**: You cannot detach a primary network interface from an instance.

## Additional Network Interfaces

**Flexibility**: Can create and attach additional network interfaces to any instance.

**Limitation**: The number you can attach varies by [[Instance Type]].

# Route Tables and Routes

## Route Table

**[[Route Table]]**: Contains a set of rules (called **[[Routes]]**) that direct network traffic from your subnet.

**Route components**:

- **Destination**: The destination CIDR block where traffic should go
- **Target**: The target that the destination traffic is sent through

**Default route**: Every route table contains a **[[Local Route]]** for communication within the VPC (cannot be deleted).

## Main Route Table

**Main Route Table**: Automatically assigned to your VPC.

**Controls**: Routing for all subnets not explicitly associated with any other route table.

**Association rules**:

- A subnet can be associated with only **one** route table at a time
- You can associate **multiple** subnets with the same route table

# Section 3: VPC Networking

## Internet Gateway

**[[Internet Gateway]]**: A scalable, redundant, and highly available VPC component allowing communication between instances in your VPC and the internet.

**Two purposes**:

1. Provide a target in VPC route tables for internet-routable traffic
2. Perform **[[NAT Gateway|Network Address Translation]]** (NAT) for instances assigned public IPv4 addresses

**Making a subnet public**:

1. Attach an internet gateway to your VPC
2. Add a route to the route table to send non-local traffic (`0.0.0.0/0`) through the internet gateway

# NAT Gateway

**[[NAT Gateway]]** (Network Address Translation Gateway): Enables instances in a private subnet to connect to the internet or other AWS services, but **prevents** the internet from initiating connections with those instances.

## Setting Up a NAT Gateway

**Requirements**:

1. Must specify the **public subnet** where NAT gateway should reside
2. Must specify an **[[Elastic IP Address]]** to associate with the NAT gateway

**Configuration**: Must update the route table associated with private subnets to point internet-bound traffic to the NAT gateway.

**Result**: Instances in private subnets can communicate with the internet.

## NAT Gateway vs. NAT Instance

**Alternative**: Can use a **NAT Instance** in a public subnet instead of a NAT gateway.

**AWS recommendation**: Use a [[NAT gateway]] for common use cases because it provides:

- Better availability
- Higher bandwidth
- Less administrative effort

# VPC Sharing

**VPC Sharing**: Enables customers to share subnets with other AWS accounts in the same organization in [[AWS Organizations]].

## How VPC Sharing Works

**Owner account**: The account that owns the VPC shares one or more subnets

**Participant accounts**: Other accounts that belong to the same organization

**Participant capabilities**: Can view, create, modify, and delete their application resources in shared subnets

**Participant limitations**: Cannot view, modify, or delete resources belonging to other participants or the VPC owner

## VPC Sharing Benefits

**Separation of Duties**: Centrally controlled VPC structure, routing, IP address allocation

**Ownership**: Application owners continue to own resources, accounts, and security groups

**[[Security Groups]]**: Participants can reference each other's security group IDs

**Efficiencies**: Higher density in subnets, efficient use of VPNs and [[AWS Direct Connect]]

**No Hard Limits**: Avoid hard limits (e.g., 50 virtual interfaces per Direct Connect connection) through simplified network architecture

**Optimized Costs**: Costs optimized through reuse of NAT gateways, VPC Interface Endpoints, and intra-Availability Zone traffic

### Architecture Benefits

**Decoupling**: Enables you to decouple accounts and networks

**Centralization**: Fewer, larger, centrally managed VPCs

**Interconnectivity**: Highly interconnected applications automatically benefit

## VPC Peering

**VPC Peering Connection**: A networking connection between two VPCs enabling private traffic routing between them.

**Communication**: Instances in either VPC can communicate as if within the same network.

**Flexibility**: Can peer:

- Between your own VPCs
- With a VPC in another AWS account
- With a VPC in a different AWS Region

### Setting Up VPC Peering

**Process**: Create rules in route tables to allow VPCs to communicate through the **Peering Resource**.

**Example configuration**:

- **VPC A route table**: Destination = VPC B IP address, Target = peering resource ID
- **VPC B route table**: Destination = VPC A IP address, Target = peering resource ID

### VPC Peering Restrictions

**IP address overlap**: IP address ranges cannot overlap

**[[Transitive Peering]]**: Not supported

- Example: If VPC A connects to VPC B, and VPC A connects to VPC C, VPC B does NOT automatically connect to VPC C
- Must explicitly establish connectivity

**One peering resource**: Can only have one peering resource between the same two VPCs

## AWS Site-to-Site VPN

**Purpose**: Connect your VPC to your remote network (create a **[[VPN]] Connection**).

### Setup Process

**Default limitation**: Instances launched in a VPC cannot communicate with a remote network by default.

**Setup steps**:

1. Create a **[[VPN|VPN Gateway]]** (virtual gateway device) and attach to your VPC
2. Define the configuration of the **Customer Gateway** (not a physical device, but an AWS resource providing information about your VPN device)
3. Create a custom route table to point corporate data center-bound traffic to the VPN gateway
4. Update security group rules
5. Establish an **AWS Site-to-Site VPN** connection to link the two systems
6. Configure routing to pass traffic through the connection

## AWS Direct Connect

**Challenge**: Network performance can be negatively affected if your data center is far from your AWS Region.

**[[AWS Direct Connect]]** (DX): Enables you to establish a dedicated, private network connection between your network and one of the **DX Locations**.

### Benefits

**Private connection**: Can reduce network costs

**Increased bandwidth**: Higher bandwidth throughput

**Consistent experience**: More consistent network experience than internet-based connections

**Standard**: Uses open standard **802.1q [[VLAN|VLANs]]** (Virtual Local Area Networks)

## VPC Endpoints

**[[VPC Endpoint]]**: A virtual device enabling private connection from your VPC to supported AWS services and VPC endpoint services powered by **[[AWS PrivateLink]]**.

**No internet gateway required**: Connection doesn't require:

- Internet gateway
- NAT device
- VPN connection
- AWS Direct Connect connection

**No public IPs needed**: Instances don't require public IP addresses to communicate with resources in the service.

**Traffic stays internal**: Traffic between your VPC and the service doesn't leave the Amazon network.

### Types of VPC Endpoints

**Interface VPC Endpoint** (Interface Endpoint):

- Connects to services powered by AWS PrivateLink
- Includes AWS services, services hosted by other AWS customers and [[APN Partners]] in their VPCs ([[Endpoint Services]]), and supported AWS Marketplace services
- **Service provider**: Owner of the service
- **Service consumer**: Principal who creates the interface endpoint
- **Charges**: For creating and using an interface endpoint (hourly usage rates and data processing rates)

**[[Gateway Endpoints]]**:

- No additional charge for use
- Standard charges for data transfer and resource usage apply

## AWS Transit Gateway

### The Connectivity Challenge

**Problem**: As AWS customers grow, they have hundreds of VPCs distributed across accounts and Regions.

**Complexity**: All connectivity options (Direct Connect, NAT gateways, internet gateways, VPC peering) are strictly point-to-point.

**Growth issue**: The number of VPC-to-VPC connections grows quickly.

**On-premises challenge**: Must attach VPN to each individual VPC—time-consuming and difficult to manage at scale.

### The Solution: AWS Transit Gateway

**[[AWS Transit Gateway]]**: Simplifies networking model by acting as a central hub.

**Hub-and-spoke model**:

- Transit gateway = hub
- Connected networks = spokes

**Setup**: Only need to create and manage a single connection from the central gateway to each VPC, on-premises data center, or remote office.

**Traffic control**: Transit gateway controls how traffic is routed among all connected networks.

### Benefits

**Simplified management**: Significantly reduces operational costs

**Easy scaling**: Any new VPC connects to transit gateway and becomes automatically available to every other connected network

**Ease of connectivity**: Makes it easier to scale network as you grow

## Section 4: VPC Security

### Overview

**Security layers**: Can build security into VPC architecture in several ways for complete control over incoming and outgoing traffic.

**Two main firewall options**:

1. Security Groups
2. Network Access Control Lists (Network ACLs)

## Security Groups

### What is a Security Group?

**[[Security Group]]**: Acts as a virtual firewall for your instance, controlling inbound and outbound traffic.

**Level of operation**: Acts at the **instance level**, not the subnet level.

**Flexibility**: Each instance in a subnet can be assigned to a different set of security groups.

**Purpose**: Filter traffic to your instances.

### Security Group Rules

**Inbound rules**: When you create a security group, it has **no inbound rules** by default.

- No inbound traffic from another host is allowed until you add inbound rules

**Outbound rules**: By default, includes an outbound rule allowing **all outbound traffic**.

- Can remove the rule and add outbound rules for specific traffic only

**Stateful**: Security groups are stateful—state information is kept even after a request is processed.

**Stateful behavior**:

- If you send a request from your instance, response traffic is allowed to flow in regardless of inbound rules
- Responses to allowed inbound traffic are allowed to flow out, regardless of outbound rules

### Custom Security Group Rules

**Allow rules only**: Can specify allow rules, but **not deny rules**

**Evaluation**: All rules are evaluated before the decision to allow traffic

**No explicit deny**: Cannot create deny rules in security groups

## Network Access Control Lists (Network ACLs)

### What is a Network ACL?

**[[Network ACL]]** (Network Access Control List): An optional layer of security for your Amazon VPC acting as a firewall for controlling traffic in and out of one or more subnets.

**Additional security**: Adds another layer on top of security groups.

**Similar to security groups**: Can set up rules similar to security groups for added protection.

### Network ACL Association

**Required association**: Each subnet must be associated with a network ACL.

**Default association**: If not explicitly associated, subnet is automatically associated with the **Default Network ACL**.

**One-to-many**: Can associate a network ACL with multiple subnets.

**One-to-one constraint**: A subnet can be associated with only **one** network ACL at a time.

**Association change**: When you associate a network ACL with a subnet, the previous association is removed.

### Network ACL Rules

**Separate rules**: Has separate inbound and outbound rules.

**Allow or deny**: Each rule can either allow or deny traffic.

**Default network ACL**: By default, allows all inbound and outbound IPv4 traffic (and IPv6 if applicable).

**Stateless**: Network ACLs are stateless—no information about a request is maintained after processing.

### Custom Network ACLs

**Default behavior**: Each custom network ACL **denies** all inbound and outbound traffic by default until you add rules.

**Numbered rules**: Contains a numbered list of rules evaluated in order, starting with the lowest numbered rule.

**Highest rule number**: 32,766

**AWS recommendation**: Create rules in increments (e.g., 10 or 100) so you can insert new rules later where needed.

## Security Groups vs. Network ACLs

**Key differences**:

|Aspect|Security Groups|Network ACLs|
|---|---|---|
|**Level**|Instance level|Subnet level|
|**Rules**|Allow rules only|Allow and deny rules|
|**State**|Stateful|Stateless|
|**Evaluation**|All rules evaluated before decision|Rules evaluated in number order|

## Section 5: Amazon Route 53

### What is Amazon Route 53?

**[[Amazon Route 53]]**: A highly available and scalable cloud **[[DNS]]** (Domain Name System) web service.

**Purpose**: Gives developers and businesses a reliable and cost-effective way to route users to internet applications by translating names (like `www.example.com`) into numeric IP addresses (like `192.0.2.1`).

**IPv6 compliant**: Fully compliant with IPv6.

### Amazon Route 53 Capabilities

**AWS integration**: Connects user requests to infrastructure running in AWS:

- Amazon EC2 instances
- Elastic Load Balancing load balancers
- Amazon S3 buckets

**External routing**: Can also route users to infrastructure outside of AWS.

**DNS Health Checks**: Can configure [[DNS]] health checks to route traffic to healthy endpoints or independently monitor application health.

**[[Amazon Route 53|Amazon Route 53 Traffic Flow]]**: Helps manage traffic globally through several routing types, which can be combined with DNS failover for low-latency, fault-tolerant architectures.

**Visual editor**: Simple visual editor to manage how users are routed to application endpoints.

**[[Domain Name Registration]]**: Can purchase and manage domain names (like `example.com`), and Amazon Route 53 automatically configures DNS settings.

## Amazon Route 53 DNS Resolution

**Basic pattern**: When a user initiates a DNS request:

1. DNS resolver checks with your domain in Route 53
2. Gets the IP address
3. Returns it to the user

## Amazon Route 53 Supported Routing Policies

Amazon Route 53 supports several routing policies determining how Route 53 responds to queries:

### Simple Routing (Round Robin)

**Simple Routing**: Use for a single resource performing a given function for your domain (e.g., a web server serving content for `example.com`).

### Weighted Round Robin Routing

**Weighted Round Robin Routing**: Route traffic to multiple resources in proportions you specify.

**How it works**: Assign weights to resource record sets to specify frequency of different responses.

**Use case**: A/B Testing—send a small portion of traffic to a server with software changes.

**Example**:

- Record set weight 3 and weight 1
- Route 53 returns weight 3 record 75% of the time
- Returns weight 1 record 25% of the time

**Weight range**: Any number between 0 and 255.

### Latency Routing (LBR)

**Latency Routing**: Use when you have resources in multiple AWS Regions and want to route traffic to the Region providing the best latency.

**How it works**: Routes customers to the AWS endpoint providing fastest experience based on actual performance measurements.

**Based on**: Real performance data from different AWS Regions where your application runs.

### Geolocation Routing

**[[Geolocation Routing]]**: Route traffic based on the location of your users.

**Use cases**:

- Localize content
- Present website in user's language
- Restrict content distribution to locations where you have distribution rights
- Balance load across endpoints in predictable way (each user location consistently routed to same endpoint)

### Geoproximity Routing

**Geoproximity Routing**: Route traffic based on the location of your resources and, optionally, shift traffic from resources in one location to resources in another.

### Failover Routing (DNS Failover)

**Failover Routing**: Configure active-passive failover.

**Purpose**: Amazon Route 53 can detect website outage and redirect users to alternate locations where application is operating properly.

**Health monitoring**: Route 53 health-checking agents monitor each location/endpoint to determine availability.

**Benefit**: Increases availability of customer-facing application.

### Multivalue Answer Routing

**Multivalue Answer Routing**: Route 53 responds to DNS queries with up to eight healthy records selected at random.

**Capability**: Can return multiple values (e.g., IP addresses for web servers) in response to DNS queries.

**Health checking**: Can check health of each resource so Route 53 returns only values for healthy resources.

**Not a load balancer**: Not a substitute for load balancer, but the ability to return multiple health-checkable IP addresses is a way to use DNS to improve availability and load balancing.

## Use Case: Multi-Region Deployment

**Multi-Region Deployment**: An example use case for Amazon Route 53.

**How it works**: User is automatically directed to the Elastic Load Balancing load balancer closest to the user.

**Benefits**:

- **Latency-Based Routing** to the Region
- **Load Balancing** routing to the Availability Zone

## Amazon [[Route 53]] DNS Failover

**Purpose**: Improve availability of applications running on AWS.

**Capabilities**:

1. Configure backup and failover scenarios for your applications
2. Enable highly available multi-Region architectures on AWS
3. Create health checks to monitor health and performance

**Health check monitoring**:

- Health of specified resource (e.g., web server)
- Status of other health checks
- Status of an [[Amazon CloudWatch]] alarm

## DNS Failover for Multi-Tiered Web Application

**Architecture**: Route 53 passes traffic to a load balancer, which distributes traffic to a fleet of EC2 instances.

### High Availability Tasks

**1. Create DNS records**: Two DNS records for **[[CNAME]]** (Canonical Name Record) `www` with Failover Routing Policy:

- **Primary route policy**: Points to load balancer for web application
- **Secondary route policy**: Points to static [[Amazon S3]] website

**2. Use Route 53 health checks**: Make sure primary is running

- If running: All traffic defaults to web application stack
- If down: Failover to static backup site triggered if either:
    - Web server goes down (or stops responding)
    - Database instance goes down

## Section 6: [[Amazon CloudFront]]

### Content Delivery and Network Latency

**The challenge**: Network performance and latency affect content delivery.

**Network hops**: When you browse a website or stream video, your request routes through many different networks to reach an **Origin Server**.

**Origin server**: Stores the original, definitive versions of objects (webpages, images, media files).

**Impact**: Number of network hops and distance significantly affect performance and responsiveness.

**Geographic variation**: Network latency differs in various geographic locations.

**Solution**: A content delivery network (CDN).

## Content Delivery Network (CDN)

**[[CDN]]** (Content Delivery Network): A globally distributed system of caching servers.

**Static content**: CDN caches copies of commonly requested files:

- [[HTML]] (Hypertext Markup Language)
- [[CSS]] (Cascading Style Sheets)
- [[JavaScript]]
- Image files

**How it works**: Delivers a local copy of requested content from a cache edge or **Point of Presence** (PoP) providing fastest delivery.

### Dynamic Content Delivery

**Dynamic content**: Unique to the requester and not cacheable.

**Performance improvement**: Having a CDN deliver dynamic content improves application performance and scaling.

**Secure connections**: CDN establishes and maintains secure connections closer to the requester.

**Same network advantage**: If CDN is on same network as origin, routing back to origin to retrieve dynamic content is accelerated.

**Content ingestion**: Content like form data, images, and text can be ingested and sent back to origin, taking advantage of low-latency connections and proxy behavior of the PoP.

## Amazon CloudFront

**[[Amazon CloudFront]]**: A fast CDN service that securely delivers data, videos, applications, and APIs to customers globally with low latency and high transfer speeds.

**Developer-friendly**: Provides a developer-friendly environment.

**Global network**: Delivers files over a worldwide network of edge locations and Regional Edge Caches.

**No contracts**: Different from traditional CDN solutions—quickly obtain benefits without negotiated contracts, high prices, or minimum fees.

**Self-service**: Self-service offering with **pay-as-you-go pricing**.

## Amazon CloudFront Infrastructure

**[[Edge Locations]]**: CloudFront delivers content through worldwide network of data centers called edge locations.

**Routing**: When a user requests content, they're routed to the edge location providing lowest latency (time delay).

**Performance**: Content delivered with best possible performance.

**Popular content**: Edge locations serve popular content quickly.

### Regional Edge Caches

**Regional Edge Caches**: CloudFront locations deployed globally close to viewers, located between origin server and global edge locations.

**Larger cache**: Regional edge cache has larger cache than individual edge location.

**Longer retention**: Objects remain in Regional edge cache longer.

**Benefit**: More content stays closer to viewers, reducing need for CloudFront to fetch from origin server, improving overall performance.

## Amazon CloudFront Benefits

### Fast and Global

**Massive scale**: Globally distributed system.

**Low latency**: Uses global network of edge locations and regional caches to deliver content with low latency.

### Security at the Edge

**Network-level protection**: Provides both network-level and application-level protection.

**[[AWS Shield|AWS Shield Standard]]**: Built-in protections at no additional cost.

**[[AWS Certificate Manager]]** (ACM): Can create and manage custom [[SSL Certificates]] at no extra cost.

### Highly Programmable

**Customization**: Features can be customized for specific application requirements.

**[[Lambda@Edge]]**: Integrates so you can run custom code across AWS locations worldwide, moving complex application logic closer to users.

**DevOps integration**: Supports integrations with other tools and automation interfaces for [[7 - Infrestructure as Code (IaC)|CI/CD]] (Continuous Integration and Continuous Delivery) environments.

### Deeply Integrated with AWS

**Physical integration**: Directly connected to AWS Global Infrastructure.

**API access**: Can programmatically configure all features via APIs or AWS Management Console.

### Cost-Effective

**No minimum commitments**: Only charges for what you use.

**Avoids complexity**: Avoids expense and complexity of operating network of cache servers across internet.

**No overprovisioning**: Eliminates need to overprovision capacity for potential traffic spikes.

**Request collapsing**: Collapses simultaneous viewer requests at edge location for same file into single request to origin server.

**Reduced origin load**: Results in reduced load on origin servers and reduced need to scale origin infrastructure.

**AWS origins savings**: If using AWS origins ([[Amazon S3]], Elastic Load Balancing), pay only for storage costs, not for data transferred between these services and [[Amazon CloudFront]].

## Amazon CloudFront Pricing

Charges based on actual usage in four areas:

### 1. Data Transfer Out

**Charge**: Volume of data transferred out from CloudFront [[edge locations]] to internet or origin, measured in GB.

**Regional pricing**: Data transfer totaled separately for geographic regions, then cost calculated based on pricing tiers for each area.

**Separate charges**: If using other AWS services as origins, charged separately for those services (storage, compute hours).

### 2. HTTP(S) Requests

**Charge**: Number of [[HTTP]](S) requests made to CloudFront for your content.

### 3. Invalidation Requests

**Charge**: Per path in your invalidation request.

**Path definition**: A path represents the URL (or multiple URLs if path contains wildcard character) of object to invalidate from CloudFront cache.

**Free tier**: Up to 1,000 paths each month at no additional charge.

**Beyond free tier**: Charged per path beyond first 1,000.

### 4. Dedicated IP Custom SSL

**Charge**: $600 per month for each custom SSL certificate associated with one or more CloudFront distributions using Dedicated IP version of custom SSL certificate support.

**Prorated**: Monthly fee prorated by the hour.

**Example**: If custom SSL certificate associated with CloudFront distribution for 24 hours (1 day) in June:

- Calculation: (1 day / 30 days) × $600 = $20

# Tags

#AWS #Networking #VPC #CloudNetworking #Subnets #IPAddressing #CIDR #SecurityGroups #InternetGateway #NetworkSecurity #CloudInfrastructure #CloudSecurity #cloud-computing 
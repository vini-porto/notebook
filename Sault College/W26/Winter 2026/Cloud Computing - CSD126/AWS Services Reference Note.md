# Storage

## [[S3]] — Simple Cloud File Storage

S3 (_Simple Storage Service_) is like a giant, infinitely large hard drive in the cloud. You upload files (called _objects_) into containers called _buckets_, and AWS stores them safely and makes them accessible from anywhere. Use it to store images, videos, backups, static website files, or any other data your application needs to keep around.
# Compute

## [[EC2]] — Your Own Virtual Server

EC2 (_Elastic Compute Cloud_) gives you a virtual computer in the cloud that you fully control. You choose the operating system, install your software, and manage everything yourself. It is the most flexible option, but it also means more responsibility — you handle patching, scaling, and configuration.

## [[Elastic Beanstalk]] — Managed App Hosting

Elastic Beanstalk is a layer on top of EC2 that handles the boring infrastructure work for you. You just upload your application code and AWS automatically provisions servers, load balancers, and scaling. Think of it as EC2 with training wheels — great when you want to focus on your code, not your servers.

> [!note] EC2 vs. Elastic Beanstalk
> 
> - **EC2** = full control, full responsibility. You manage the server from the ground up. Best when you need custom configurations or specific software setups.
> - **Elastic Beanstalk** = you give AWS your code, AWS handles the infrastructure. Best when you want to deploy a web app quickly without thinking about servers.
> - Rule of thumb: if the exam scenario mentions a _developer_ who just wants to deploy code, think Elastic Beanstalk. If it mentions _custom server configuration_, think EC2.

## [[Auto Scaling]] — Automatically Adjusting Your Capacity

Auto Scaling watches your EC2 instances and adds more when traffic spikes, then removes them when things quiet down. This keeps your app fast during busy periods and saves money during slow ones. It works hand-in-hand with a load balancer to distribute traffic across those instances.

# Networking & Delivery

## [[VPC]] / Subnets — Your Private Network in the Cloud

A VPC (_Virtual Private Cloud_) is your own isolated section of the AWS network — like renting a private floor in a building. Inside a VPC, you divide your network into _subnets_: **public subnets** for things that need to be reached from the internet (like web servers), and **private subnets** for things that should stay hidden (like databases). This gives you control over who can talk to what.

## [[ELB]] — Traffic Director for Your Servers

ELB (_Elastic Load Balancer_) sits in front of your EC2 instances and distributes incoming requests evenly across them. If one server goes down, ELB automatically stops sending traffic to it. This keeps your application reliable and prevents any single server from being overwhelmed.

## [[CloudFront]] — Fast Global Content Delivery

CloudFront is a _CDN_ (Content Delivery Network). It caches copies of your content (images, videos, web pages) at servers located around the world, so users always get data from the location closest to them. This makes your app load much faster for users in different countries.

> [!note] ELB vs. CloudFront
> 
> - **ELB** distributes traffic _between your servers_. It is about reliability and load distribution within your infrastructure.
> - **CloudFront** distributes content _to your users_ by caching it globally. It is about speed and reducing latency for end users.
> - They are not competing — they often work together. CloudFront sits at the edge facing the internet; ELB sits behind it routing to your servers.

# Database

## [[RDS]] — Managed Relational Database

RDS (_Relational Database Service_) gives you a traditional SQL database (like MySQL, PostgreSQL, or Oracle) without having to manage the underlying server. AWS handles backups, updates, and failover. Use it when your data has a clear structure and relationships between tables — for example, users, orders, and products in an e-commerce app.

## [[DynamoDB]] — Fast NoSQL Database

DynamoDB is a fully managed _NoSQL_ database built for speed and massive scale. Instead of structured tables with fixed columns, it stores data as flexible key-value or document pairs. Use it when your app needs to handle millions of requests per second, or when your data does not fit neatly into rows and columns — for example, storing user session data or game leaderboards.

> [!note] RDS vs. DynamoDB
> 
> |Feature|RDS|DynamoDB|
> |---|---|---|
> |Data model|Structured (SQL tables)|Flexible (key-value / JSON)|
> |Scaling|Vertical (bigger server)|Horizontal (automatic, massive)|
> |Best for|Complex queries, relationships|High-speed, high-volume, simple lookups|
> |Schema|Fixed columns|Schema-less|
> 
> Rule of thumb: if the scenario mentions _structured relational data_ or _complex joins_, think RDS. If it mentions _high throughput_, _millions of users_, or _simple lookups_, think DynamoDB.

# Security

## [[IAM]] — Who Can Do What Inside AWS

IAM (_Identity and Access Management_) is the system that controls which AWS users or services are allowed to access which AWS resources. You create _users_, _groups_, and _roles_, then attach _policies_ (permission rules) to them. Example: you can give your Lambda function permission to read from S3, but nothing else.

## [[Cognito]] — User Sign-Up and Login for Your App

Cognito handles user authentication for your _application's_ users — the people using your website or mobile app. It manages sign-up, login, password reset, and even social login (Google, Facebook). You do not need to build your own login system; Cognito does it for you.

## [[CloudTrail]] — The Audit Log for Your AWS Account

CloudTrail records every action taken in your AWS account — who did what, when, and from where. It is like a security camera for your cloud. Use it to investigate security incidents, meet compliance requirements, or track down who accidentally deleted a resource.

> [!important] IAM vs. Cognito vs. CloudTrail These three are commonly confused because they all relate to "users" and "access" — but they operate at completely different levels:
> 
> |Service|Controls access for...|Main purpose|
> |---|---|---|
> |**IAM**|AWS engineers / services|Who can manage AWS resources|
> |**Cognito**|Your app's end users|Sign-up and login for your application|
> |**CloudTrail**|Everyone (after the fact)|Auditing and logging all AWS actions|
> 
> Think of it this way: **IAM** is the employee badge system for your AWS team. **Cognito** is the login screen your customers see. **CloudTrail** is the security camera recording everything.

# Monitoring

## [[CloudWatch]] — Health Monitor for Your Infrastructure

CloudWatch collects metrics, logs, and events from your AWS services and lets you set up _alarms_ when something goes wrong. For example, you can trigger an alert if CPU usage on your EC2 instance goes above 80%, or automatically scale your servers based on a metric. It is your central dashboard for understanding how your application is performing.

# Serverless

## [[Lambda]] — Run Code Without Servers

Lambda lets you run a piece of code in response to an event — without needing to provision or manage any server. You upload a function, and AWS runs it only when triggered (for example, when a file is uploaded to S3, or when an API call comes in). You are charged only for the milliseconds your code actually runs.

## [[API Gateway]] — The Front Door for Your API

API Gateway creates, manages, and secures HTTP APIs that route requests to your backend services — most commonly Lambda functions. It handles things like authentication, rate limiting, and routing so your Lambda functions do not have to. Together, Lambda + API Gateway is the classic _serverless backend_ pattern.

> [!note] Lambda + API Gateway vs. EC2
> 
> - **EC2** runs a server _continuously_. You pay for it 24/7, even when no one is using it. You have full control and can run long-running processes.
> - **Lambda + API Gateway** runs code _only on demand_. You pay per request. There is no server to manage. It is ideal for event-driven workloads, APIs, or tasks that run in short bursts.
> - Rule of thumb: if the app is simple, event-driven, or needs to scale to zero, use Lambda. If it needs to run a long process, maintain state in memory, or requires custom software, use EC2.

# Messaging & Notifications

## [[SNS]] — Send Notifications at Scale

SNS (_Simple Notification Service_) is a messaging service that lets you send notifications to many subscribers at once. It can push messages to email addresses, SMS, mobile push notifications, or other AWS services like Lambda or SQS. Use it to alert users, trigger workflows, or fan out a message to multiple systems simultaneously.

> [!important] How to design an AWS application — layer by layer
> 
> When you get a question describing an application, build your answer from the bottom up:
> 
> 1. **Storage** — What data does the app need to store? Static files? Use [[S3]]. Structured records? Use [[RDS]]. High-speed lookups? Use [[DynamoDB]].
> 2. **Compute** — What runs your app logic? Need full control? Use [[EC2]]. Want to just deploy code? Use [[Elastic Beanstalk]]. Short event-driven tasks? Use [[Lambda]].
> 3. **Database** — Already covered in Storage, but double-check: relational vs. NoSQL, read-heavy vs. write-heavy.
> 4. **Delivery & Networking** — How does traffic reach your servers? Use [[ELB]] to balance load across instances. Use [[CloudFront]] to cache content globally and reduce latency. Use [[VPC]]/Subnets to isolate your infrastructure.
> 5. **Security** — Who can access what? [[IAM]] for AWS-level permissions. [[Cognito]] for app user login. [[CloudTrail]] for auditing.
> 6. **Monitoring & Scaling** — How do you keep it running smoothly? [[CloudWatch]] for alerts and metrics. [[Auto Scaling]] to handle traffic spikes.
> 
> **Always justify each service choice** based on the specific needs of the application:
> 
> - _Scalability_ — Will traffic vary a lot? Favor Auto Scaling, Lambda, DynamoDB.
> - _Cost_ — Is the workload sporadic? Favor Lambda (pay per use) over EC2 (always on).
> - _Security_ — Is sensitive data involved? Make sure IAM roles are tight, subnets are private, and CloudTrail is enabled.
> - _Reliability_ — Does it need to stay up? Use ELB + Auto Scaling + Multi-AZ RDS.

# Tags

#aws #cloud-computing #s3 #ec2 #lambda #rds #dynamodb #iam #cognito #cloudwatch #cloudtrail #cloudfront #elb #vpc #sns #api-gateway #elastic-beanstalk #serverless #exam-prep

> [!note] Definition 
> **AWS [[Lambda]]** is a **serverless compute service** provided by [[AWS]] that lets you run code without provisioning or managing servers. You simply upload your code, and Lambda handles everything required to run and scale it — from the infrastructure to the execution environment.

The core idea is **event-driven execution**: your code runs _only when triggered_, and you pay _only for the compute time consumed_. When no code is running, you pay nothing.

# How Lambda Works

At a high level, Lambda follows a simple three-step model:

|Step|What Happens|
|---|---|
|**1. Trigger**|An event source invokes your Lambda function|
|**2. Execute**|Lambda provisions an environment and runs your code|
|**3. Return**|The function returns a response (or writes output elsewhere)|

> [!important] 
> Lambda is **not a long-running server**. Each invocation is short-lived (up to **15 minutes** maximum). For longer workloads, consider [[EC2]] or [[ECS]].

# Core Concepts

## Functions

A **Lambda function** is the unit of deployment. It contains:

- Your **code** (the handler)
- A **runtime** ([[Node.js]], [[Python]], [[Java]], [[Go]], [[Ruby]], [[.NET]], etc.)
- **Configuration** (memory, timeout, environment variables, permissions)

The entry point of a function is called the **handler**:

```python
# Python Lambda handler example
def handler(event, context):
    name = event.get("name", "World")
    return {
        "statusCode": 200,
        "body": f"Hello, {name}!"
    }
```

- `event` — the data passed in by the trigger (a dictionary/object)
- `context` — runtime information about the invocation (request ID, time remaining, etc.)

## Triggers and Event Sources

Lambda functions are invoked by **event sources**. Common ones include:

|Event Source|Use Case|
|---|---|
|[[API Gateway]]|Build REST or HTTP APIs|
|[[S3]]|React to file uploads|
|[[DynamoDB]] Streams|Process database changes|
|[[SNS]] / [[SQS]]|Message-driven processing|
|[[CloudWatch]] Events / [[EventBridge]]|Scheduled or rule-based triggers|
|[[Cognito]]|Custom authentication flows|
|[[ALB]] (Application [[Load Balancer]])|Handle HTTP traffic directly|

> [!example] 
> A user uploads an image to an [[S3]] bucket. This triggers a Lambda function that automatically resizes the image and stores the thumbnail in another bucket — all without any server running in between.

## Execution Environment and Lifecycle

When Lambda receives an invocation, it goes through these phases:

1. **Init phase** — Lambda initializes the runtime and runs any code _outside_ your handler (imports, DB connections, etc.)
2. **Invoke phase** — Your handler runs with the event data
3. **Shutdown phase** — Lambda freezes or terminates the environment

> [!tip] Cold Starts vs Warm Starts
> 
> - **Cold start**: The first invocation spins up a new environment — takes slightly longer (milliseconds to seconds depending on runtime and package size).
> - **Warm start**: If the same environment is reused for a subsequent invocation, initialization is skipped — much faster.
> 
> To reduce cold starts: keep packages small, use **Provisioned Concurrency**, or choose lighter runtimes like Python or Node.js over Java.

## Concurrency

[[Lambda]] can handle **thousands of concurrent invocations** automatically. Each invocation gets its own isolated environment.

- **Concurrency** = number of invocations running simultaneously
- **Reserved Concurrency** — guarantees a set number of instances for a specific function
- **Provisioned Concurrency** — pre-warms environments to eliminate cold starts entirely

> [!warning] 
> Your [[AWS]] account has a **default concurrency limit of 1,000** across all Lambda functions in a region. You can request an increase, but be aware that one function going viral can starve others if concurrency is not reserved properly.

## Permissions and [[IAM]]

Lambda integrates tightly with [[IAM]] for security:

- **Execution Role** — an [[IAM]] role attached to the function that defines _what AWS resources the function can access_ (e.g., read from [[S3]], write to [[DynamoDB]])
- **Resource-based Policy** — controls _who or what can invoke the function_ (e.g., allow [[API Gateway]] to invoke it)

```json
// Minimal IAM execution role policy (allow writing logs)
{
  "Effect": "Allow",
  "Action": [
    "logs:CreateLogGroup",
    "logs:CreateLogStream",
    "logs:PutLogEvents"
  ],
  "Resource": "*"
}
```

> [!important] 
> Always follow the **principle of least privilege**: give your Lambda function only the permissions it actually needs.

## Layers

A **Lambda Layer** is a reusable package of libraries, dependencies, or custom runtimes that can be shared across multiple functions. Instead of bundling heavy dependencies inside every function's deployment package, you attach a layer.

> [!example] 
> You have 10 Lambda functions that all use the `pandas` library. Instead of packaging pandas into each one, you create a single Lambda Layer with pandas and attach it to all 10 functions.

## Destinations and Error Handling

When a function succeeds or fails asynchronously, you can route the result to a **destination**:

- On **success**: send to [[SQS]], [[SNS]], [[EventBridge]], or another Lambda
- On **failure**: send to a **Dead Letter Queue (DLQ)** via [[SQS]] or [[SNS]] for later inspection

Lambda also supports **retry behavior**:

- Synchronous invocations: no automatic retry (caller handles it)
- Asynchronous invocations: Lambda retries **up to 2 times** on failure

## Pricing Model

> [!note] 
> Lambda uses a **pay-per-use** model with no minimum fees.

You are charged based on:

| Dimension              | Detail                                              |
| ---------------------- | --------------------------------------------------- |
| **Number of requests** | First 1 million requests/month are free             |
| **Duration**           | Charged per millisecond, based on memory configured |
| **Memory**             | You choose between 128 MB and 10,240 MB             |

The more memory you allocate, the more CPU power Lambda gives the function. Sometimes **increasing memory reduces duration enough to lower total cost**.

# Deploying a Lambda Function

## Via AWS Console

The simplest approach — upload a `.zip` file or write code inline directly in the browser.

## Via AWS CLI

```bash
# Package and deploy a Lambda function
zip function.zip lambda_function.py

aws lambda create-function \
  --function-name my-function \
  --runtime python3.12 \
  --role arn:aws:iam::123456789:role/lambda-role \
  --handler lambda_function.handler \
  --zip-file fileb://function.zip
```

## Via Infrastructure as Code

Using [[Terraform]]:

```hcl
resource "aws_lambda_function" "example" {
  function_name = "my-function"
  runtime       = "python3.12"
  handler       = "lambda_function.handler"
  role          = aws_iam_role.lambda_exec.arn
  filename      = "function.zip"
}
```

Using [[AWS SAM]] (Serverless Application Model) or the [[Serverless Framework]] are also popular choices for Lambda-focused projects.

# Lambda vs Other Compute Options

|Feature|Lambda|[[EC2]]|[[ECS]] / [[Fargate]]|
|---|---|---|---|
|Server management|None|Full|Minimal|
|Scaling|Automatic|Manual / [[Auto Scaling]]|Configurable|
|Max runtime|15 minutes|Unlimited|Unlimited|
|Pricing|Per invocation|Per hour|Per resource used|
|Best for|Short event-driven tasks|Long-running workloads|Containerized apps|

# Common Use Cases

- **REST APIs** — paired with [[API Gateway]] to build fully serverless backends
- **Data processing** — transforming files uploaded to [[S3]] or streaming data from [[Kinesis]]
- **Scheduled jobs** — replacing cron jobs using [[EventBridge]] rules
- **Authentication** — custom authorizers in [[API Gateway]]
- **Chatbots and webhooks** — lightweight, always-available endpoints
- **Backend for mobile apps** — combined with [[Cognito]] and [[DynamoDB]]

> [!warning] Common Misconceptions
> 
> - Lambda is **not suitable for long-running tasks** (max 15 min). For those, use [[EC2]], [[Fargate]], or [[Step Functions]] to orchestrate multiple Lambdas.
> - Lambda functions are **stateless** — do not store state in memory between invocations. Use [[DynamoDB]], [[S3]], or [[ElastiCache]] for persistence.
> - More memory does not just mean more RAM — it also means **proportionally more CPU and network bandwidth**.

## Other Sources

- [AWS Lambda Official Documentation](https://docs.aws.amazon.com/lambda/latest/dg/welcome.html)
- [AWS Lambda Pricing](https://aws.amazon.com/lambda/pricing/)
- [Serverless Architectures on AWS — AWS Whitepaper](https://docs.aws.amazon.com/whitepapers/latest/serverless-architectures-lambda/welcome.html)
- [AWS Lambda Best Practices](https://docs.aws.amazon.com/lambda/latest/dg/best-practices.html)
- [GeeksforGeeks — AWS Lambda](https://www.geeksforgeeks.org/aws-lambda/)

# Tags

#cloud #aws #serverless #lambda #cloud-computing #event-driven #iac #devops #compute #api #networking
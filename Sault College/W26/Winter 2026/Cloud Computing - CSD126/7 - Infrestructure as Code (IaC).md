# What is the Problem with Manual Infrastructure?

Before understanding IaC, it helps to understand _why_ it was created. There are two traditional approaches to setting up cloud infrastructure, and both have serious drawbacks.

## Approach 1: Manual Setup via the Cloud Console

This means logging into the [[AWS]] (or [[Azure]], GCP, etc.) web interface and clicking through menus to configure each service one by one.

**Problems with this approach:**

- **Poor change management** — if you need to update something, you have to manually figure out what needs to be created, modified, or deleted.
- **Hard to keep environments consistent** — your dev, test, and production environments can easily drift apart because each was set up by hand.
- **Hard to automate** — clicking through a web UI cannot be scripted easily.

## Approach 2: Using the [[Amazon CLI]] or SDKs

This means writing scripts that use the [[AWS]] CLI or an SDK to configure services programmatically.

**Problems with this approach:**

- **Hard to maintain state** — there is no built-in way to know what the current state of your infrastructure is.
- **Hard to express dependencies** — scripts must be run in the correct order (e.g., you must create a [[VPC]] before adding a subnet to it), and managing that order manually is error-prone.
- **Still poor change management** — you still have to figure out what changed and update your scripts accordingly.

# Infrastructure as Code (IaC)

> [!note] Definition
>  **[[Infrastructure as Code]] (IaC)** is the practice of defining your cloud infrastructure in code files, and then using a tool to automatically build and manage that infrastructure based on those files.

Instead of saying _"first do this, then do that"_ (imperative), you say _"here is what my infrastructure should look like"_ (declarative), and the IaC tool figures out how to make it happen.

## Declarative vs. Imperative

| Style                  | Meaning                        | Example                                              |
| ---------------------- | ------------------------------ | ---------------------------------------------------- |
| **Imperative** (how)   | Step-by-step instructions      | "Create a VPC, then add subnets, then add instances" |
| **Declarative** (what) | Describe the desired end state | "I want a VPC, some subnets, and some instances"     |

> [!important] IaC is **declarative**. 
> You describe _what_ the infrastructure should look like, not _how_ to build it. The tool handles the "how" for you.

# The IaC Workflow

The basic IaC workflow has three steps:

1. **Plan** — The IaC tool compares your code files against the actual current state of your infrastructure and produces a _plan of changes_ (what needs to be created, modified, or deleted).
2. **Refine the plan** — You review the plan. If it looks wrong, you adjust your code files and plan again.
3. **Apply** — Once the plan is acceptable, you tell the tool to execute it. The tool makes the necessary API calls to update the real infrastructure and then updates its record of the current state.

> [!tip] 
> Always review the **plan** carefully before applying. This is your safety net — it lets you catch mistakes before anything real changes.

# Benefits of IaC

Using IaC solves all the problems listed above:

- **Consistent environments** — The code files are a single source of truth. Every environment (dev, test, prod) is built from the same definition.
- **Easy to automate** — The tool does all the work of updating infrastructure.
- **Easy to maintain state** — The tool tracks the current state of your infrastructure automatically.
- **Automatic dependency management** — The tool reads your code and figures out the correct order to create resources (e.g., it knows a subnet depends on a VPC).
- **Simple change management** — The tool compares your desired state with the actual state and figures out what needs to change.
- **Auditable** — Because IaC files are just code, they can be stored in [[Git]] (version control). Every change is a commit, so you have a full history of who changed what and when.

## IaC Tools

There are several IaC tools available. Here is a comparison:

| Tool                               | Notes                                                                                    |
| ---------------------------------- | ---------------------------------------------------------------------------------------- |
| **AWS CloudFormation**             | Works only with [[AWS]]                                                                  |
| **Azure Resource Manager / Bicep** | Works only with Azure                                                                    |
| **[[Terraform]]**                  | Industry standard; works across many platforms; uses a simple config language            |
| **OpenTofu**                       | Open-source fork of [[Terraform]]                                                        |
| **Pulumi**                         | Cross-platform; config written in general-purpose languages like Java, Go, or TypeScript |

> [!important] 
> **[[Terraform]]** is the industry standard and the focus of this lesson. It is widely used, supports many cloud platforms, and uses a simple human-readable configuration format.

# Introduction to Terraform

[[Terraform]] is a [[CLI]] tool made by HashiCorp. It:

- Supports many platforms through **providers** (e.g., AWS provider, Azure provider, etc.)
- Uses a simple configuration format called **[[HashiCorp Configuration Language|HCL]]** (HashiCorp Configuration Language)
- Takes your declarative configuration and automatically translates it into the correct imperative API calls to build your infrastructure

## Configuration Language: HCL

[[HashiCorp Configuration Language|HCL]] files end in `.tf`. Here is a small example of a complete Terraform config:

```hcl
terraform {
  required_version = ">= 1.14.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 6.0"
    }
  }
}

variable "aws_region" {
  type    = string
  default = "us-east-1"
}

provider "aws" {
  region = var.aws_region
}

resource "aws_vpc" "myvpc" {
  cidr_block = "10.0.0.0/16"
}
```

# Terraform Blocks

Most Terraform configuration is made up of **blocks**. A block has a type, optional labels, and a set of attributes inside curly braces.

```hcl
blocktype "label" "label" {
  attribute_name = value_expression
}
```

Here is a summary of all important block types:

| Block Type  | Purpose                                                                       |
| ----------- | ----------------------------------------------------------------------------- |
| `terraform` | General Terraform configuration (version, providers, backend)                 |
| `provider`  | Specifies a target platform (e.g., [[AWS]], Azure)                            |
| `resource`  | Configures a specific cloud resource (e.g., [[EC2]], [[VPC]], security group) |
| `variable`  | Stores a reusable value                                                       |
| `data`      | Fetches a value from the provider dynamically                                 |
| `output`    | Displays useful values after the tool runs                                    |
| `import`    | Synchronizes Terraform's state with already-existing infrastructure           |
| `module`    | Groups complex config into a simpler, reusable block                          |

## The `terraform` Block

Used for general Terraform settings: which version of Terraform is required, and which providers are needed.

```hcl
terraform {
  required_version = ">= 1.14.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 6.0"
    }
  }
}
```

## Terraform Backends

By default, Terraform stores its state file on your local disk. You can configure a **backend** to store it remotely (e.g., in [[Amazon S3]]), which enables team collaboration.

```hcl
terraform {
  backend "s3" {
    bucket  = "my-bucket"
    key     = "my-state-file.tfstate"
    region  = "us-east-1"
    profile = "MyProfile"
  }
}
```

# The `provider` Block

Tells Terraform which cloud platform to target and how to configure it (e.g., which region to use). Terraform uses the same credentials as the [[AWS]] [[CLI]] (the `.aws/credentials` file).

```hcl
provider "aws" {
  region = "us-east-1"
}
```

> [!tip] 
> You can define multiple provider blocks, even multiple of the same provider (e.g., two AWS providers pointing to two different regions).

# The `resource` Block

This is the most common block. Each resource block configures one specific cloud resource.

```hcl
resource "aws_vpc" "myvpc" {
  cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "az1_public" {
  vpc_id            = aws_vpc.myvpc.id
  availability_zone = "us-east-1a"
  cidr_block        = "10.0.0.0/24"
  tags = {
    Name = "my-subnet"
  }
}

resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.myvpc.id
}

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.myvpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }
}
```

## References and Dependency Management

Notice that `aws_subnet.az1_public` references `aws_vpc.myvpc.id` instead of hard-coding the VPC's ID. This is called a **reference**.

> [!important] References serve two purposes:
> 
> 1. They avoid hard-coding values that might change.
> 2. They form Terraform's **dependency graph** — Terraform sees that the subnet depends on the VPC and ensures the VPC is created _first_, automatically.

The reference format is: `<resource_type>.<resource_label>.<attribute>`

## The `variable` Block

Variables let you store reusable values and avoid repeating yourself. You access a variable using the `var.` prefix. 

```hcl
variable "aws_region" {
  type    = string
  default = "us-east-1"
}

provider "aws" {
  region = var.aws_region
}
```

## The `data` Block

Sometimes you need a value that is dynamic — something you cannot hard-code because it changes (like the ID of the latest Amazon Linux AMI). The `data` block fetches this information live from the provider.

```hcl
data "aws_ami" "example" {
  most_recent = true
  owners      = ["amazon"]
  filter {
    name   = "name"
    values = ["amzn-ami-*-x86_64-*"]
  }
}

resource "aws_instance" "web" {
  ami           = data.aws_ami.example.id
  instance_type = "t2.micro"
}
```

> [!example] 
> Common uses for `data` blocks: fetching the latest AMI ID, getting your AWS account ID, reading values from Parameter Store.

# The `output` Block

After Terraform finishes running, output blocks display useful values in the terminal — for example, the public IP address of a newly created server.

```hcl
resource "aws_instance" "web" {
  # ... attributes
}

output "web_server_public_ip" {
  value = aws_instance.web.public_ip
}
```

When you run `terraform apply`, you will see something like:

```
web_server_public_ip = 203.14.36.64
```


## The `import` Block

If you have infrastructure that already exists (created manually before you started using Terraform), you can use an `import` block to bring it under Terraform's management.

```hcl
import {
  to = aws_vpc.main
  id = "vpc-1a747d829fd983"
}

resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
}
```

### How to Import Existing Infrastructure

There are two approaches:

**Option 1: Write the resource block yourself**

1. Write both an `import` block and a `resource` block for each resource.
2. Run `terraform plan` and adjust the resource block until the plan shows _only imports_ — no additions, changes, or deletions.

**Option 2: Use code generation** Terraform can generate the resource block for you using the `--generate-config-out` option:

```bash
terraform plan --generate-config-out=genvpc.tf
```

This creates a new file with auto-generated resource blocks for all unimported resources. After generation, you must clean up the file:

- Remove unnecessary or default attributes
- Replace hard-coded IDs with references
- Move the resource block to the appropriate `.tf` file

> [!warning] **Imports should ONLY import.** 
> Do not try to import and modify infrastructure at the same time. Before applying, confirm that your plan shows _only imports_ and zero creates, updates, or deletes. Once importing is done, you can remove the import blocks (though it is not required — already-imported resources are simply ignored).

# Terraform State

[[Terraform]] tracks the current state of your infrastructure in a file called `terraform.tfstate`.

> [!note] The **state file** serves three purposes:
> 
> - Maps your configuration to real cloud resources
> - Detects what has changed since the last apply
> - Lets Terraform compute an accurate plan

This is why Terraform is so much better at change management than plain CLI scripts — it always knows what actually exists.

# The Terraform CLI Workflow

The three core commands you will use constantly are:

## 1. `terraform init`

Initializes Terraform's environment. This downloads the required providers specified in your `terraform` block.

```bash
$ terraform init

Initializing the backend...
Initializing provider plugins...
- Reusing previous version of hashicorp/aws from the dependency lock file
- Using previously-installed hashicorp/aws v6.35.1

Terraform has been successfully initialized!
```

> [!tip] 
> Run `terraform init` once when you start a new project or after adding a new provider.

## 2. `terraform plan`

Compares your code files with the actual infrastructure state and produces a plan of what needs to change. Nothing is actually modified at this step.

## 3. `terraform apply`

1. Calculates and shows you the plan one more time.
2. Asks for confirmation.
3. Executes the necessary API calls to apply the changes.
4. Updates the state file.

# Project Structure

Terraform configuration files are typically kept in a dedicated folder (separate from your application code). All `.tf` files in that folder are loaded automatically.

```
$ ls infra/
terraform.tf   vpc.tf   variables.tf   compute.tf
```

Terraform automatically detects dependencies across all these files, so resources are always created in the correct order regardless of which file they are in.

# Terraform Registry

Documentation for all providers and their resource types can be found at the official [Terraform Registry](https://registry.terraform.io/). This is where you look up what attributes are available for any given resource type (e.g., `aws_vpc`, `aws_instance`, etc.).

# What Terraform Does NOT Do

> [!warning] Terraform is a tool for managing **infrastructure design**. It is _not_ meant for:
> 
> - Managing **application data**
> - Starting, stopping, or restarting **VM instances**
> - Managing **user accounts**
> - Applying **runtime configuration**
> - Storing or managing **secrets**

For these tasks, you would use other tools (e.g., Ansible for configuration management, AWS Secrets Manager for secrets).

# Tags

#iac #terraform #aws #cloud #infrastructure #devops #hcl #cli #declarative #cloud-computing 
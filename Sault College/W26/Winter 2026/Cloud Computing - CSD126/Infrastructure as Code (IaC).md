## What is the Problem with Manual Infrastructure?

When building cloud infrastructure, the traditional approach is to log into a cloud console (like [[AWS]]), click through menus, and configure each service one by one. Alternatively, developers might use the [[CLI]] or SDKs to write scripts that run commands for each service.

Both approaches cause serious headaches:

**Problems with manual console setup:**

- _Poor change management_ — if something needs to change, you must manually figure out what to create, modify, or delete
- _Hard to keep environments consistent_ — your dev, test, and production environments easily drift apart
- _Hard to automate_ — humans clicking through screens cannot be scripted

**Problems with CLI/SDK scripts:**

- _No easy way to track state_ — the script doesn't know what already exists in the cloud
- _Hard to express dependencies_ — you must run scripts in the right order manually
- _Poor change management_ — same problem as manual setup: you must figure out what changed yourself

> [!important] Both manual and scripted approaches put the burden on **you** to figure out _how_ to build infrastructure, and _what_ has changed. IaC removes that burden.

---

## The Solution: Infrastructure as Code (IaC)

[[Infrastructure as Code]] flips the idea around. Instead of writing step-by-step instructions ("create a VPC, then create subnets, then add instances"), you simply **describe what the infrastructure should look like**, and let an IaC tool figure out how to build it.

> [!note] Definition: Infrastructure as Code **IaC** is the practice of defining cloud infrastructure in code files, using a _declarative_ approach. The IaC tool reads the files and determines the steps to make the real infrastructure match the description.

### Declarative vs Imperative

|Approach|Style|Example|
|---|---|---|
|**Imperative**|_How_ to build|"Create a VPC, then add subnets, then add instances"|
|**Declarative**|_What_ it should look like|"Here are my VPC, subnet, and instance configurations"|

IaC is **declarative** — you say _what_ you want, not _how_ to get there. The tool handles the "how."

---

## The Basic IaC Workflow

There are three phases in working with IaC:

1. **Plan** — The IaC tool compares your code files against the _actual current state_ of the infrastructure and produces a _plan of changes_ (what will be created, modified, or deleted)
2. **Refine** — You review the plan and adjust your code until the plan looks right
3. **Apply** — The IaC tool executes the plan, making API calls to update the real infrastructure and recording the new state

> [!tip] Think of it like a preview before committing. You always see _exactly_ what will change before anything actually changes in the cloud.

---

## Benefits of IaC

IaC solves all the problems mentioned above:

- **Consistent environments** — the code files are a _single source of truth_ for what infrastructure should look like, so dev/test/prod stay in sync
- **Easy to automate** — the tool does the work; no humans clicking
- **State tracking** — the tool knows the current state of infrastructure
- **Automatic dependency management** — the tool infers which resources must be created before others (e.g., a subnet needs its VPC first)
- **Simple change management** — the tool computes the diff between desired state and actual state automatically
- **Auditable** — IaC files can be stored in [[Git]] version control, giving you a full history of every infrastructure change

---

## IaC Tools Available

Several IaC tools exist. Here is a quick comparison:

|Tool|Notes|
|---|---|
|[[AWS]] CloudFormation|AWS only|
|Azure Resource Manager / Bicep|Azure only|
|[[Terraform]]|Industry standard, cross-platform, simple config language|
|OpenTofu|Open-source fork of Terraform|
|Pulumi|Cross-platform; write config in Java, Go, TypeScript, etc.|

> [!important] **[[Terraform]]** is the industry standard and the focus of this lecture. It works across many cloud platforms and uses a simple, readable configuration language.

---

## Terraform

[[Terraform]] is widely used in industry for several reasons:

- Supports many platforms through **providers** (AWS, Azure, GCP, and more)
- Uses a simple, human-readable configuration format
- The CLI tool reads your _declarative_ config and automatically translates it into the _imperative_ API calls needed to build infrastructure

### Configuration Language: HCL

Terraform uses **[[HCL]]** (HashiCorp Configuration Language) to write config files. These files are plain text with a `.tf` extension.

> [!example] A basic HCL configuration
> 
> ```hcl
> terraform {
>   required_version = ">= 1.14.0"
>   required_providers {
>     aws = {
>       source  = "hashicorp/aws"
>       version = "~> 6.0"
>     }
>   }
> }
> 
> variable "aws_region" {
>   type    = string
>   default = "us-east-1"
> }
> 
> provider "aws" {
>   region = var.aws_region
> }
> 
> resource "aws_vpc" "myvpc" {
>   cidr_block = "10.0.0.0/16"
> }
> ```

---

## Terraform Blocks

Almost everything in Terraform is written as a **block**. A block has a _type_, one or more _labels_, and a body of _attributes_.

**General structure:**

```hcl
blocktype "label" "label" {
  attribute_name = value_expression
}
```

> [!example] A resource block for a VPC
> 
> ```hcl
> resource "aws_vpc" "myvpc" {
>   cidr_block = "10.0.0.0/16"
> }
> ```
> 
> Here, `resource` is the block type, `"aws_vpc"` is the resource type label, and `"myvpc"` is the local name label.

### Overview of Important Block Types

|Block Type|Purpose|
|---|---|
|`terraform`|General Terraform settings (version, providers)|
|`provider`|Specify a target platform (AWS, Azure, etc.)|
|`resource`|Configure a specific resource (VPC, EC2, S3, etc.)|
|`variable`|Store reusable values|
|`data`|Fetch dynamic values from the provider|
|`output`|Display useful values after the tool finishes|
|`import`|Import config from already-existing infrastructure|
|`module`|Bundle complex config into a reusable block|

---

### The `terraform` Block

Used for general Terraform settings: specifying the required Terraform version and listing required providers.

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

#### Terraform Backends

A **backend** tells Terraform where to store its _state file_. By default, Terraform saves state on your local disk. For team environments, you can point it to a remote backend like [[S3]] so everyone shares the same state.

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

---

### The `provider` Block

Specifies which cloud platform to target and sets global configuration for all resources managed by that provider (e.g., region, credentials).

- For AWS, Terraform uses the same credentials as the [[AWS]] [[CLI]] (the `.aws/credentials` file)
- You can define _multiple providers_, including multiple AWS providers in different regions

```hcl
provider "aws" {
  region = "us-east-1"
}
```

---

### `terraform init`

Before doing anything else in a new project, run:

```bash
terraform init
```

This command **downloads the required providers** and sets up the local environment. You must run it once before `plan` or `apply`.

```
Initializing provider plugins...
- Using previously-installed hashicorp/aws v6.35.1
Terraform has been successfully initialized!
```

> [!tip] Documentation for all providers and block types is available at the **Terraform Registry**. It is your go-to reference when writing Terraform config.

---

### The `resource` Block

This is the most common block. Each _individual_ cloud resource (a [[VPC]], a subnet, an [[EC2]] instance, a security group, etc.) gets its own `resource` block.

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

#### References and the Dependency Graph

Instead of hard-coding IDs, you _reference_ other resources using the pattern `resource_type.local_name.attribute`.

> [!example] Reference example
> 
> ```hcl
> resource "aws_vpc" "myvpc" {
>   cidr_block = "10.0.0.0/16"
> }
> 
> resource "aws_internet_gateway" "igw" {
>   vpc_id = aws_vpc.myvpc.id   # <-- Reference to the VPC above
> }
> ```
> 
> Terraform reads this reference and knows: _the internet gateway depends on the VPC_, so it will create the VPC first.

> [!important] References are how Terraform builds its **dependency graph** — it automatically figures out the correct order to create resources without you having to worry about it.

---

### The `variable` Block

Stores reusable values that can be referenced throughout the config. Access variable values using the `var.` prefix.

```hcl
variable "aws_region" {
  type    = string
  default = "us-east-1"
}

provider "aws" {
  region = var.aws_region   # Using the variable
}
```

---

### The `data` Block

Used to **fetch dynamic values from the provider** rather than hard-coding them. Useful for things that change or that you don't want to look up manually.

Common examples:

- Get the AMI ID for the latest Amazon Linux image
- Get your AWS account ID
- Read parameters from AWS Parameter Store

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
  ami           = data.aws_ami.example.id   # Using the data source
  instance_type = "t2.micro"
}
```

---

### The `output` Block

Displays useful values in the terminal after `terraform apply` completes. Great for printing things like public IP addresses that you need after deployment.

```hcl
resource "aws_instance" "web" {
  # ... attributes
}

output "web_server_public_ip" {
  value = aws_instance.web.public_ip
}
```

After running `terraform apply`, you would see:

```
web_server_public_ip = 203.14.36.64
```

---

### The `import` Block

Allows you to bring **already-existing infrastructure** (resources not created by Terraform) under Terraform's management. The import block tells Terraform: "this real resource corresponds to this config block."

```hcl
resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
}

import {
  to = aws_vpc.main
  id = "vpc-1a747d829fd983"
}
```

#### Two Ways to Import

**Method 1: Manual resource block** Write both the `import` block and the matching `resource` block yourself, then run `terraform plan` and adjust the resource block until the plan shows _only imports_ and no creates/updates/deletes.

**Method 2: Code generation** Let Terraform generate the resource block automatically:

```bash
terraform plan -generate-config-out=genvpc.tf
```

This creates a `.tf` file with resource blocks for all unimported resources. You must then clean up the generated file: remove unnecessary attributes, replace hard-coded IDs with references, and move the block to an appropriate file.

> [!warning] Imports should ONLY import Do not try to import and update infrastructure at the same time. Always check that your plan shows **only imports** and **no create/update/delete** operations. Once importing is complete, import blocks can be removed (though leaving them in is harmless — already-imported resources are ignored).

---

## Terraform State

Terraform keeps track of the real-world state of your infrastructure in a file called **`terraform.tfstate`**.

> [!note] Purpose of the state file
> 
> - Maps your config resources to real cloud resources
> - Detects what has changed since the last apply
> - Computes what the next plan should do

---

## `terraform apply`

When you run `terraform apply`, Terraform:

1. Calculates and shows the plan (and asks you to confirm)
2. Executes the necessary API calls to make the changes
3. Updates the `terraform.tfstate` file to reflect the new state

---

## Project Structure

Terraform config files are typically stored in their own folder (separate from application code). Terraform automatically loads all `.tf` files in the folder and detects dependencies between them.

> [!example] Typical project layout
> 
> ```bash
> $ ls infra/
> terraform.tf   vpc.tf   variables.tf   compute.tf
> ```
> 
> Each file can contain any combination of blocks. Splitting by concern (networking, compute, variables) keeps things organized.

---

## Terraform Limitations

[[Terraform]] is a tool for managing **infrastructure design** — not everything. It is _not_ the right tool for:

- Managing **application data**
- Controlling **VM state** (starting, stopping, restarting instances)
- Managing **user accounts**
- Handling **runtime configuration**
- Storing **secrets**

> [!warning] Don't use Terraform for things it wasn't designed for. Use the right tool for each job — for example, use [[AWS]] Secrets Manager for secrets, and use a configuration management tool or startup scripts for runtime config.

---

## Tags

#iac #terraform #aws #cloud #infrastructure #devops #hcl #deployment #git
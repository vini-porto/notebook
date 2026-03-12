# Blocks

- Most terraform congiruration consists of blocks
- General structure
``` terraform
blocktype "label" "label" {
	attribute_name = value_expression
}
```

- Example
``` terraform
resource "aws_vpc" "myvpc" {
	cidr_block = "10.0.0.0/16"
}
```

# Important block types

| Type        | Purpose                                                                                                          |
| ----------- | ---------------------------------------------------------------------------------------------------------------- |
| `terraform` | Defines Terraform settings, such as required providers and backend configuration.                                |
| `provider`  | Configures a specific cloud provider (e.g., AWS, Azure, GCP) or service, including authentication details.       |
| `resource`  | Declares an infrastructure object to be managed by Terraform (e.g., a VPC, EC2 instance, S3 bucket).             |
| `data`      | Fetches information about existing infrastructure objects or external data sources for use in the configuration. |
| `variable`  | Defines input variables for a Terraform module, allowing configurations to be dynamic and reusable.              |
| `output`    | Defines output values for a Terraform module, making specific data available to parent modules or the user.      |
| `locals`    | Defines local variables for a module, allowing complex expressions to be named and reused within the module.     |
| `module`    | Calls and configures a reusable collection of Terraform configurations (a module) from a local path or registry. |

# Blocks - terraform

- General terraform config
- Specificaly terraform version
- Specify required providers (can be multiple!)

The `provider` block for AWS is missing. While the `terraform` block declares the required AWS provider, it doesn't configure it.

```terraform
terraform {
	required_version ">= 1.14.0"
	required_providers {
		aws = {
			source = "nashicorn/aws"
			version = "~> 6.0"
		}
	}
}

provider "aws" {
  region = "us-east-1" # Example region, adjust as needed
  # Add other provider-specific configurations here if needed
}
```


# Blocks - terraform backends

# Blocks - provider

- Specify a target plataform (e.g. AWS, Azure, etc.)
- Set configuration for all resources managed by the provider
	- E.g. region
``` terraform
	provider "aws" {
		region = "us-east-1"
	}
```

- Set credentials

# Blocks - resource

- Configure a specific resource (Security group, EC2 instance, Route, etc.)
- Each individual resource should have its own resource block
- Different resource types allow different attribute settings

# References

- Instead of hard-coding IDs and other values, references may be used in config files.

``` terraform
resource "aws vpc" "myvpc" {
	cidr_block = "10.0.0.0/16"
}
```

# Block - variable

- Store reusable values

``` terraform 
variable "aws region" {
	type = string
	default = "us-east-1"
}
```

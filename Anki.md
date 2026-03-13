START
Q: What is the general structure of a Terraform block?  
A:

```terraform
blocktype "label" "label" {
	attribute_name = value_expression
}
```

Q: What is the purpose of the `terraform` block in Terraform?  
A: Defines Terraform settings, such as required providers and backend configuration.

Q: What is the purpose of the `provider` block in Terraform?  
A: Configures a specific cloud provider (e.g., AWS, Azure, GCP) or service, including authentication details.

Q: What is the purpose of the `resource` block in Terraform?  
A: Declares an infrastructure object to be managed by Terraform (e.g., a VPC, EC2 instance, S3 bucket).

Q: What is the purpose of the `data` block in Terraform?  
A: Fetches information about existing infrastructure objects or external data sources for use in the configuration.

Q: What is the purpose of the `variable` block in Terraform?  
A: Defines input variables for a Terraform module, allowing configurations to be dynamic and reusable. It is also used to store reusable values.

Q: What is the purpose of the `output` block in Terraform?  
A: Defines output values for a Terraform module, making specific data available to parent modules or the user.

Q: What is the purpose of the `locals` block in Terraform?  
A: Defines local variables for a module, allowing complex expressions to be named and reused within the module.

Q: What does a `provider` block typically specify and configure?  
A: It specifies a target platform (e.g., AWS, Azure) and sets configuration for all resources managed by that provider, such as the region and credentials.

Q: Why are "references" used in Terraform configuration files?  
A: Instead of hard-coding IDs and other values, references are used to make configurations more dynamic and reusable.
END
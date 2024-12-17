variable "lambda_function_name" { default = "jll-conference-scheduler-function" }
variable "lambda_runtime"       { default = "java11" }
variable "lambda_handler"       {}
variable "role_arn"       {}
variable "s3_bucket"            {}
variable "s3_key"               {}
variable "memory_size"          { default = "512" }
variable "timeout"              { default = "180" }

provider "aws" {
  region = "us-east-2"
}

terraform {
  backend "s3" {
    bucket         = "darchan-terraform-statebucket"
    key            = "state/terraform.tfstate"
    region         = "us-east-2"
    dynamodb_table = "jdc-terraform-lock-table"
    encrypt        = true
  }
}


module "lambda" {
  source         = "./modules/lambda"
  lambda_function_name  = var.lambda_function_name
  lambda_handler = var.lambda_handler
  role_arn       = module.iam.role_arn
  s3_bucket      = var.lib_bucket
  s3_key         = var.jar_s3_key
}

module "api_gateway" {
  source           = "./modules/api_gateway"
  api_name         = "jll-scheduler-api"
  stage_name       = "development"
  lambda_function_arn = module.lambda.function_arn
}
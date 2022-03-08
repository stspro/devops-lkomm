resource "aws_cloudwatch_log_group" "log_group" {
  name = "openapi-devl-cw"
    tags = {
    Environment = "production"
  }
}

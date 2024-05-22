# Terraform HCL

provider "aws" {
  region = "us-east-1"
}
  
 #create SNS topic 
resource "aws_sns_topic" "message_topic" {
  name = "server_message_topic"
}

 #createSQS Queue
resource "aws_sqs_queue" "message_queue" {
  name = "server_message_queue"
  delay_seconds = 0
  message_rentention_seconds = 86400 # 1 day
}

# subscribe SQS queue to SNS topic
resource "aws_sns_topic_subscription" "queue_subscription" {
  topic_arn = aws_sns_topic.message_topic.arn
  protocol  = "sqs"
  endpoint  = aws_sqs_queue.message_queue.arn
}

import boto3
import datetime

#Initialize AWS clients
sns_client = boto3.client('sns' ,region_name = 'us-east-1')
topic_arn = 'arn:aws:sns:us-east-1:i-ami-0bb84b8ffd87024d8:server_message_topic'


#publish message to SNS topic_arn
def publish_message():
	message =f"Hello from server A at {datetime.datetime.now().strftime('%Y-%m-%d-%H:%M:%S')}"
	sns_client.publish(TopicArn=topic_arn, Message=message)
	print("Message published to SNS topic:", message)

if __name__ =="__main__":
	publish_message()


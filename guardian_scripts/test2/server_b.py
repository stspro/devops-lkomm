import boto3
import datetime

#Initialize AWS clients
sns_client = boto3.client('sns' ,region_name = 'us-east-1')
topic_arn = 'arn:aws:sns:us-east-1:ami-0bb84b8ffd87024d8_message_topic'


#Receive message from SQS queue
def receive_message():
	response = sqs_client,receive_message(QueueUrl=queue_url, MaxNumberOfMessages=1,WaitTimeSeconds=20)
	if ''Messages ' in response:
	message = response['Messages'][0]
	message_body = message['Body']
	timestamp = message_body.split()[-1]
	
	#write message to file
	filename + f"{timestamp}_message.log"
	with open(filename.'w') as file:
		file.write(message_body)
		
		
		print("Message received and returned to file:" , filename)
		
	else:
		print ("No messages available in the queue.")	
		

if __name__ =="__main__":
	receive_message()

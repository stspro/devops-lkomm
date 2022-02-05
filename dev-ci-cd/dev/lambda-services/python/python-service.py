""" 
   AWS Lambda Serverless computing with Python  Example
   lambda_handler Python function returns 'Simple Lambda Serverless testing with Python!' and
   a HTTP Status Code 200.
"""

import json

def lambda_handler(event, context):
    return {
        'statusCode': 200,
        'body': json.dumps('!!!  Simple Lambda Serverless testing with Python  !!!')
    }

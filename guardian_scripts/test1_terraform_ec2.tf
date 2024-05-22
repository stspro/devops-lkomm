provider "aws" {
  region     = "US-east-1"
  }

resource "aws_instance" "web_server" {
    ami = "ami-0c55b159cbfafe1f0"
    instance_type = "t2.micro"
    key_name = "ec2_keypair"
	
    
    tags = {
        Name = "web-server-instance"
    }
}

resource "aws_security_group" "web_server_sg" {
 name = "web_server_sg"
 description = "security group of the web server"


  
  ingress {
    from_port = 80
    protocol = "tcp"
    to_port = 80
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port       = 0
    to_port         = 0
    protocol        = "-1"
    cidr_blocks     = ["0.0.0.0/0"]
  }
  
  }


#Provisioner to install and configure Apache Web Server
 user_data = <<-EOF
      #!/bin/bash
      yum update -y
      yum install -y httpd
      sudo systemctl start httpd
      sudo systemctl enable httpd
      echo "<html><body><h1>Hello,World!<h1><body><html>" > /var/www/html/index.html
      EOF
	
    
}


# outputs
output"web_server_public_ip"{
  value =  aws_instance.web_server.public_ip
}

# Terraform HCL

provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "ubuntu" {
  ami           = "ami-07d0cf3af28718ef8"
  instance_type = "t2.micro"
  
  
   user_data = <<-EOF
	#!/bin/bash   
	sudo apt update -y
	sudo apt-cache search tomcat7.0 -y
	sudo apt install tomcat7.0 tomcat-webapps tomcat-admin-webapps -y
	cd /usr/share/tomcat/webapps/
	ss -ltn
	sudo wget <https://tomcat.apache.org/tomcat-7.0-doc/appdev/sample.war>
	sudo systemctl enable tomcat7.0
	
	EOF
  

  tags = {
    Name = "HelloTerraform"
  }
}

#!/bin/bash

launch_ec2_instance() {
		aws ec2 run-instances --image-id -0c55b159cbfafe1f0
		--instance-type t2.micro
		--key-name ec2test_keypair
		--security-group-ids<SeCURITY_Group_ID>
		--subnet-id<SUBNET_ID>
		--count 1
}

install_and_run_tomcat() {

	#Install Tomcat
	sudo yum install -y tomcat
	
	#start tomact service
	sudo service tomcat start
}

validate_and_start_tomcat() {
	if systemctl is -active  --quiet tomcat; then
		echo "Tomcat is running"
		sudo service tomcat start
	fi
}

main() {

	#launch ec2 instance
	launch_ec2_instance

	#install and run tomcat
	install_and_run_tomcat


	#validate and start tomcat
	validate_and_start_tomcat
}

#execute the main function
main


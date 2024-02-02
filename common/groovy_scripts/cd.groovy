
def stopAndRemoveDockerContainer(container_name, docker_image,build_version){
	println ">>>> Starting stopDockerContainer method >>>>"
	
	sh "docker container ls"
	
	//Stop Docker Container
	sh "docker stop $container_name"
	 
	//Remove Docker Container
	sh "docker rm -f  $container_name"
		
	sh "docker container ls"
	sh "docker ps"
}

def removeDockerImage( docker_image,build_version){

	println ">>>> Starting removeDockerImage method >>>>"
	
	sh "docker image ls"
	
	//Remove Docker Image
	sh "docker rmi -f $docker_image:$build_version"

	sh "docker image ls"
	
}

def startDockerContainer(docker_image,build_version, ingress_port, egress_port){

	println ">>>> Starting startDockerContainer method >>>>"

	sh "docker container ls"

	sh "docker run -d -p $ingress_port:$egress_port $docker_image:$build_version"

	sh "docker container ls"
	
	sh "docker ps"
}

return this



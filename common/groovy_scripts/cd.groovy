
def stopAndRemoveDockerContainerInDockerHub(container_name, docker_image,build_version){
	println ">>>> Starting stopDockerContainerInDockerHub method >>>>"
	
	sh "docker container ls"
	
	//Stop Docker Container
	sh "docker stop $container_name"
	 
	//Remove Docker Container
	sh "docker rm -f  $container_name"
		
	sh "docker container ls"
	sh "docker ps"
}

def removeDockerImageInDockerHub( docker_image,build_version){

	println ">>>> Starting removeDockerImageInDockerHub method >>>>"
	
	sh "docker image ls"
	
	//Remove Docker Image
	sh "docker rmi -f $docker_image:$build_version"

	sh "docker image ls"
	
}

def startDockerContainerInDockerHub(docker_image,build_version, ingress_port, egress_port){

	println ">>>> Starting startDockerContainerInDockerHub method >>>>"

	sh "docker container ls"

	sh "docker run -d -p $ingress_port:$egress_port $docker_image:$build_version"

	sh "docker container ls"
	
	sh "docker ps"
}

return this

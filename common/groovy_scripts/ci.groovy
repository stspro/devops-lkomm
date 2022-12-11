checkoutGitRepo(git_url, git_repo_branch){

	println ">>>> Starting checkoutGitRepo method >>>>"
	
	sh "ls -ltr"	
	git branch: git_repo_branch,    url: git_url	
	sh "ls -ltr"
}     

buildMavenPackage(project_name){

	println ">>>> Starting buildMavenPackage method >>>>"
	
	sh "ls -ltr"
	dir ("$project_name"){
		sh "mvn package "
	}
}

runUnitTestsMaven(project_name){

	println ">>>> Starting runUnitTestsMaven method >>>>"
	
	dir ("$project_name"){
		sh "mvn test "
	}
}

publishUnitTestReportToSonarMaven(project_dir,projectKey,sonarUrl,sonarLogin) {

	println ">>>> Starting publishUnitTestReportToSonarMaven method >>>>"
	
	dir(project_dir){
		//sh 'mvn clean package sonar:sonar'
		sh "mvn clean verify sonar:sonar -Dsonar.projectKey=$projectKey -Dsonar.host.url=$sonarUrl -Dsonar.login=$sonarLogin"
	}
}

//artifactory upload
uploadArtifacts(project_target_dir,package_name){

	println ">>>> Starting uploadArtifacts method >>>>"
	 
	dir (project_target_dir){
		archiveArtifacts package_name
	}
}

/**
*
*
*/
buildDockerImage(docker_build_folder,image_name,build_version){

	println ">>>> Starting buildDockerImage method >>>>"
	
    dir (docker_build_folder){
		sh "sudo docker build -t $image_name:$build_version ."
		// sh "docker scan nugensol:1.0"
    }
}

sendEmailNotification(email_body, email_subject, email_to){
	println ">>>> Starting sendEmailNotification method >>>>"
	
	emailext body: email_body, subject: email_subject, to: email_to
}


def readConfigYaml(yaml_file_location){
	println ">>>> Starting readConfigYaml method >>>>"
	
	def configfileObj = readYaml file: yaml_file_location
	
	return configfileObj
}

	
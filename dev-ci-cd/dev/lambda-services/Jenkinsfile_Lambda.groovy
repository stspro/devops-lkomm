node{
  def application_git_url =''
   def mvn_version = ''
   def package_name =''
  cleanWs()
  stage("Read Configuration"){    
     sh "ls -ltr"
     git branch: 'main',    url: 'https://github.com/stspro/nugensol.git'
     sh "ls -ltr"
     sh "mkdir -p dockerimage"
    sh "cp dev-ci-cd/dev/services/Dockerfile dockerimage"
     def configfile = readYaml file: 'dev-ci-cd/dev/services/config.yml'
     println configfile
     application_git_url =  configfile.git_url
     mvn_version = configfile.mvn_version
    package_name = "mkyong.war"
    println pipelines_env
    println "this is env: ${pipelines_env}"
     println configfile.jenkins_environment
  }
  stage("checkout Application"){
     git url: application_git_url  
     sh "ls -ltr"
     sh "java --version"        
  }  
 stage("build automation"){
   sh "ls -ltr"
   dir ("lambda-services/python"){
      zip lambda-services lambda.py
      sh "cp lambda-services.zip dev-ci-cd/dev/lambda-services/terraform"
   }
 }  
 stage("unit testing"){

 }
  
  stage("code analysis"){
  }
  
  stage("build management"){
  //artifactory upload
    dir ("lambda-services/python"){
     archiveArtifacts "lambda-services.zip"
    }    
  }

stage("Lambda Deployment"){
   dir ("dev-ci-cd/dev/lambda-services/terraform"){     
      sh "terraform init"
      sh "terraform apply -auto-approve"
     }
}


}

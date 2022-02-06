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


   dir ("web-thymeleaf-war/target"){
      sh "C:\DevOps\apache-tomcat-9.0.43\bin\catalina.sh shutdown"     
      sh "copy mkyong.war C:\\DevOps\\apache-tomcat-9.0.43\\webapps"
      sh "C:\DevOps\apache-tomcat-9.0.43\bin\catalina.sh start"
     
      sh "ssh -i ~/.ssh/idprivatekey ec2-user@ec2-44-201-116-194.compute-1.amazonaws.com 'ls -ltr'"
      sh "scp -i ~/.ssh/idprivatekey test2 ec2-user@ec2-44-201-116-194.compute-1.amazonaws.com:/home/ec2-user/ "
      sh "ssh -i ~/.ssh/idprivatekey ec2-user@ec2-44-201-116-194.compute-1.amazonaws.com 'ls -ltr'"
     }
}


}

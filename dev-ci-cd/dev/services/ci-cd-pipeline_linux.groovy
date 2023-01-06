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
   dir ("web-thymeleaf-war"){
      sh "mvn package "
   }
 }  
 stage("unit testing"){
   dir ("web-thymeleaf-war"){
      sh "mvn test "
   }
 }
  
  stage("code analysis"){
  }
  
  stage("build management"){
  //artifactory upload
  dir ("web-thymeleaf-war/target"){
   archiveArtifacts "mkyong.war"
  }
    
  }
  stage("build image"){
    sh "ls -ltr"

    sh "cp web-thymeleaf-war/target/mkyong.war dockerimage"
    dir ("dockerimage"){
     sh "sudo docker build -t simple-tomcat:1.0 ."
     sh "docker scan"
    }
  }
  stage("upload image to ecr"){
    sh "ls -ltr"

  }
  
  stage("deployment"){
    //infrastructure and software instllation steps
    sh "sudo apt install default-jdk"
    sh "java -version"
    sh "sudo ssh -i path_to_pem_file server_username@ip_address"
    sh "sudo wget https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.81/bin/apache-tomcat-8.5.81.tar.gz"
    sh "sudo tar xvzf apache-tomcat-8.5.81.tar.gz"
    
    //deploy steps
    sh "scp path_to_war/war_file_name.war server_username@ip_address:~"
    sh "ssh -i ~/.ssh/idprivatekey server_username@ip_address 'chmod 555 war_file_name.war'"
    sh "ssh -i ~/.ssh/idprivatekey server_username@ip_address 'ls -ltr war_file_name.war'"
    sh "ssh -i ~/.ssh/idprivatekey server_username@ip_address 'sh /opt/apache-tomcat-8.5.81/bin/shutdown.sh'"
    sh "ssh -i ~/.ssh/idprivatekey server_username@ip_address 'sh rm -Rf /opt/apache-tomcat-8.5.81/webapps/ROOT'"
    sh "ssh -i ~/.ssh/idprivatekey server_username@ip_address 'mv war_file_name.war /opt/apache-tomcat-8.5.81/webapps/ROOT.war'"
    sh "ssh -i ~/.ssh/idprivatekey server_username@ip_address 'cd /opt/apache-tomcat-8.5.81/bin && sh startup.sh'"
    //&& tail -f logs/catalina.out
    


    /*dir ("dockerimage"){
      sh "docker ps"
      sh "docker run -d -p 8081:8080 simple-tomcat:1.0"
            sh "docker ps"
   //   sh  "docker stop spring-boot:1.0"*/
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
  

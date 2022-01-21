node{
  
  cleanWs()
  stage("checkout"){
    
     sh "mkdir docker-springboot"
     sh "cd docker-springboot"
     sh "ls -ltr"
    //git branch: 'main',    url: 'https://github.com/stspro/nugensol.git'
     git branch: 'master',    url: 'https://github.com/stspro/spring-boot.git'
    
     def configfile = readYaml file: 'dev-ci-cd/dev/services/config.yml'
     println configfile
     println configfile.git_url
     println configfile.mvn_version
     println configfile.jenkins_environment
     git url: configfile.git_url
    
     def amap = ['something': 'my datas',
                    'size': 3,
                    'isEmpty': false]

      writeJSON file: 'groovy1.json', json: amap
      def read = readJSON file: 'groovy1.json'

      assert read.something == 'my datas'
      assert read.size == 3
      assert read.isEmpty == false
  
      sh "java --version"
        
  }
  
   stage("build automation"){
   sh "ls -ltr"
   dir ("web-thymeleaf-war"){
   sh "mvn package "
      }
  }
  
  stage("unit testing"){
     
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
     dir ("web-thymeleaf-war/target")
     sh "sudo docker build -t spring-boot:1.0 ."
     sh "docker scan"
  }
  stage("upload image to ecr"){
    sh "ls -ltr"

  }
  
  stage("deployment"){
    sh "docker ps"
    sh "docker run -d -p 8080:8080 spring-boot:1.0"
    sh  "docker stop spring-boot:1.0"


   // dir ("web-thymeleaf-war/target"){
      //bat "copy mkyong.war C:\\DevOps\\apache-tomcat-9.0.43\\webapps"
    // }
    //copy web-thymeleaf-war/target/mkyong.war $TOMCAT/webapps/
   //start Tomcat
  //C:\DevOps\apache-tomcat-9.0.43\webapps
}


}
  


























node{
  stage("checkout"){
 
    git url: "https://github.com/stspro/nugensol.git"

    bat "dir *.*/s"
    def configfile = readYaml file: 'config.yml'    
        println configfile.git_url
     println configfile.mvn_version
     println configfile.jenkins-pipieline-type
     println configfile.jenkins_environment
    git url: configfile.git_url
  }
    
     //dir ("web-thymeleaf-war")
     //   bat "cd web-thymeleaf-war"
     //copy web-thymeleaf-war/target/mkyong.war $TOMCAT/webapps/
   //start Tomcat
   //localhost:8080/mkyong
  
  stage("build automation"){
   // mvn package
    //bat cd web-thymeleaf-war;
    dir ("web-thymeleaf-war"){
    bat "mvn package"
     bat "dir *.*/s"
    }
  }
  
  stage("unit testing"){
     dir ("web-thymeleaf-war"){
    bat "mvn test"
 
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
  stage("deployment"){
    dir ("web-thymeleaf-war/target"){
      bat "copy mkyong.war C:\\DevOps\\apache-tomcat-9.0.43\\webapps"
    }
  }
    //copy web-thymeleaf-war/target/mkyong.war $TOMCAT/webapps/
   //start Tomcat
  //C:\DevOps\apache-tomcat-9.0.43\webapps
}
  

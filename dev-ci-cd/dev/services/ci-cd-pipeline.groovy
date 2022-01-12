node{
  stage("checkout"){
   
   git url: "https://github.com/stspro/spring-boot.git"
     //dir ("web-thymeleaf-war")
     bat "dir *.*/s"
//   bat "cd web-thymeleaf-war"
  
   //copy web-thymeleaf-war/target/mkyong.war $TOMCAT/webapps/
   //start Tomcat
   //localhost:8080/mkyong
  }
  stage("build automation"){
   // mvn package
    //bat cd web-thymeleaf-war;
    dir ("web-thymeleaf-war"){
    bat "mvn package"
     bat "dir *.*/s"
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
}
  

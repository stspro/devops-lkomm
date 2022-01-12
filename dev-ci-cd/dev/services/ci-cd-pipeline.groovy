node{
  stage("checkout")
   
   git clone "https://github.com/stspro/spring-boot.git"
     //dir (")
     bat "dir *.*/s"
//   bat "cd web-thymeleaf-war"
  
   //copy web-thymeleaf-war/target/mkyong.war $TOMCAT/webapps/
   //start Tomcat
   //localhost:8080/mkyong
  
  stage("build automation"){
   // mvn package
  }
  
  stage("unit testing"){
  }
  
  stage("code analysis"){
  }
  
  stage("build management"){
  //artifactory upload
  }
  
  

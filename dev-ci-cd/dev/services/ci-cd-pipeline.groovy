node{
  stage("checkout"){
 
    
    git branch: 'main',    url: 'https://github.com/stspro/nugensol.git'

    //bat "dir *.*/s"
    def configfile = readYaml file: 'dev-ci-cd/dev/services/config.yml'
    println configfile
        println configfile.git_url
     println configfile.mvn_version
     println configfile.jenkins_environment
    git url: configfile.git_url
  }
  
  stage("build automation"){
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

    deployment()
  }
    //copy web-thymeleaf-war/target/mkyong.war $TOMCAT/webapps/
   //start Tomcat
  //C:\DevOps\apache-tomcat-9.0.43\webapps
}

deployment(){
  println "in deployment function............."
    dir ("web-thymeleaf-war/target"){
      bat "copy mkyong.war C:\\DevOps\\apache-tomcat-9.0.43\\webapps"
    }
}
  

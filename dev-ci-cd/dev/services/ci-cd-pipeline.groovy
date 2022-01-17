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
    
  
    
        def amap = ['something': 'my datas',
                    'size': 3,
                    'isEmpty': false]

        writeJSON file: 'groovy1.json', json: amap
        def read = readJSON file: 'groovy1.json'

        assert read.something == 'my datas'
        assert read.size == 3
        assert read.isEmpty == false
    
    zip zipFile: 'test.zip', archive: true, dir: 'web-thymeleaf-war'
    archiveArtifacts artifacts: 'test.zip', fingerprint: true

    
    bat "dir *.*/s"
    
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

    dir ("web-thymeleaf-war/target"){
      bat "copy mkyong.war C:\\DevOps\\apache-tomcat-9.0.43\\webapps"
  }
    //copy web-thymeleaf-war/target/mkyong.war $TOMCAT/webapps/
   //start Tomcat
  //C:\DevOps\apache-tomcat-9.0.43\webapps
}


}
  

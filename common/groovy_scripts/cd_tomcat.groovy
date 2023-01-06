def deploywebappontomcat(username,ip_address,privatekey,tomcat,app_war){
  //def username= "ec2_user"
  //def ip_address="12.1.23.124"
  //def privatekey="~/.ssh/idprivatekey"
  //def tomcat="/opt/apache-tomcat-8.5.81"
  //def app_war="war_file_name.war"

	println ">>>> copying war file to tomcat folder and stsrting up the server >>>>"
	
	sh "scp path_to_war/war_file_name.war server_username@$ip_address:~"
    sh "ssh -i $privatekey $username@12.1.23.124 'chmod 555 $app_war'"
    sh "ssh -i $privatekey $username@$ip_address 'ls -ltr $app_war'"
    sh "ssh -i $privatekey $username@$ip_address 'sh $tomcat/bin/shutdown.sh'"
    sh "ssh -i $privatekey $username@$ip_address 'sh rm -Rf $tomcat/webapps/ROOT'"
    sh "ssh -i $privatekey $username@$ip_address 'mv war_file_name.war $tomcat/webapps/ROOT.war'"
    sh "ssh -i $privatekey $username@$ip_address 'cd $tomcat/bin && sh startup.sh'"
}

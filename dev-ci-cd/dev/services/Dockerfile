FROM tomcat:8.0-alpine

RUN mkdir /opt/app/

# cd /opt/app
WORKDIR /opt/app

RUN /usr/bin/curl -O https://downloads.apache.org/tomcat/tomcat-8/v8.5.73/bin/apache-tomcat-8.5.73-deployer.tar.gz
RUN tar xvfz apache*.tar.gz
RUN mv apache-tomcat-8.5.40/* /opt/tomcat/.
RUN yum -y install java
RUN java -version


EXPOSE 8080

CMD [“catalina.sh”, “run”]














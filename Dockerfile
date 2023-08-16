FROM tomcat:latest
COPY /target/tech-store.war /usr/local/tomcat/webapps/technology.war
EXPOSE 8080
CMD ["catalina.sh", "run"]



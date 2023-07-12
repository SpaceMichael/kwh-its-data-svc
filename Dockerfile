FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
WORKDIR /opt/app
COPY ${JAR_FILE} /opt/app/kwh-eform-svc.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/app/kwh-eform-svc.jar"]

FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
WORKDIR /opt/app
COPY ${JAR_FILE} /opt/app/kcc-spring-boot-svc.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/app/kcc-spring-boot-svc.jar"]

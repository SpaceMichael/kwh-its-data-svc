FROM registry.access.redhat.com/ubi9/openjdk-21-runtime
ARG JAR_FILE=target/*.jar
WORKDIR /opt/app
COPY ${JAR_FILE} /opt/app/kwh-its-data-svc.jar
COPY cacerts /opt/app
EXPOSE 8080
ENTRYPOINT ["java","-Djavax.net.ssl.trustStore=/opt/app/cacerts","-Djavax.net.ssl.trustStorePassword=changeit","-jar","/opt/app/kwh-its-data-svc.jar"]

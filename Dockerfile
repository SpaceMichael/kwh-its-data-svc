FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
RUN mkdir -p /root/.m2
COPY ./.mvn/settings.xml /root/.m2/
COPY ./ ./
RUN keytool -import -noprompt -trustcacerts -alias ha-root-ca -file ha-root-ca.cer -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit
RUN keytool -import -noprompt -trustcacerts -alias ha-trial-root-ca -file ha-trial-root-ca.cer -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit
RUN mvn clean install

FROM eclipse-temurin:21-jre
ARG JAR_FILE=target/*.jar
WORKDIR /opt/app
COPY ha-root-ca.cer ha-trial-root-ca.cer /opt/app/
RUN keytool -import -noprompt -trustcacerts -alias ha-root-ca -file ha-root-ca.cer -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit
RUN keytool -import -noprompt -trustcacerts -alias ha-trial-root-ca -file ha-trial-root-ca.cer -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit
COPY --from=builder ${JAR_FILE} /opt/app/kcc-spring-boot-svc.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/app/kcc-spring-boot-svc.jar"]
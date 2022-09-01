# KCC Create Spring Boot Service

## Table of Contents <!-- omit in toc -->
- [1. Setup Development Environment](#1-setup-development-environment)
  - [1.1. Install Java Development Kit (OpenJDK)](#11-install-java-development-kit-openjdk)
  - [1.2. Install Apache Maven](#12-install-apache-maven)
  - [1.3. Install / Setup Visual Studio Code](#13-install--setup-visual-studio-code)
  - [1.4. Install Git](#14-install-git)
- [2. Generate Spring Boot Project with Spring Initializr](#2-generate-spring-boot-project-with-spring-initializr)
- [3. Update Maven `pom.xml` for Dependent Libraries](#3-update-maven-pomxml-for-dependent-libraries)
- [4. Copy Common Libraries](#4-copy-common-libraries)
- [5. Update Source Files](#5-update-source-files)
- [6. Configure VS Code and Maven](#6-configure-vs-code-and-maven)
- [7. Copy Deployment Files](#7-copy-deployment-files)
- [8. Run the container on Docker Desktop at Local Machine](#8-run-the-container-on-docker-desktop-at-local-machine)

## 1. Setup Development Environment

### 1.1. Install Java Development Kit (OpenJDK)

* Download [OpenJDK 11 (Windows/x64 JDK)](https://jdk.java.net/java-se-ri/11)
* Extract the downloaded zip file to `C:\Java\jdk-11`
* Set the System Variables:
  - System properties (WinKey + Pause) > "Change settings" > "Advanced" tab > "Environment Variables"
  - Add New System Variables:
    - JAVA_HOME: `C:\Java\jdk-11`
  - Update `PATH` System Variables:
    - Adding: `%JAVA_HOME%\bin`

### 1.2. Install Apache Maven

* [Donwload Binary zip archive](https://maven.apache.org/download.cgi)
* [Installing Apache Maven](https://maven.apache.org/install.html)
  - Unzip to: `D:\Users\<Username>\Tools\apache-maven-3.6.3`
  - System properties (WinKey + Pause) > "Change settings" > "Advanced" tab > "Environment Variables"
  - Add at User variable PATH: `D:\Users\<Username>\Tools\apache-maven-3.6.3\bin`
  - New command prompt:
		```shell
		$ mvn -v
		```
* Proxy Setting:
  - Create a file at: `D:\Users\<Username>\.m2\settings.xml`
      ```xml
      <settings>
          <proxies>
              <proxy>
                  <id>ha-proxy</id>
                  <active>true</active>
                  <protocol>http</protocol>
                  <host>proxy.ha.org.hk</host>
                  <port>8080</port>
                  <username>[Internet ID]</username>
                  <password>[Internet Password]</password>
                  <nonProxyHosts>idfartif|*.ha.org.hk|*.home|localhost|127.0.0.0/8</nonProxyHosts>
              </proxy>
          </proxies>
      </settings>
      ```

### 1.3. Install / Setup Visual Studio Code

* [Download VS Code](https://code.visualstudio.com/)
* Insall VS Code Extenstions:
  - Java Extension Pack
  - Spring Boot Extension Pack
  - Lombok Annotations Support for VS Code
  - Docker
  - GitLens
* Proxy Setting 1:
  - System properties (WinKey + Pause) > "Change settings" > "Advanced" tab > "Environment Variables"
  - Add User Variables:
    - HTTP_PROXY: `http://[username]:[password]@proxy.ha.org.hk:8080`
    - HTTPS_PROXY: `http://[username]:[password]@proxy.ha.org.hk:8080`
* Proxy Setting 2 (optional - configured with setting 1):_
    - VS Code > Manage > Settings (Ctrl+,) > Search Settings: "Proxy"
    - Http: Proxy: `http://[username]:[password]@proxy.ha.org.hk:8080`

### 1.4. Install Git

* [Install Latest Release for Windows](https://git-scm.com/)
* [Getting Started - First-Time Git Setup](https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup)
  - View all Git settings
		```shell
		$ git config --list --show-origin
		```
  - Setup Identity
		```shell
		$ git config --global user.name "Your Name"
		$ git config --global user.email username@ha.org.hk
		```
* Import `HA Root CA1` certificate to Git certificate trust store
  - Check Git certificate trust store location:
		```shell
		$ git config --get http.sslcainfo
		```
  - Export `HA Root CA1` cert from URL: 
    - Open in browser: https://hagithub.home/
    - View certificates > Certification Path > Select root cert > View Certificate > Details > Copy To File...
    - Certificate Export Wizard > Next > Select: Base-64 encoded X.509 (.CER) > Browse... > Named as `ha-root-ca1.cer` > Next > Finish
  - Copy and paste the content of the `ha-root-ca1.cer` file to the end of the Git certificate trust store file (e.g. `C:/Program Files/Git/mingw64/ssl/certs/ca-bundle.crt`)

## 2. Generate Spring Boot Project with Spring Initializr

* Generate Spring Boot Project with Spring initializr:
  * URL: https://start.spring.io/
  * Project: Maven Project
  * Language: Java
  * Spring Boot: 2.7.3
  * Project Metadata
    * Group: hk.org.ha.kcc
    * Artifact: kcc-spring-boot-svc
    * Name: kcc-spring-boot-svc
    * Description: KCC Spring Boot Service
    * Package name: hk.org.ha.kcc.spring
    * Packaging: Jar
    * Java: 11
  * Dependencies
    * Developer Tools:
      * Spring Boot Dev Tools
      * Lombok
    * Web:
      * Spring Web
      * Spring Reactive Web
    * Security
      * Spring Security
    * SQL
      * Spring Data JPA
      * H2 Database
      * MS SQL Server Driver
    * I/O
      * Validation
    * OPS
      * Spring Boot Actuator
  * Click `GENERATE`

* Save and extract generated package `kcc-spring-boot-svc.zip` to `D:\Users\<Username>\Workspaces`.

* Open the project with VS Code and press 'F5' to trial run the project.

* Initialize the Git repository
  ```sh
  $ cd D:\Users\<Username>\Workspaces\kcc-spring-boot-svc
  $ git init
  $ git add .
  $ git commit -m "Initialize project using Spring Initializr"
  ```

## 3. Update Maven `pom.xml` for Dependent Libraries

* Update `pom.xml` for dependent libraries:
  ```xml
	<properties>
    ...
    <org.mapstruct.version>1.5.2.Final</org.mapstruct.version>
    <hibernate-jpamodelgen.version>5.6.10.Final</hibernate-jpamodelgen.version>
  </properties>
  ...
		<dependency>
			<groupId>org.mapstruct</groupId>
			<artifactId>mapstruct</artifactId>
			<version>${org.mapstruct.version}</version>
		</dependency>
		<dependency>
			<groupId>org.hibernate.orm</groupId>
			<artifactId>hibernate-jpamodelgen</artifactId>
			<version>${hibernate-jpamodelgen.version}</version>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-api</artifactId>
			<version>0.11.2</version>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-impl</artifactId>
			<version>0.11.2</version>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-jackson</artifactId>
			<version>0.11.2</version>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-ui</artifactId>
			<version>1.6.9</version>
		</dependency>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-ext</artifactId>
			<version>1.7.36</version>
		</dependency>
		<dependency>
			<groupId>hk.org.ha.audit</groupId>
			<artifactId>ha-app-audit-als-k8s</artifactId>
			<version>4.3.1</version>
		</dependency>
    ...
		<dependency>
			<groupId>org.awaitility</groupId>
			<artifactId>awaitility</artifactId>
			<version>4.2.0</version>
			<scope>test</scope>
		</dependency>
	...
	<build>
		<plugins>
		  ...
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>${java.version}</source>
					<target>${java.version}</target>
					<annotationProcessorPaths>
						<path>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
							<version>${lombok.version}</version>
						</path>
						<path>
							<groupId>org.mapstruct</groupId>
							<artifactId>mapstruct-processor</artifactId>
							<version>${org.mapstruct.version}</version>
						</path>
						<path>
							<groupId>org.hibernate</groupId>
							<artifactId>hibernate-jpamodelgen</artifactId>
							<version>${hibernate-jpamodelgen.version}</version>
						</path>
					</annotationProcessorPaths>
					<compilerArgs>
						<compilerArg>-Amapstruct.defaultComponentModel=spring</compilerArg>
					</compilerArgs>
				</configuration>
			</plugin>
	...
  ```

## 4. Copy Common Libraries

* Reference to latest Spring Boot projects, for examples:
  * [KCC e-Ward Logbook - API Service](https://hagithub.home/KCC-Clinical/qeh-eward-logbook-svc)
  * [SMS Communication System - API Service](https://hagithub.home/KCC-Clinical/sms-comm-svc)
  * [ICU Bed Booking System - API Service](https://hagithub.home/KCC-Clinical/icu-booking-svc)
* Create folders and copies files at `src\main\java`:
	* hk.org.ha.kcc.common.data.AuditorAwareImpl.java
	* hk.org.ha.kcc.common.data.PrefixedSequenceIdGenerator.java
	* hk.org.ha.kcc.common.logging.AlsXLogger.java
	* hk.org.ha.kcc.common.logging.AlsXLoggerFactory.java
	* hk.org.ha.kcc.common.logging.LogType.java
	* hk.org.ha.kcc.common.security.JwtAuthenticationEntryPoint.java
	* hk.org.ha.kcc.common.security.JwtTokenFilter.java
	* hk.org.ha.kcc.common.security.JwtTokenUtil.java
	* hk.org.ha.kcc.common.security.UserDetailsImpl.java
	* hk.org.ha.kcc.common.web.HttpHeaders.java
	* hk.org.ha.kcc.common.web.ServiceRequestInterceptor.java

## 5. Update Source Files

* Add `ComponentScan` at `hk.org.ha.kcc.springboot.KccSpringBootSvcApplication`:
	```java
	import org.springframework.context.annotation.ComponentScan;

	@SpringBootApplication
	@ComponentScan(basePackages = "hk.org.ha.kcc")
	public class KccSpringBootSvcApplication {
	```
* Add configuration classes
  * hk.org.ha.kcc.springboot.config.JpaConfig
  * hk.org.ha.kcc.springboot.config.OpenApiConfig (update title)
  * hk.org.ha.kcc.springboot.config.WebMvcConfig
  * hk.org.ha.kcc.springboot.config.WebSecurityConfig
* Add folder under `src\main\java\hk\org\ha\kcc\springboot`:
  * controller
  * controller\PatientApiController.java
  * dto
  * mapper
  * model
  * repository
  * service
* Update Test folder `src\test`:
  * src\test\resources\logback-test.xml
  * src\test\java\hk\org\ha\kcc\med\doctor\config\JpaTestConfig.java (update @EnableJpaRepositories, @EntityScan)
* Update `src\main\resources\application.properties`:
  ```
  # Enable gracefule shutdown
  server.shutdown=graceful
  # Inclode error message in response
  server.error.include-message=always
  # Allow grace timeout period for 20 seconds
  spring.lifecycle.timeout-per-shutdown-phase=20s

  # Spring Boot Actuator
  management.endpoint.health.probes.enabled=true

  # Logging
  logging.level.hk.org.ha.kcc=debug

  # JWT
  app.jwt.secret=${JWT_SECRET}
  app.jwt.expirationMs=1200000
  ```
* Add `src\main\resources\application-default.properties`:
  ```
  # Spring Data JPA
  spring.datasource.url=jdbc:h2:mem:kcc_spring_boot;INIT=CREATE SCHEMA IF NOT EXISTS KCC_SPRING_BOOT
  spring.datasource.username=sa
  spring.datasource.password=
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=false
  spring.jpa.properties.hibernate.format_sql=false
  ```
* Add `src\main\resources\application-dev.properties`:
  ```
  # Spring Data JPA
  spring.datasource.url=jdbc:sqlserver://kwhdocker01.corp.ha.org.hk:1433;database=kcc_spring_boot_dev;encrypt=false
  spring.datasource.username=${DB_USER}
  spring.datasource.password=${DB_PASS}
  spring.jpa.hibernate.ddl-auto=none
  spring.jpa.show-sql=false
  spring.jpa.properties.hibernate.format_sql=false
  ```
* Add `src\main\resources\application-prod.properties`:
  ```
  # Spring Data JPA
  spring.datasource.url=jdbc:sqlserver://DC7KWHIDB01.corp.ha.org.hk;instanceName=KWHI_DC7_MP1;databaseName=kcc_spring_boot;encrypt=false;failoverPartner=DC6KWHIDB01.corp.ha.org.hk\KWHI_DC6_MP1
  spring.datasource.username=${DB_USER}
  spring.datasource.password=${DB_PASS}
  spring.jpa.hibernate.ddl-auto=none
  spring.jpa.show-sql=false
  spring.jpa.properties.hibernate.format_sql=false
  ```

## 6. Configure VS Code and Maven

* Add `envFile` to `.vscode/launch.json` for VS Code Debugger:
  ```json
  {
    "configurations": [
      {
        ...
        "cwd": "${workspaceFolder}",
        "console": "internalConsole",
        ...
        "args": "",
        "envFile": "${workspaceFolder}/.env"
      }
    ]
  }
  ```
* Add `envFile` to `.vscode/settings.json` for VS Code Testing:
  ```json
  {
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.test.config": {
      "envFile": "${workspaceFolder}/.env"
    }
  }
  ```
* Add environment variables to `.env` for VS Code and Docker Compose:
  ```
  JWT_SECERT=[secert]
  DB_USER=[username]
  DB_PASS=[password]
  ```
* Add environment variables to `.mvn/maven.config` for Maven Testing:
  ```
  -DJWT_SECERT=[secert]
  -DDB_USER=[username]
  -DDB_PASS=[password]
  ```
* Update `.gitignore` to add:
	```
	.mvn/jvm.config
	.mvn/maven.config
	.env
	```
* At VS Code, press 'F5' to trial run the Spring Boot project 
* Open Swaggger OpenAPI Specification page in browser: http://localhost:8080

## 7. Copy Deployment Files

* Reference to latest Spring Boot projects, for examples:
  * [KCC e-Ward Logbook - API Service](https://hagithub.home/KCC-Clinical/qeh-eward-logbook-svc)
  * [SMS Communication System - API Service](https://hagithub.home/KCC-Clinical/sms-comm-svc)
  * [ICU Bed Booking System - API Service](https://hagithub.home/KCC-Clinical/icu-booking-svc)
* Create folders and copies files:
  * .github\workflows\development.yml
  * .github\workflows\production.yml
  * openshift\kcc-spring-boot-svc.yaml (update filename and app name)
  * openshift-stg\kcc-spring-boot-svc.yaml (update filename and app name)
  * docker-compose.yml (update app name)
  * Dockerfile (update jar filename)
* At GitHub repository, add MAVEN_CONFIG with content of `.mvn\maven.config`
  * GitHub repository > Settings > Secrets > New repository secret

## 8. Run the container on Docker Desktop at Local Machine

* Run the container from `docker-compose.yml` file:
  ```shell
  $ cd D:\Users\<Username>\Workspaces\kcc-spring-boot-svc
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kcc-spring-boot-svc .
  $ docker tag kcc-spring-boot-svc docker-registry-default.cldpaast71.server.ha.org.hk/kccnonc-kcc-dev/kcc-spring-boot-svc
  
  $ docker-compose config
  $ docker-compose down
  $ docker-compose up -d
  $ docker logs -f kcc-spring-boot-svc
  ```
* Open Swaggger OpenAPI Specification page in browser: http://localhost:30000

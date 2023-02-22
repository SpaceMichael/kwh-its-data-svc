# KCC Spring Boot Service - Base Project

| Env.    | Git Branch | Database        | URL                                                                             |
| ------- | ---------- | --------------- | ------------------------------------------------------------------------------- |
| Staging | main       | kcc_spring_boot | https://kcc-spring-boot-svc-kccnonc-kcc-staging-prd.cldpaasp61.server.ha.org.hk |
| PROD    | main (tag) | kcc_spring_boot | https://kcc-spring-boot-svc-kccnonc-kcc-prd.cldpaasp61.server.ha.org.hk         |

## Table of Contents <!-- omit in toc -->
- [1. Configure VS Code](#1-configure-vs-code)
- [2. Configure Maven](#2-configure-maven)
- [3. Run `kcc-spring-boot-svc` container on Docker Desktop at Local Machine](#3-run-kcc-spring-boot-svc-container-on-docker-desktop-at-local-machine)
- [4. Deploy `kcc-spring-boot-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccnonc-kcc-staging-prd`)](#4-deploy-kcc-spring-boot-svc-to-openshift-at-ha-private-cloud-staging-production-kccnonc-kcc-staging-prd)
- [5. Deploy `kcc-spring-boot-svc` to OpenShift at HA Private Cloud Production (`kccnonc-kcc-prd`)](#5-deploy-kcc-spring-boot-svc-to-openshift-at-ha-private-cloud-production-kccnonc-kcc-prd)

## 1. Configure VS Code
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
        "env": {
                "SPRING_PROFILES_ACTIVE": "dev"
        },
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
  JWT_SECRET=[secret]
  DB_USER=[username]
  DB_PASS=[password]
  ```

## 2. Configure Maven
* Add environment variables to `.mvn/maven.config` for Maven Testing:
  ```
  -DJWT_SECRET=[secret]
  -DDB_USER=[username]
  -DDB_PASS=[password]
  ```

## 3. Run `kcc-spring-boot-svc` container on Docker Desktop at Local Machine
* Run `kcc-spring-boot-svc` container from `docker-compose.yml` file:
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

## 4. Deploy `kcc-spring-boot-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccnonc-kcc-staging-prd`)
* Build, Tag and Push `kcc-spring-boot-svc` image:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kcc-spring-boot-svc .
  $ docker tag kcc-spring-boot-svc docker-registry-default.cldpaasp61.server.ha.org.hk/kccnonc-kcc-staging-prd/kcc-spring-boot-svc
  
  $ oc login https://cldpaasp61-asm.server.ha.org.hk:8443
  $ oc project kccnonc-kcc-staging-prd
  $ docker login -u $(oc whoami) -p $(oc whoami -t) docker-registry-default.cldpaasp61.server.ha.org.hk
  $ docker push docker-registry-default.cldpaasp61.server.ha.org.hk/kccnonc-kcc-staging-prd/kcc-spring-boot-svc
  $ oc get is kcc-spring-boot-svc
  ```
* Deploy `kcc-spring-boot-svc` service with OC commands:
  ```shell
  $ cd D:\Users\<Username>\Workspaces\kcc-spring-boot-svc
  $ oc apply -f openshift-stg\kcc-spring-boot-svc.yaml
  $ oc get pod
  $ oc logs -f kcc-spring-boot-svc-57cb8ff78f-qctht
  $ oc get route
  ```

## 5. Deploy `kcc-spring-boot-svc` to OpenShift at HA Private Cloud Production (`kccnonc-kcc-prd`)
* Build, Tag and Push `kcc-spring-boot-svc` image:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kcc-spring-boot-svc .
  $ docker tag kcc-spring-boot-svc docker-registry-default.cldpaasp61.server.ha.org.hk/kccnonc-kcc-prd/kcc-spring-boot-svc:1.0
  
  $ oc login https://cldpaasp61-asm.server.ha.org.hk:8443
  $ oc project kccnonc-kcc-prd
  $ docker login -u $(oc whoami) -p $(oc whoami -t) docker-registry-default.cldpaasp61.server.ha.org.hk
  $ docker push docker-registry-default.cldpaasp61.server.ha.org.hk/kccnonc-kcc-prd/kcc-spring-boot-svc
  $ oc get is kcc-spring-boot-svc
  ```
* Deploy `kcc-spring-boot-svc` service with OC commands:
  ```shell
  $ cd D:\Users\<Username>\Workspaces\kcc-spring-boot-svc
  $ oc apply -f openshift\kcc-spring-boot-svc.yaml
  $ oc get pod
  $ oc logs -f kcc-spring-boot-svc-57cb8ff78f-qctht
  $ oc get route
  ```

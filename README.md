# KCC Spring Boot Service - Base Project

| Env.    | Git Branch | Database        | URL                                                                        |
| ------- | ---------- | --------------- | -------------------------------------------------------------------------- |
| Staging | main       | kcc_spring_boot | https://kcc-spring-boot-svc-kccclinical-stag-prd.prdcld61.server.ha.org.hk |
|         |            |                 | https://kcc-spring-boot-svc-kccclinical-stag-prd.prdcld71.server.ha.org.hk |
| PROD    | main (tag) | kcc_spring_boot | https://kcc-spring-boot-svc-kccclinical-prd.prdcld61.server.ha.org.hk      |
|         |            |                 | https://kcc-spring-boot-svc-kccclinical-prd.prdcld71.server.ha.org.hk      |

## Table of Contents <!-- omit in toc -->
- [1. Configure VS Code](#1-configure-vs-code)
- [2. Configure Maven](#2-configure-maven)
- [3. Run `kcc-spring-boot-svc` container on Docker Desktop at Local Machine](#3-run-kcc-spring-boot-svc-container-on-docker-desktop-at-local-machine)
- [4. Deploy `kcc-spring-boot-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-stag-prd`)](#4-deploy-kcc-spring-boot-svc-to-openshift-at-ha-private-cloud-staging-production-kccclinical-stag-prd)
- [5. Deploy `kcc-spring-boot-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)](#5-deploy-kcc-spring-boot-svc-to-openshift-at-ha-private-cloud-production-kccclinical-prd)

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
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kcc-spring-boot-svc .
  $ docker-compose config
  $ docker-compose down
  $ docker-compose up -d
  $ docker logs -f kcc-spring-boot-svc
  ```

## 4. Deploy `kcc-spring-boot-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-stag-prd`)
* Build, Tag and Push `kcc-spring-boot-svc` image:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kcc-spring-boot-svc .
  $ docker tag default-route-openshift-image-registry.prdcld61.server.ha.org.hk/kccclinical-stag-prd/kcc-spring-boot-svc
  
  $ oc login -u [username] https://api.prdcld61.server.ha.org.hk:6443
  $ oc project kccclinical-stag-prd
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.prdcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.prdcld61.server.ha.org.hk/kccclinical-stag-prd/kcc-spring-boot-svc
  $ oc get is kcc-spring-boot-svc
  ```
* Deploy `kcc-spring-boot-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift-stg\kcc-spring-boot-svc.yaml
  $ oc get pod
  $ oc logs -f kcc-spring-boot-svc-57cb8ff78f-qctht
  $ oc get route
  ```

## 5. Deploy `kcc-spring-boot-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)
* Build, Tag and Push `kcc-spring-boot-svc` image:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kcc-spring-boot-svc .
  $ docker tag kcc-spring-boot-svc default-route-openshift-image-registry.prdcld61.server.ha.org.hk/kccclinical-prd/kcc-spring-boot-svc
  
  $ oc login -u [username] https://api.prdcld61.server.ha.org.hk:6443
  $ oc project kccclinical-prd
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.prdcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.prdcld61.server.ha.org.hk/kccclinical-prd/kcc-spring-boot-svc
  $ oc get is kcc-spring-boot-svc
  ```
* Deploy `kcc-spring-boot-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift\kcc-spring-boot-svc.yaml
  $ oc get pod
  $ oc logs -f kcc-spring-boot-svc-57cb8ff78f-qctht
  $ oc get route
  ```

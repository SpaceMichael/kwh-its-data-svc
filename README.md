# KCC Spring Boot Service - Base Project

| Env.    | Git Branch | Database        | URL                                                                                                                                                         |
| ------- | ---------- | --------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Staging | main       |                 | https://kwh-eform-svc-kccclinical-dev.tstcld61.server.ha.org.hk |
| PROD    | main (tag) |                 | https://kwh-eform-svc-kccclinical-prd.tstcld61.server.ha.org.hk |

## Table of Contents <!-- omit in toc -->
- [1. Configure VS Code](#1-configure-vs-code)
- [2. Configure Maven](#2-configure-maven)
- [3. Run `kwh-eform-svc` container on Docker Desktop at Local Machine](#3-run-kwh-eform-svc-container-on-docker-desktop-at-local-machine)
- [4. Deploy `kwh-eform-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-dev`)](#4-deploy-kwh-eform-svc-to-openshift-at-ha-private-cloud-staging-production-kccclinical-dev)
- [5. Deploy `kwh-eform-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)](#5-deploy-kwh-eform-svc-to-openshift-at-ha-private-cloud-production-kccclinical-prd)

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

## 3. Run `kwh-eform-svc` container on Docker Desktop at Local Machine
* Run `kwh-eform-svc` container from `docker-compose.yml` file:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-eform-svc .
  $ docker-compose config
  $ docker-compose down
  $ docker-compose up -d
  $ docker logs -f kwh-eform-svc
  ```

## 4. Deploy `kwh-eform-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-dev`)
* Build, Tag and Push `kwh-eform-svc` image:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-eform-svc .
  $ docker tag default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-dev/kwh-eform-svc
  
  $ oc login -u [username] https://api.tstcld61.server.ha.org.hk:6443
  $ oc project kccclinical-dev
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.tstcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-dev/kwh-eform-svc
  $ oc get is kwh-eform-svc
  ```
* Deploy `kwh-eform-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift-stg\kwh-eform-svc.yaml
  $ oc get pod
  $ oc logs -f kwh-eform-svc-57cb8ff78f-qctht
  $ oc get route
  ```

## 5. Deploy `kwh-eform-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)
* Build, Tag and Push `kwh-eform-svc` image:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-eform-svc .
  $ docker tag kwh-eform-svc default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-prd/kwh-eform-svc
  
  $ oc login -u [username] https://api.tstcld61.server.ha.org.hk:6443
  $ oc project kccclinical-prd
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.tstcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-prd/kwh-eform-svc
  $ oc get is kwh-eform-svc
  ```
* Deploy `kwh-eform-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift\kwh-eform-svc.yaml
  $ oc get pod
  $ oc logs -f kwh-eform-svc-57cb8ff78f-qctht
  $ oc get route
  ```

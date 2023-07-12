# KCC Spring Boot Service - Base Project

| Env.    | Git Branch | Database        | URL                                                                                                                                                         |
| ------- | ---------- | --------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Staging | main       |                 | https://kwh-eforms-svc-kccclinical-dev.tstcld61.server.ha.org.hk |
| PROD    | main (tag) |                 | https://kwh-eforms-svc-kccclinical-prd.tstcld61.server.ha.org.hk |

## Table of Contents <!-- omit in toc -->
- [1. Configure VS Code](#1-configure-vs-code)
- [2. Configure Maven](#2-configure-maven)
- [3. Run `kwh-eforms-svc` container on Docker Desktop at Local Machine](#3-run-kwh-eforms-svc-container-on-docker-desktop-at-local-machine)
- [4. Deploy `kwh-eforms-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-dev`)](#4-deploy-kwh-eforms-svc-to-openshift-at-ha-private-cloud-staging-production-kccclinical-dev)
- [5. Deploy `kwh-eforms-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)](#5-deploy-kwh-eforms-svc-to-openshift-at-ha-private-cloud-production-kccclinical-prd)

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

## 3. Run `kwh-eforms-svc` container on Docker Desktop at Local Machine
* Run `kwh-eforms-svc` container from `docker-compose.yml` file:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-eforms-svc .
  $ docker-compose config
  $ docker-compose down
  $ docker-compose up -d
  $ docker logs -f kwh-eforms-svc
  ```

## 4. Deploy `kwh-eforms-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-dev`)
* Build, Tag and Push `kwh-eforms-svc` image:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-eforms-svc .
  $ docker tag kwh-eforms-svc default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-dev/kwh-eforms-svc
  
  $ oc login -u [username] https://api.tstcld61.server.ha.org.hk:6443
  $ oc project kccclinical-dev
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.tstcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-dev/kwh-eforms-svc
  $ oc get is kwh-eforms-svc
  ```
* Deploy `kwh-eforms-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift-stg\kwh-eforms-svc.yaml
  $ oc get pod
  $ oc logs -f kwh-eforms-svc-57cb8ff78f-qctht
  $ oc get route
  ```

## 5. Deploy `kwh-eforms-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)
* Build, Tag and Push `kwh-eforms-svc` image:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-eforms-svc .
  $ docker tag kwh-eforms-svc default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-prd/kwh-eforms-svc
  
  $ oc login -u [username] https://api.tstcld61.server.ha.org.hk:6443
  $ oc project kccclinical-prd
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.tstcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-prd/kwh-eforms-svc
  $ oc get is kwh-eforms-svc
  ```
* Deploy `kwh-eforms-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift\kwh-eforms-svc.yaml
  $ oc get pod
  $ oc logs -f kwh-eforms-svc-57cb8ff78f-qctht
  $ oc get route
  ```

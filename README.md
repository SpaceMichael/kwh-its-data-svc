# KWH ITS Eform Service - API Service

| Env.        | Git Branch | Database | URL                                                                                                                                                     |
| ----------- | ---------- | -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Development | main       | N/A      | https://kwh-its-menu-svc-kccclinical-dev.tstcld61.server.ha.org.hk                                                                                     |
| Staging     | main       | N/A      | https://kwh-its-menu-svc-kccclinical-stag-prd.prdcld61.server.ha.org.hk <br/> https://kwh-its-menu-svc-kccclinical-stag-prd.prdcld71.server.ha.org.hk |
| PROD        | main (tag) | N/A      | https://kwh-its-menu-svc-kccclinical-prd.prdcld61.server.ha.org.hk <br/> https://kwh-its-menu-svc-kccclinical-prd.prdcld71.server.ha.org.hk           |

## Table of Contents <!-- omit in toc -->
- [1. Configure VS Code](#1-configure-vs-code)
- [2. Configure Maven](#2-configure-maven)
- [3. Run `kwh-its-menu-svc` container on Docker Desktop at Local Machine](#3-run-kwh-its-menu-svc-container-on-docker-desktop-at-local-machine)
- [4. Deploy `kwh-its-menu-svc` to OpenShift at HA Private Cloud Non-Production (`kccclinical-dev`)](#4-deploy-kwh-its-menu-svc-to-openshift-at-ha-private-cloud-non-production-kccclinical-dev)
- [5. Deploy `kwh-its-menu-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-stag-prd`)](#5-deploy-kwh-its-menu-svc-to-openshift-at-ha-private-cloud-staging-production-kccclinical-stag-prd)
- [6. Deploy `kwh-its-menu-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)](#6-deploy-kwh-its-menu-svc-to-openshift-at-ha-private-cloud-production-kccclinical-prd)

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

## 3. Run `kwh-its-menu-svc` container on Docker Desktop at Local Machine
* Run `kwh-its-menu-svc` container from `docker-compose.yml` file:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-its-menu-svc .
  $ docker-compose config
  $ docker-compose down
  $ docker-compose up -d
  $ docker logs -f kwh-its-menu-svc
  ```

## 4. Deploy `kwh-its-menu-svc` to OpenShift at HA Private Cloud Non-Production (`kccclinical-dev`)
* Build, Tag and Push `kwh-its-menu-svc` image:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-its-menu-svc .
  $ docker tag kwh-its-menu-svc default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-dev/kwh-its-menu-svc
  
  $ oc login -u [username] https://api.tstcld61.server.ha.org.hk:6443
  $ oc project kccclinical-dev
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.tstcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-dev/kwh-its-menu-svc
  $ oc get is kwh-its-menu-svc
  ```
* Deploy `kwh-its-menu-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift-dev\kwh-its-menu-svc.yaml
  $ oc get pod
  $ oc logs -f kwh-its-menu-svc-845d67f8f5-t79dt
  $ oc get route
  ```

## 5. Deploy `kwh-its-menu-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-stag-prd`)
* Build, Tag and Push `kwh-its-menu-svc` image:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-its-menu-svc .
  $ docker tag default-route-openshift-image-registry.prdcld61.server.ha.org.hk/kccclinical-stag-prd/kwh-its-menu-svc
  
  $ oc login -u [username] https://api.prdcld61.server.ha.org.hk:6443
  $ oc project kccclinical-stag-prd
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.prdcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.prdcld61.server.ha.org.hk/kccclinical-stag-prd/kwh-its-menu-svc
  $ oc get is kwh-its-menu-svc
  ```
* Deploy `kwh-its-menu-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift-stg\kwh-its-menu-svc.yaml
  $ oc get pod
  $ oc logs -f kwh-its-menu-svc-57cb8ff78f-qctht
  $ oc get route
  ```

## 6. Deploy `kwh-its-menu-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)
* Build, Tag and Push `kwh-its-menu-svc` image:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-its-menu-svc .
  $ docker tag kwh-its-menu-svc default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-prd/kwh-its-menu-svc
  
  $ oc login -u [username] https://api.tstcld61.server.ha.org.hk:6443
  $ oc project kccclinical-prd
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.tstcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-prd/kwh-its-menu-svc
  $ oc get is kwh-its-menu-svc
  ```
* Deploy `kwh-its-menu-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift\kwh-its-menu-svc.yaml
  $ oc get pod
  $ oc logs -f kwh-its-menu-svc-57cb8ff78f-qctht
  $ oc get route
  ```

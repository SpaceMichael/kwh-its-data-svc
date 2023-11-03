# KWH ITS Eform Service - API Service

| Env.        | Git Branch | Database | URL                                                                                                                                                   |
| ----------- | ---------- | -------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- |
| Development | main       | kwh_its  | https://kwh-its-data-svc-kccclinical-dev.tstcld61.server.ha.org.hk                                                                                    |
| Staging     | main       | kwh_its  | https://kwh-its-data-svc-kccclinical-stag-prd.prdcld61.server.ha.org.hk <br/> https://kwh-its-data-svc-kccclinical-stag-prd.prdcld71.server.ha.org.hk |
| PROD        | main (tag) | kwh_its  | https://kwh-its-data-svc-kccclinical-prd.prdcld61.server.ha.org.hk <br/> https://kwh-its-data-svc-kccclinical-prd.prdcld71.server.ha.org.hk           |
|             |

## Table of Contents <!-- omit in toc -->
- [1. Configure VS Code](#1-configure-vs-code)
- [2. Configure Maven](#2-configure-maven)
- [3. Run `kwh-its-data-svc` container on Docker Desktop at Local Machine](#3-run-kwh-its-data-svc-container-on-docker-desktop-at-local-machine)
- [4. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud Non-Production (`kccclinical-dev`)](#4-deploy-kwh-its-data-svc-to-openshift-at-ha-private-cloud-non-production-kccclinical-dev)
- [4. Deploy `mssql` to OpenShift at HA Private Cloud Non-Production (`kccclinical-dev`)](#4-deploy-mssql-to-openshift-at-ha-private-cloud-non-production-kccclinical-dev)
- [5. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud Non-Production (`kccclinical-dev`)](#5-deploy-kwh-its-data-svc-to-openshift-at-ha-private-cloud-non-production-kccclinical-dev)
- [5. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-stag-prd`)](#5-deploy-kwh-its-data-svc-to-openshift-at-ha-private-cloud-staging-production-kccclinical-stag-prd)
- [6. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-stag-prd`)](#6-deploy-kwh-its-data-svc-to-openshift-at-ha-private-cloud-staging-production-kccclinical-stag-prd)
- [6. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)](#6-deploy-kwh-its-data-svc-to-openshift-at-ha-private-cloud-production-kccclinical-prd)
- [7. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)](#7-deploy-kwh-its-data-svc-to-openshift-at-ha-private-cloud-production-kccclinical-prd)
>>>>>>> 614fc30c1e38ae9ead05f2b521b58e13ebc606fb

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

## 3. Run `kwh-its-data-svc` container on Docker Desktop at Local Machine
* Run `kwh-its-data-svc` container from `docker-compose.yml` file:
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-its-data-svc .
  $ docker-compose config
  $ docker-compose down
  $ docker-compose up -d
  $ docker logs -f kwh-its-data-svc
  ```

<<<<<<< HEAD
## 4. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud Non-Production (`kccclinical-dev`)
* Build, Tag and Push `kwh-its-data-svc` image:
=======
## 4. Deploy `mssql` to OpenShift at HA Private Cloud Non-Production (`kccclinical-dev`)
* Tag and Push `mssql` image:
  ```shell
  $ docker pull mcr.microsoft.com/mssql/server:2022-latest
  $ docker tag mcr.microsoft.com/mssql/server:2022-latest artifactrepo.server.ha.org.hk:55743/int_docker_dev/projects/kcc-non/intranet/mssql:2022-latest

  $ docker login -u [username] artifactrepo.server.ha.org.hk:55743
  $ docker push artifactrepo.server.ha.org.hk:55743/int_docker_dev/projects/kcc-non/intranet/mssql:2022-latest
  
  $ oc login -u [username] --server=https://api.cldkwhtst1.server.ha.org.hk:6443
  $ oc project kccclinical-dev
  $ oc create secret docker-registry jfrog-secret --docker-server artifactrepo.server.ha.org.hk:55743 --docker-username=[serviceusername] --docker-password='[password]' -n kccclinical-dev
  $ oc import-image mssql:2022-latest --from artifactrepo.server.ha.org.hk:55743/int_docker_dev/projects/kcc-non/intranet/mssql:2022-latest --insecure --confirm --reference-policy=local -n kccclinical-dev
  ```
* Deploy `mssql` service with OC commands:
  ```shell
  $ oc create secret generic mssql-db-secret --from-literal=mssql.db.user=[db_user] --from-literal=mssql.db.password=[db_password]
  $ oc get secret mssql-db-secret
  
  $ oc apply -f openshift-dev\mssql.yaml
  $ oc get pod --selector='app=mssql'
  $ oc logs -f mssql-689cc8699c-nmt7k
  ```
* Run `sqlcmd` utility in `mssql` pod:
  ```
  $ oc exec -ti mssql-689cc8699c-nmt7k -- /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P [password]

  > SELECT name FROM sys.databases
  > GO
  > QUIT
  ```
* Forward local 1433 port to `mysql` pod:
  ```
  $ oc port-forward mssql-689cc8699c-nmt7k 1433:1433
  ```

## 5. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud Non-Production (`kccclinical-dev`)
* Build, Tag and Push `kwh-its-data-svc` image:
>>>>>>> 614fc30c1e38ae9ead05f2b521b58e13ebc606fb
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-its-data-svc .
  $ docker tag kwh-its-data-svc default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-dev/kwh-its-data-svc
  
  $ oc login -u [username] https://api.tstcld61.server.ha.org.hk:6443
  $ oc project kccclinical-dev
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.tstcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-dev/kwh-its-data-svc
  $ oc get is kwh-its-data-svc
  ```
* Deploy `kwh-its-data-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift-dev\kwh-its-data-svc.yaml
  $ oc get pod
  $ oc logs -f kwh-its-data-svc-845d67f8f5-t79dt
  $ oc get route
  ```

<<<<<<< HEAD
## 5. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-stag-prd`)
* Build, Tag and Push `kwh-its-data-svc` image:
=======
## 6. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud (Staging) Production (`kccclinical-stag-prd`)
* Build, Tag and Push `kwh-its-data-svc` image:
>>>>>>> 614fc30c1e38ae9ead05f2b521b58e13ebc606fb
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-its-data-svc .
  $ docker tag default-route-openshift-image-registry.prdcld61.server.ha.org.hk/kccclinical-stag-prd/kwh-its-data-svc
  
  $ oc login -u [username] https://api.prdcld61.server.ha.org.hk:6443
  $ oc project kccclinical-stag-prd
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.prdcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.prdcld61.server.ha.org.hk/kccclinical-stag-prd/kwh-its-data-svc
  $ oc get is kwh-its-data-svc
  ```
* Deploy `kwh-its-data-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift-stg\kwh-its-data-svc.yaml
  $ oc get pod
  $ oc logs -f kwh-its-data-svc-57cb8ff78f-qctht
  $ oc get route
  ```

<<<<<<< HEAD
## 6. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)
* Build, Tag and Push `kwh-its-data-svc` image:
=======
## 7. Deploy `kwh-its-data-svc` to OpenShift at HA Private Cloud Production (`kccclinical-prd`)
* Build, Tag and Push `kwh-its-data-svc` image:
>>>>>>> 614fc30c1e38ae9ead05f2b521b58e13ebc606fb
  ```shell
  $ mvn clean install
  # $ mvn clean install -DskipTests
  
  $ docker build -t kwh-its-data-svc .
  $ docker tag kwh-its-data-svc default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-prd/kwh-its-data-svc
  
  $ oc login -u [username] https://api.tstcld61.server.ha.org.hk:6443
  $ oc project kccclinical-prd
  $ docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.tstcld61.server.ha.org.hk
  $ docker push default-route-openshift-image-registry.tstcld61.server.ha.org.hk/kccclinical-prd/kwh-its-data-svc
  $ oc get is kwh-its-data-svc
  ```
* Deploy `kwh-its-data-svc` service with OC commands:
  ```shell
  $ oc apply -f openshift\kwh-its-data-svc.yaml
  $ oc get pod
  $ oc logs -f kwh-its-data-svc-57cb8ff78f-qctht
  $ oc get route
  ```

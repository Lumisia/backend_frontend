# Jenkins Submission Notes

## Recommended Jenkins Jobs

- `frontend-pipeline`
  - Pipeline script path: `CICD/Jenkins/frontend_Jenkinsfile`
- `backend-pipeline`
  - Pipeline script path: `CICD/Jenkins/backend_Jenkinsfile`

## Required Jenkins Credentials

- No Jenkins credentials are required for Docker Hub push when the `dockerhub-cred` Kubernetes secret is already mounted into the Kaniko container.

## Required Tools On Jenkins Agent

- Jenkins Kubernetes plugin
  - The pipeline launches temporary build pods inside the cluster.
- Kaniko
  - The build pod uses `gcr.io/kaniko-project/executor:debug` to build and push images.
- kubectl
  - The build pod uses `bitnami/kubectl:1.30` for deployment.

The frontend pipeline no longer requires Node.js on the Jenkins controller because the build runs inside a Kubernetes agent pod.
The backend pipeline no longer requires Java or Gradle on the Jenkins controller because the build runs inside a Kubernetes agent pod.

## Target Kubernetes Cluster

- Expected API server: `https://192.100.1.10:32639`
- Target namespace: `chw`
- Example kubeconfig file: `CICD/Jenkins/kubeconfig.example.yaml`

## Required Kubernetes Secrets And RBAC

- Docker Hub pull/push secret in namespace `chw`
  - Secret name: `dockerhub-cred`
- Jenkins RBAC manifest
  - Apply once: `kubectl apply -f CICD/k8s/jenkins-rbac.yaml`

## Notes

- The Jenkinsfiles are written for Jenkins running inside Kubernetes with the Kubernetes plugin.
- They assume the Jenkins service account name is `jenkins` in namespace `default`.
- If your Jenkins chart created a different service account, update `serviceAccountName` in both Jenkinsfiles and the subject in `CICD/k8s/jenkins-rbac.yaml`.

## Replace Before Use

- `lumisia/frontend-board-web`
- `lumisia/frontend-backend-api`

Update those image names in:

- `CICD/Jenkins/frontend_Jenkinsfile`
- `CICD/Jenkins/backend_Jenkinsfile`
- `CICD/k8s/frontend.yaml`
- `CICD/k8s/backend.yaml`

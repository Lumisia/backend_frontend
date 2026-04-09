# Jenkins Submission Notes

## Recommended Jenkins Jobs

- `frontend-pipeline`
  - Pipeline script path: `CICD/Jenkins/frontend_Jenkinsfile`
- `backend-pipeline`
  - Pipeline script path: `CICD/Jenkins/backend_Jenkinsfile`

## Required Jenkins Credentials

- `dockerhub-credentials`
  - Type: Username with password
  - Usage: Docker image push
- `kubeconfig-file`
  - Type: Secret file
  - Usage: `kubectl apply` and rollout check
  - The kubeconfig inside this credential must point to `https://192.100.1.10:32639`

## Required Tools On Jenkins Agent

- Docker
  - Frontend and backend images are built with Dockerfiles.
- kubectl
  - The deployment stage applies the Kubernetes manifests.

The frontend pipeline no longer requires Node.js to be installed directly on the Jenkins host because `npm ci` and `npm run build` are executed inside the Docker image build.
The backend pipeline also no longer requires Java or Gradle on the Jenkins host because `bootJar` is built inside the backend Docker image build.

## Target Kubernetes Cluster

- Expected API server: `https://192.100.1.10:32639`
- Target namespace: `chw`
- Example kubeconfig file: `CICD/Jenkins/kubeconfig.example.yaml`

The Jenkins pipelines verify the kubeconfig server URL before running `kubectl apply`.

## Replace Before Use

- `lumisia/frontend-board-web`
- `lumisia/frontend-backend-api`

Update those image names in:

- `CICD/Jenkins/frontend_Jenkinsfile`
- `CICD/Jenkins/backend_Jenkinsfile`
- `CICD/k8s/frontend.yaml`
- `CICD/k8s/backend.yaml`

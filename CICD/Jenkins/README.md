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

## Replace Before Use

- `your-dockerhub-username/frontend-board-web`
- `your-dockerhub-username/frontend-backend-api`

Update those image names in:

- `CICD/Jenkins/frontend_Jenkinsfile`
- `CICD/Jenkins/backend_Jenkinsfile`
- `CICD/k8s/frontend.yaml`
- `CICD/k8s/backend.yaml`

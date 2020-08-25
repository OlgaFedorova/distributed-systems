oc delete all -l name=first-service
oc delete -f deployments/first-service-template.yaml
oc create -f deployments/first-service-template.yaml
oc new-app --template="distributed-systems/container-first-service-template"
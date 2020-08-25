oc delete all -l name=second-service
oc delete -f deployments/second-service-template.yaml
oc create -f deployments/second-service-template.yaml
oc new-app --template="distributed-systems/container-second-service-template"